package de.digitalcollections.core.backend.api.resource;

import de.digitalcollections.core.model.api.MimeType;
import de.digitalcollections.core.model.api.resource.Resource;
import de.digitalcollections.core.model.api.resource.enums.ResourcePersistenceType;
import de.digitalcollections.core.model.api.resource.exceptions.ResourceIOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import org.w3c.dom.Document;

public interface ResourceRepository<R extends Resource> {

  Resource create(String key, ResourcePersistenceType resourcePersistenceType, MimeType mimeType) throws ResourceIOException;

  default Resource create(String key, ResourcePersistenceType resourcePersistenceType, String filenameExtension) throws ResourceIOException {
    return create(key, resourcePersistenceType, MimeType.fromExtension(filenameExtension));
  }

  void delete(R resource) throws ResourceIOException;

  Resource find(String key, ResourcePersistenceType resourcePersistenceType, MimeType mimeType) throws ResourceIOException;

  default Resource find(String key, ResourcePersistenceType resourcePersistenceType, String filenameExtension) throws ResourceIOException {
    return find(key, resourcePersistenceType, MimeType.fromExtension(filenameExtension));
  }

  byte[] getBytes(R resource) throws ResourceIOException;

  Document getDocument(R resource) throws ResourceIOException;

  default Document getDocument(String key, ResourcePersistenceType resourcePersistenceType) throws ResourceIOException {
    Resource resource = find(key, resourcePersistenceType, MimeType.fromExtension("xml"));
    return getDocument((R) resource);
  }

  InputStream getInputStream(URI resourceUri) throws ResourceIOException;

  InputStream getInputStream(R resource) throws ResourceIOException;

  Reader getReader(R resource) throws ResourceIOException;

  void write(Resource resource, String input) throws ResourceIOException;

  void write(Resource resource, InputStream inputStream) throws ResourceIOException;

}
