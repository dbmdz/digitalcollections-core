package de.digitalcollections.core.model.api;

import java.net.URI;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class MimeTypeTest {
  @Test
  public void getTypeName() throws Exception {
    assertThat(MimeType.MIME_APPLICATION_JSON.getTypeName()).isEqualTo("application/json");
  }

  @Test
  public void getExtensions() throws Exception {
    assertThat(MimeType.MIME_IMAGE_JPEG.getExtensions()).containsExactly("jpg", "jpeg", "jpe");
  }

  @Test
  public void getPrimaryType() throws Exception {
    assertThat(MimeType.MIME_APPLICATION_JSON.getPrimaryType()).isEqualTo("application");
  }

  @Test
  public void getSubType() throws Exception {
    assertThat(MimeType.MIME_APPLICATION_XML.getSubType()).isEqualTo("xml");
  }

  @Test
  public void getSuffix() throws Exception {
    assertThat(MimeType.fromTypename("image/svg+xml").getSuffix()).isEqualTo("xml");
  }

  @Test
  public void returnsNullForUnknownMimetype() throws Exception {
    assertThat(MimeType.fromTypename("foo/bar")).isNull();
  }

  @Test
  public void testEquals() throws Exception {
    MimeType mime = MimeType.fromURI(new URI("file:/bsbstruc/content/bsb_content0009/bsb00092995/xml/standard/2.2/bsb00092995_page.xml"));
    assertThat(mime.equals(MimeType.MIME_APPLICATION_XML));
  }
}