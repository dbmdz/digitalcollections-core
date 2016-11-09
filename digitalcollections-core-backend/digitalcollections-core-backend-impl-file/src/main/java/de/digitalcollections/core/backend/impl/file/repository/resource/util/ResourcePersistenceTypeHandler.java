package de.digitalcollections.core.backend.impl.file.repository.resource.util;

import de.digitalcollections.core.model.api.MimeType;
import de.digitalcollections.core.model.api.resource.enums.ResourcePersistenceType;
import de.digitalcollections.core.model.api.resource.exceptions.ResourceIOException;
import java.net.URI;
import java.util.List;

public interface ResourcePersistenceTypeHandler {

  ResourcePersistenceType getResourcePersistenceType();

  List<URI> getUris(String resolvingKey, MimeType mimeType) throws ResourceIOException;

  default URI getUri(String resolvingKey, String fileExtension) throws ResourceIOException {
    List<URI> uris = getUris(resolvingKey, fileExtension);
    if (uris.isEmpty()) {
      throw new ResourceIOException("Could not find URI for " + resolvingKey + " with extension " + fileExtension);
    } else {
      return uris.get(0);
    }
  }

  default List<URI> getUris(String resolvingKey, String filenameExtension) throws ResourceIOException {
    return getUris(resolvingKey, MimeType.fromExtension(filenameExtension));
  };
}
