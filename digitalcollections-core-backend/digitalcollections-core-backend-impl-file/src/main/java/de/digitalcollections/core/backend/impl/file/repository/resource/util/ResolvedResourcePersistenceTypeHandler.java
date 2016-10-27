package de.digitalcollections.core.backend.impl.file.repository.resource.util;

import de.digitalcollections.core.backend.impl.file.repository.resource.resolver.FileNameResolver;
import de.digitalcollections.core.model.api.resource.enums.ResourcePersistenceType;
import de.digitalcollections.core.model.api.resource.exceptions.ResourceIOException;
import java.net.URI;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ResolvedResourcePersistenceTypeHandler implements ResourcePersistenceTypeHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ResolvedResourcePersistenceTypeHandler.class);

  @Autowired
  private List<FileNameResolver> fileNameResolvers;

  @Override
  public ResourcePersistenceType getResourcePersistenceType() {
    return ResourcePersistenceType.RESOLVED;
  }

  @Override
  public URI getUri(String resolvingKey, String filenameExtension) throws ResourceIOException {
    FileNameResolver fileNameResolver = getFileNameResolver(resolvingKey);
    final URI uri = fileNameResolver.getUri(resolvingKey);

    if (!StringUtils.isEmpty(filenameExtension) && !uri.toString().endsWith(filenameExtension)) {
      throw new ResourceIOException("Resolved URI " + uri.toString() + " targets a resource not of desired filenameExtension " + filenameExtension);
    }
    return uri;
  }

  public FileNameResolver getFileNameResolver(String key) throws ResourceIOException {
    for (FileNameResolver fileNameResolver : fileNameResolvers) {
      if (fileNameResolver.isResolvable(key)) {
        return fileNameResolver;
      }
    }
    LOGGER.warn("No matching pattern found for key " + key);
    throw new ResourceIOException(key + " not resolvable!");
  }
}
