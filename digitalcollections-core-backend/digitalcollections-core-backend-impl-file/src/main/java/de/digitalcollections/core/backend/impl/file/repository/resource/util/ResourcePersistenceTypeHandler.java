package de.digitalcollections.core.backend.impl.file.repository.resource.util;

import de.digitalcollections.core.model.api.resource.enums.ResourcePersistenceType;
import de.digitalcollections.core.model.api.resource.exceptions.ResourceIOException;
import java.net.URI;

public interface ResourcePersistenceTypeHandler {

  ResourcePersistenceType getResourcePersistenceType();

  public URI getUri(String resolvingKey, String filenameExtension) throws ResourceIOException;
}
