package de.digitalcollections.core.backend.impl.file.repository.resource.util;

import java.net.URI;
import java.net.URISyntaxException;
import de.digitalcollections.core.model.api.resource.enums.ResourcePersistenceType;
import de.digitalcollections.core.model.api.resource.exceptions.ResourceIOException;
import org.springframework.stereotype.Component;

@Component
public class CustomResourcePersistenceTypeHandler implements ResourcePersistenceTypeHandler {

  @Override
  public ResourcePersistenceType getResourcePersistenceType() {
    return ResourcePersistenceType.CUSTOM;
  }

  @Override
  public URI getUri(String key, String filenameExtension) throws ResourceIOException {
    try {
      return new URI(key);
    } catch (URISyntaxException e) {
      throw new ResourceIOException(e);
    }
  }
}
