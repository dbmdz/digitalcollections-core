package de.digitalcollections.core.backend.impl.file.repository.resource.resolver;

import de.digitalcollections.core.config.SpringConfigBackendFile;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
// ApplicationContext will be loaded from the static inner SpringConfigBackendFile class
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class MultiPatternsFileNameResolverImplTest {

  @Autowired
  private FileNameResolver fileNameResolver;

  @BeforeClass
  public static void setupClass() {
    System.setProperty("spring.profiles.active", "TEST");
  }

  /**
   * Test of getString method, of class MultiPatternsFileNameResolverImpl.
   *
   * @throws java.lang.Exception
   */
  @Test
  public void testGetString() throws Exception {
    System.out.println("getString");
    String fileName = "bsb00001000_manifest.json";
    String expResult = "file:/bsbstruc/content/bsb_content0000/bsb00001000/iiif/1.0/bsb00001000_manifest.json";
    String result = fileNameResolver.getString(fileName);
    assertEquals(expResult, result);
  }

  /**
   * Test of getURI method, of class MultiPatternsFileNameResolverImpl.
   *
   * @throws java.lang.Exception
   */
  @Test
  public void testGetURI() throws Exception {
    System.out.println("getURI");
    String identifier = "bsb00001000.xml";
    String expResult = "http://rest.digitale-sammlungen.de/data/bsb00001000.xml";
    String result = fileNameResolver.getUri(identifier).toString();
    assertEquals(expResult, result);
  }

  /**
   * Test of isResolvable method, of class MultiPatternsFileNameResolverImpl.
   */
  @Test
  public void testIsResolvable() {
    System.out.println("isResolvable");
    String identifier = "bsb00001000.xml";
    Boolean expResult = true;
    Boolean result = fileNameResolver.isResolvable(identifier);
    assertEquals(expResult, result);
  }

  @Configuration
  static class SpringConfigBackendFileTest extends SpringConfigBackendFile {

    @Bean
    public FileNameResolver fileNameResolver() {
      return new MultiPatternsFileNameResolverImpl();
    }
  }
}
