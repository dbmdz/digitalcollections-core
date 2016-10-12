package de.digitalcollections.core.backend.impl.file.repository.resource;

import de.digitalcollections.core.backend.api.resource.ResourceRepository;
import de.digitalcollections.core.config.SpringConfigBackendFile;
import de.digitalcollections.core.model.api.resource.Resource;
import de.digitalcollections.core.model.api.resource.enums.ResourcePersistenceType;
import java.net.URI;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfigBackendFile.class})
public class ResourceRepositoryImplTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(ResourceRepositoryImplTest.class);

  @Autowired
  ResourceRepository resourceRepository;

  public ResourceRepositoryImplTest() {
  }

  @BeforeClass
  public static void setUpClass() {
    System.setProperty("spring.profiles.active", "TEST");
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  /**
   * Test of create method, of class ResourceRepositoryImpl.
   */
  @Test
  public void testCreate() throws Exception {
    System.out.println("create");

    // test managed
    String key = "a30cf362-5992-4f5a-8de0-61938134e721";
    ResourcePersistenceType resourcePersistenceType = ResourcePersistenceType.MANAGED;
    Resource resource = resourceRepository.create(key, resourcePersistenceType, "xml");
    URI expResult = new URI("/src/test/resources/repository/de.digitalcollections.core.data.stream/a30c/f362/5992/4f5a/8de0/6193/8134/e721/a30cf362-5992-4f5a-8de0-61938134e721.xml");
    URI result = resource.getUri();
    Assert.assertEquals(expResult, result);

    // test resolved
    key = "bsb00001000";
    resourcePersistenceType = ResourcePersistenceType.RESOLVED;
    resource = resourceRepository.create(key, resourcePersistenceType, "xml");
    expResult = new URI("http://rest.digitale-sammlungen.de/data/bsb00001000.xml");
    result = resource.getUri();
    Assert.assertEquals(expResult, result);
    Assert.assertFalse(resource.isReadonly());

    // test referenced
    key = "bsb00001000";
    resourcePersistenceType = ResourcePersistenceType.REFERENCED;
    resource = resourceRepository.create(key, resourcePersistenceType, "xml");
    expResult = new URI("http://rest.digitale-sammlungen.de/data/bsb00001000.xml");
    result = resource.getUri();
    Assert.assertEquals(expResult, result);
    Assert.assertTrue(resource.isReadonly());
  }

  /**
   * Test of find method, of class ResourceRepositoryImpl.
   */
  @Test
  public void testFind() throws Exception {
    System.out.println("find");
    String key = "snafu";
    ResourcePersistenceType resourcePersistenceType = ResourcePersistenceType.RESOLVED;
    Resource resource = resourceRepository.find(key, resourcePersistenceType, "xml");

    URI expResult = new URI("classpath:/snafu.xml");
    URI result = resource.getUri();
    Assert.assertEquals(expResult, result);

    long expSize = 65;
    long size = resource.getSize();
    Assert.assertEquals(expSize, size);

    long lastModified = resource.getLastModified();
    Assert.assertTrue(lastModified > 0);
  }
}
