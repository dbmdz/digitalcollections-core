package de.digitalcollections.core.business.impl.service;

import de.digitalcollections.core.backend.api.resource.ResourceRepository;
import de.digitalcollections.core.business.api.ResourceService;
import de.digitalcollections.core.model.api.MimeType;
import de.digitalcollections.core.model.api.resource.Resource;
import de.digitalcollections.core.model.api.resource.enums.ResourcePersistenceType;
import de.digitalcollections.core.model.api.resource.exceptions.ResourceIOException;
import java.io.InputStream;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

@Service
public class ResourceServiceImpl implements ResourceService {

  @Autowired
  private ResourceRepository resourceRepository;

  @Override
  public Resource create(String key, ResourcePersistenceType resourcePersistenceType, MimeType mimeType) throws ResourceIOException {
    return resourceRepository.create(key, resourcePersistenceType, mimeType);
  }

  @Override
  public Resource get(String key, ResourcePersistenceType resourcePersistenceType, MimeType mimeType) throws ResourceIOException {
    return resourceRepository.find(key, resourcePersistenceType, mimeType);
  }

  @Override
  public Document getDocument(Resource resource) throws ResourceIOException {
    return resourceRepository.getDocument(resource);
  }

  @Override
  public InputStream getInputStream(Resource resource) throws ResourceIOException {
    return resourceRepository.getInputStream(resource);
  }

  @Override
  public InputStream getInputStream(URI resourceUri) throws ResourceIOException {
    return resourceRepository.getInputStream(resourceUri);
  }

  @Override
  public void write(Resource resource, String input) throws ResourceIOException {
    resourceRepository.write(resource, input);
  }

  @Override
  public void assertDocument(Resource resource) throws ResourceIOException {
    resourceRepository.assertDocument(resource);
  }
}
