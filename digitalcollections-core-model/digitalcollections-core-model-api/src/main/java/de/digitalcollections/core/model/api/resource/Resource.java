package de.digitalcollections.core.model.api.resource;

import java.net.URI;
import java.util.UUID;

import de.digitalcollections.core.model.api.MimeType;

/**
 * Eine Resource (Quelle) beschreibt eine beliebige Datei, unabhaengig von deren physikalischen Speicherort, verwendete
 * Speichertechnologie oder benoetigten Anzeigemittel (aka "Viewer"). Eine Resource kann z.B. ein Bild, eine Videodatei,
 * eine XML-Dokument oder eine JSON-Datei beinhalten.
 */
public interface Resource {

  String getFilename();

  String getFilenameExtension();

  void setFilenameExtension(String filenameExtension);

  long getLastModified();

  void setLastModified(long lastModified);

  MimeType getMimeType();

  void setMimeType(MimeType mimeType);

  boolean isReadonly();

  void setReadonly(boolean readonly);

  long getSize();

  void setSize(long size);

  URI getUri();

  void setUri(URI uri);

  UUID getUuid();

  void setUuid(UUID uuid);
}
