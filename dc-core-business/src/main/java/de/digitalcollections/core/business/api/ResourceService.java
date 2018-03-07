package de.digitalcollections.core.business.api;

import de.digitalcollections.core.model.api.MimeType;
import de.digitalcollections.core.model.api.resource.Resource;
import de.digitalcollections.core.model.api.resource.enums.ResourcePersistenceType;
import de.digitalcollections.core.model.api.resource.exceptions.ResourceIOException;
import java.io.InputStream;
import java.net.URI;
import org.w3c.dom.Document;

public interface ResourceService {

  Resource create(String key, ResourcePersistenceType resourcePersistenceType, MimeType mimeType) throws ResourceIOException;

  default Resource create(String key, ResourcePersistenceType resourcePersistenceType, String fileExtension) throws ResourceIOException {
    return create(key, resourcePersistenceType, MimeType.fromExtension(fileExtension));
  }

  Resource get(String key, ResourcePersistenceType resourcePersistenceType, MimeType mimeType) throws ResourceIOException;

  default Resource get(String key, ResourcePersistenceType resourcePersistenceType, String fileExtension) throws ResourceIOException {
    return get(key, resourcePersistenceType, MimeType.fromExtension(fileExtension));
  }

  Document getDocument(Resource resource) throws ResourceIOException;

  default Document getDocument(String key, ResourcePersistenceType resourcePersistenceType) throws ResourceIOException {
    Resource resource = get(key, resourcePersistenceType, MimeType.fromExtension("xml"));
    return getDocument(resource);
  }

  InputStream getInputStream(Resource resource) throws ResourceIOException;

  InputStream getInputStream(URI resourceUri) throws ResourceIOException;

  void write(Resource resource, String input) throws ResourceIOException;
}
