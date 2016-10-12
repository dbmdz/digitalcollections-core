package de.digitalcollections.core.backend.impl.file.repository.resource.util;

import java.net.URI;
import de.digitalcollections.core.model.api.resource.enums.ResourcePersistenceType;
import de.digitalcollections.core.model.api.resource.exceptions.ResourceIOException;

public interface ResourcePersistenceTypeHandler {

  ResourcePersistenceType getResourcePersistenceType();

  public URI getUri(String key, String filenameExtension) throws ResourceIOException;
}
