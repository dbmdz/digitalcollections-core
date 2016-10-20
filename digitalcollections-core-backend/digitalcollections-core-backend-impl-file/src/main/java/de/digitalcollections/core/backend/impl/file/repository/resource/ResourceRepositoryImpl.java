package de.digitalcollections.core.backend.impl.file.repository.resource;

import de.digitalcollections.core.backend.api.resource.ResourceRepository;
import de.digitalcollections.core.backend.impl.file.repository.resource.util.ResourcePersistenceTypeHandler;
import de.digitalcollections.core.model.api.enums.MimeTypes;
import de.digitalcollections.core.model.api.resource.Resource;
import de.digitalcollections.core.model.api.resource.enums.ResourcePersistenceType;
import de.digitalcollections.core.model.api.resource.exceptions.ResourceIOException;
import de.digitalcollections.core.model.impl.resource.ResourceImpl;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.ReaderInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * A binary repository using filesystem. see http://docs.oracle.com/javase/tutorial/essential/io/fileio.html see
 * https://docs.oracle.com/javase/tutorial/essential/io/file.html see
 * http://michaelandrews.typepad.com/the_technical_times/2009/10/creating-a-hashed-directory-structure.html
 */
@Repository
public class ResourceRepositoryImpl implements ResourceRepository<Resource> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ResourceRepositoryImpl.class);

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private List<ResourcePersistenceTypeHandler> resourcePersistenceTypeHandlers;

  @Override
  public Resource create(String key, ResourcePersistenceType resourcePersistenceType, String filenameExtension) throws ResourceIOException {
    Resource resource = new ResourceImpl();
    resource.setFilenameExtension(filenameExtension);
    resource.setMimeType(MimeTypes.getMimeType(filenameExtension));

    URI uri = getUri(key, resourcePersistenceType, filenameExtension);
    resource.setUri(uri);

    if (ResourcePersistenceType.REFERENCED.equals(resourcePersistenceType)) {
      resource.setReadonly(true);
    }
    if (ResourcePersistenceType.MANAGED.equals(resourcePersistenceType)) {
      resource.setUuid(UUID.fromString(key));
    }
    return resource;
  }

  @Override
  public void delete(Resource resource) throws ResourceIOException {
    throw new UnsupportedOperationException("Not yet implemented.");
  }

  @Override
  public Resource find(String key, ResourcePersistenceType resourcePersistenceType, String filenameExtension) throws ResourceIOException {
    Resource resource = create(key, resourcePersistenceType, filenameExtension);

    org.springframework.core.io.Resource springResource = applicationContext.getResource(resource.getUri().toString());

    long lastModified = getLastModified(springResource);
    resource.setLastModified(lastModified);

    long length = getSize(springResource);
    resource.setSize(length);

    return resource;
  }

  private long getLastModified(org.springframework.core.io.Resource springResource) {
    try {
      long lastModified = springResource.lastModified();
      return lastModified;
    } catch (IOException ex) {
      if ( ex instanceof FileNotFoundException ) {
        LOGGER.warn("Resource " + springResource.toString()+" does not exist.");
      } else {
        LOGGER.warn("Can not get lastModified for resource " + springResource.toString(), ex);
      }
    }
    return -1;
  }

  private long getSize(org.springframework.core.io.Resource springResource) {
    try {
      long length = springResource.contentLength();
      return length;
    } catch (IOException ex) {
      LOGGER.warn("Can not get size for resource " + springResource.toString(), ex);
    }
    return -1;
  }

  @Override
  public byte[] getBytes(Resource resource) throws ResourceIOException {
    try {
      return IOUtils.toByteArray(this.getInputStream(resource));
    } catch (IOException ex) {
      String msg = "Could not read bytes from resource: " + resource;
      LOGGER.error(msg, ex);
      throw new ResourceIOException(msg, ex);
    }
  }

  @Override
  public InputStream getInputStream(Resource resource) throws ResourceIOException {
    try {
      final URI uri = resource.getUri();
      String location = uri.toString();
      LOGGER.info("Getting inputstream for location '{}'.", location);

      if (location.startsWith("classpath:")) {
        return this.applicationContext.getResource(location).getInputStream();
      } else {
        URL url = uri.toURL();
        InputStream stream = url.openStream();
        return stream;
      }
    } catch (IOException e) {
      throw new ResourceIOException(e);
    }
  }

  @Override
  public Reader getReader(Resource resource) throws ResourceIOException {
    return new InputStreamReader(this.getInputStream(resource));
  }

  public void setResourcePersistenceHandlers(List<ResourcePersistenceTypeHandler> resourcePersistenceTypeHandlers) {
    this.resourcePersistenceTypeHandlers = resourcePersistenceTypeHandlers;
  }

  public ResourcePersistenceTypeHandler getResourcePersistenceTypeHandler(ResourcePersistenceType resourcePersistence)
          throws ResourceIOException {
    for (ResourcePersistenceTypeHandler resourcePersistenceTypeHandler : this.getResourcePersistenceTypeHandlers()) {
      if (resourcePersistence.equals(resourcePersistenceTypeHandler.getResourcePersistenceType())) {
        return resourcePersistenceTypeHandler;
      }
    }
    throw new ResourceIOException("No ResourcePersistenceHandler defined for " + resourcePersistence);
  }

  public List<ResourcePersistenceTypeHandler> getResourcePersistenceTypeHandlers() {
    if (this.resourcePersistenceTypeHandlers == null) {
      this.resourcePersistenceTypeHandlers = new LinkedList<>();
    }
    return resourcePersistenceTypeHandlers;
  }

  private URI getUri(String key, ResourcePersistenceType resourcePersistenceType, String filenameExtension) throws ResourceIOException {
    try {
      ResourcePersistenceTypeHandler resourcePersistenceTypeHandler = this.
              getResourcePersistenceTypeHandler(resourcePersistenceType);
      URI uri = resourcePersistenceTypeHandler.getUri(key, filenameExtension);
      return uri;
    } catch (IOException e) {
      String msg = "Could not create URI for key " + key + " (" + resourcePersistenceType + ")";
      LOGGER.error(msg, e);
      throw new ResourceIOException(msg, e);
    }
  }

  @Override
  public void write(Resource resource, InputStream payload) throws ResourceIOException {

    Assert.notNull(payload);
    Assert.notNull(resource);

    if (resource.isReadonly()) {
      throw new ResourceIOException("Resource does not support write-operations.");
    }

    URI uri = resource.getUri();
    try {
      if ("http".equals(uri.getScheme()) || "https".equals(uri.getScheme())) {
        throw new ResourceIOException("Scheme not supported for write-operations: " + uri.getScheme() + " (" + uri + ")");
      }

      createDirectories(Paths.get(uri));
      LOGGER.info("Writing: " + uri);
      IOUtils.copy(payload, new FileOutputStream(Paths.get(uri).toFile()));
    } catch (IOException e) {
      String msg = "Could not write data to uri " + String.valueOf(uri);
      LOGGER.error(msg, e);
      throw new ResourceIOException(msg, e);
    }
  }

  @Override
  public void write(Resource resource, String output) throws ResourceIOException {
    try (InputStream in = new ReaderInputStream(new StringReader(output), Charset.forName("UTF-8"))) {
      write(resource, in);
    } catch (IOException ex) {
      String msg = "Could not write data to uri " + String.valueOf(resource.getUri());
      LOGGER.error(msg, ex);
      throw new ResourceIOException(msg, ex);
    }
  }

  public static void createDirectories(Path path) throws IOException {
    if (!Files.exists(path.getParent())) {
      LOGGER.info("Creating directories: " + path.getParent());
      Files.createDirectories(path.getParent());
    }
  }
}
