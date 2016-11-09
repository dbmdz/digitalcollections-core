package de.digitalcollections.core.backend.impl.file.repository.resource.util;

import de.digitalcollections.core.model.api.MimeType;
import de.digitalcollections.core.model.api.resource.enums.ResourcePersistenceType;
import de.digitalcollections.core.model.api.resource.exceptions.ResourceIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CustomResourcePersistenceTypeHandler implements ResourcePersistenceTypeHandler {

  @Override
  public ResourcePersistenceType getResourcePersistenceType() {
    return ResourcePersistenceType.CUSTOM;
  }

  @Override
  public List<URI> getUris(String resolvingKey, MimeType mimeType) throws ResourceIOException {
    try {
      return Collections.singletonList(new URI(resolvingKey));
    } catch (URISyntaxException e) {
      throw new ResourceIOException(e);
    }
  }
}
