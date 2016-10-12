package de.digitalcollections.core.business.impl.service;

import java.io.InputStream;
import de.digitalcollections.core.backend.api.resource.ResourceRepository;
import de.digitalcollections.core.business.api.ResourceService;
import de.digitalcollections.core.model.api.resource.Resource;
import de.digitalcollections.core.model.api.resource.enums.ResourcePersistenceType;
import de.digitalcollections.core.model.api.resource.exceptions.ResourceIOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl implements ResourceService {

  @Autowired(required = false)
  private ResourceRepository resourceRepository;

  @Override
  public Resource create(String key, ResourcePersistenceType resourcePersistenceType, String filenameExtension) throws ResourceIOException {
    return resourceRepository.create(key, resourcePersistenceType, filenameExtension);
  }

  @Override
  public Resource get(String key, ResourcePersistenceType resourcePersistenceType, String filenameExtension) throws ResourceIOException {
    return resourceRepository.find(key, resourcePersistenceType, filenameExtension);
  }

  @Override
  public InputStream getInputStream(Resource resource) throws ResourceIOException {
    return resourceRepository.getInputStream(resource);
  }

  @Override
  public void write(Resource resource, String output) throws ResourceIOException {
    resourceRepository.write(resource, output);
  }
}
