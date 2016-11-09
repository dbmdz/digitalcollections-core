package de.digitalcollections.core.backend.impl.file.repository.resource.util;

import de.digitalcollections.core.backend.impl.file.repository.resource.resolver.FileNameResolver;
import de.digitalcollections.core.model.api.MimeType;
import de.digitalcollections.core.model.api.resource.enums.ResourcePersistenceType;
import de.digitalcollections.core.model.api.resource.exceptions.ResourceIOException;
import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ResolvedResourcePersistenceTypeHandler implements ResourcePersistenceTypeHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ResolvedResourcePersistenceTypeHandler.class);

  @Autowired
  private List<FileNameResolver> fileNameResolvers;

  @Override
  public ResourcePersistenceType getResourcePersistenceType() {
    return ResourcePersistenceType.RESOLVED;
  }

  @Override
  public List<URI> getUris(String resolvingKey, MimeType mimeType) throws ResourceIOException {
    FileNameResolver fileNameResolver = getFileNameResolver(resolvingKey);
    return fileNameResolver.getUris(resolvingKey, mimeType);
  }

  private FileNameResolver getFileNameResolver(String key) throws ResourceIOException {
    return fileNameResolvers.stream()
        .filter(r -> r.isResolvable(key))
        .findFirst()
        .orElseThrow(() -> new ResourceIOException(key + "not resolvable!"));
  }
}
