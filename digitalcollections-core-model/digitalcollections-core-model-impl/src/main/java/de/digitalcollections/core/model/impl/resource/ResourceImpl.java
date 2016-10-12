package de.digitalcollections.core.model.impl.resource;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.UUID;
import javax.activation.MimeType;
import de.digitalcollections.core.model.api.resource.Resource;

public class ResourceImpl implements Resource {

  private String filenameExtension;
  private long lastModified = -1;
  private boolean readonly = false;
  private long size = -1;
  private MimeType mimeType;
  private URI uri;
  private UUID uuid = UUID.randomUUID();

  public ResourceImpl() {
  }

  @Override
  public String getFilename() {
    String filename = null;
    if (uri != null) {
      try {
        filename = uri.toURL().getFile();
      } catch (MalformedURLException ex) {
        filename = null;
      }
    }
    return filename;
  }

  @Override
  public String getFilenameExtension() {
    return filenameExtension;
  }

  @Override
  public void setFilenameExtension(String filenameExtension) {
    this.filenameExtension = filenameExtension;
  }

  @Override
  public long getLastModified() {
    return this.lastModified;
  }

  @Override
  public void setLastModified(long lastModified) {
    this.lastModified = lastModified;
  }

  @Override
  public MimeType getMimeType() {
    return this.mimeType;
  }

  @Override
  public void setMimeType(MimeType mimeType) {
    this.mimeType = mimeType;
  }

  @Override
  public long getSize() {
    return size;
  }

  @Override
  public void setSize(long size) {
    this.size = size;
  }

  @Override
  public URI getUri() {
    return this.uri;
  }

  @Override
  public void setUri(URI uri) {
    this.uri = uri;
  }

  @Override
  public UUID getUuid() {
    return uuid;
  }

  @Override
  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  @Override
  public boolean isReadonly() {
    return this.readonly;
  }

  @Override
  public void setReadonly(boolean readonly) {
    this.readonly = readonly;
  }

  @Override
  public String toString() {
    return "ResourceImpl"
            + "\n{"
            + "\n  uuid=" + String.valueOf(uuid)
            + ",\n  uri=" + String.valueOf(uri)
            + ",\n  mimetype=" + String.valueOf(mimeType)
            + ",\n  lastModified=" + lastModified
            + "\n}";
  }
}
