package de.digitalcollections.core.backend.api.resource;

import de.digitalcollections.core.model.api.resource.Resource;
import de.digitalcollections.core.model.api.resource.enums.ResourcePersistenceType;
import de.digitalcollections.core.model.api.resource.exceptions.ResourceIOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;

public interface ResourceRepository<R extends Resource> {

  Resource create(String key, ResourcePersistenceType resourcePersistenceType, String filenameExtension) throws ResourceIOException;

  void delete(R resource) throws ResourceIOException;

  Resource find(String key, ResourcePersistenceType resourcePersistenceType, String filenameExtension) throws ResourceIOException;

  byte[] getBytes(R resource) throws ResourceIOException;

  InputStream getInputStream(URI resourceUri) throws ResourceIOException;

  InputStream getInputStream(R resource) throws ResourceIOException;

  Reader getReader(R resource) throws ResourceIOException;

  void write(Resource resource, String output) throws ResourceIOException;

  void write(Resource resource, InputStream inputStream) throws ResourceIOException;

}
