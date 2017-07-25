package de.digitalcollections.core.backend.impl.file.repository.resource.util;

import de.digitalcollections.core.model.api.resource.enums.ResourcePersistenceType;
import org.springframework.stereotype.Component;

@Component
public class ReferencedResourcePersistenceTypeHandler extends ResolvedResourcePersistenceTypeHandler {

  @Override
  public ResourcePersistenceType getResourcePersistenceType() {
    return ResourcePersistenceType.REFERENCED;
  }
}
