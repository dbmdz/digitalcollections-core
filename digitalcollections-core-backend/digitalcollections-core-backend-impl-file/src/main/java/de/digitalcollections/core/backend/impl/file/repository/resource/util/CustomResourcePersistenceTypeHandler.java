package de.digitalcollections.core.backend.impl.file.repository.resource.util;

import de.digitalcollections.core.model.api.resource.enums.ResourcePersistenceType;
import de.digitalcollections.core.model.api.resource.exceptions.ResourceIOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.stereotype.Component;

@Component
public class CustomResourcePersistenceTypeHandler implements ResourcePersistenceTypeHandler {

  @Override
  public ResourcePersistenceType getResourcePersistenceType() {
    return ResourcePersistenceType.CUSTOM;
  }

  @Override
  public URI getUri(String resolvingKey, String filenameExtension) throws ResourceIOException {
    try {
      return new URI(resolvingKey);
    } catch (URISyntaxException e) {
      throw new ResourceIOException(e);
    }
  }
}
