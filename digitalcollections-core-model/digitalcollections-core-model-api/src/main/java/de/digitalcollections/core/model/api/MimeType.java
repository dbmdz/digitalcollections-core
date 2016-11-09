package de.digitalcollections.core.model.api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;

public class MimeType {
  private static Map<String, MimeType> knownTypes;
  static {
    // Load list of known MIME types and their extensions from the IANA list in the
    // package resources
    InputStream mimeStream = MimeType.class
        .getClassLoader().getResourceAsStream("mime.types");
    BufferedReader mimeReader = new BufferedReader(new InputStreamReader(mimeStream));
    knownTypes = mimeReader.lines()
        .filter(l -> !l.startsWith("#"))
        // Normalize multiple tab-delimiters to a single one for easier parsing
        // and split into (type, extensions) pairs
        .map(l -> l.replaceAll("\\t+", "\t").split("\\t"))
        // From those pairs, make a list of the extensions and create MimeType instances
        .map(p -> new MimeType(p[0], Arrays.asList(p[1].split(" "))))
        .collect(Collectors.toMap(
            MimeType::getTypeName,
            Function.identity()));

    // Some custom overrides to influence the order of file extensions
      // Since these are added to the end of the list, they take precedence over the
      // types from the `mime.types` file
      knownTypes.get("image/jpeg").setExtensions(Arrays.asList("jpg", "jpeg", "jpe"));
      knownTypes.get("image/tiff").setExtensions(Arrays.asList("tif", "tiff"));
      knownTypes.get("application/xml").getExtensions().add("ent");
  }

  /** Convenience definitions for commonly used MIME types */
  public static final MimeType MIME_WILDCARD = new MimeType("*", Collections.emptyList());
  public static final MimeType MIME_APPLICATION_JSON = knownTypes.get("application/json");
  public static final MimeType MIME_APPLICATION_XML = knownTypes.get("application/xml");
  public static final MimeType MIME_IMAGE_JPEG = knownTypes.get("image/jpeg");
  public static final MimeType MIME_IMAGE_TIF = knownTypes.get("image/tif");
  public static final MimeType MIM_IMAGE_PNG = knownTypes.get("image/png");

  private final Pattern mimePattern = Pattern.compile(
      "^(?<primaryType>[a-z]+?)/(?<subType>[-\\\\.a-z0-9]+?)(?:\\+(?<suffix>\\w+))?$");

  private String typeName;
  private List<String> extensions;

  /** Determine MIME type for the given file extension */
  public static MimeType fromExtension(String ext) {
    final String extension;
    if (ext.startsWith(".")) {
      extension = ext.substring(1);
    } else {
      extension = ext;
    }
    return knownTypes.values().stream()
        .filter(m -> m.getExtensions().contains(extension))
        .findFirst().orElseGet(() -> null);
  }

  /**
   * Determine MIME type from filename string.
   * Returns null if no matching MIME type was found.
   */
  public static MimeType fromFilename(String filename) {
    return fromExtension(FilenameUtils.getExtension(filename));
  }

  public static MimeType fromURI(URI uri) {
    try {
      return fromFilename(Paths.get(uri).toString());
    } catch (FileSystemNotFoundException e) {
      // For non-file URIs, try to guess the MIME type from the URL path, if possible
      return fromExtension(FilenameUtils.getExtension(uri.toString()));
    }
  }

  public static MimeType fromTypename(String typeName) {
    return knownTypes.get(typeName);
  }

  private MimeType(String typeName) {
    this.typeName = typeName;
    this.extensions = Collections.emptyList();
  }

  private MimeType(String typeName, List<String> extensions) {
    this.typeName = typeName;
    this.extensions = extensions;
  }


  /** Get the MIME type's name (e.g. "application/json") */
  public String getTypeName() {
    return typeName;
  }

  /** Get the known file extensions for the MIME type */
  public List<String> getExtensions() {
    return extensions;
  }

  private void setExtensions(List<String> extensions) {
    this.extensions = extensions;
  }

  public String getPrimaryType() {
    Matcher matcher = mimePattern.matcher(typeName);
    if (matcher.matches()) {
      return matcher.group("primaryType");
    } else {
      return null;
    }
  }

  public String getSubType() {
    Matcher matcher = mimePattern.matcher(typeName);
    if (matcher.matches()) {
      return matcher.group("subType");
    } else {
      return null;
    }
  }

  public String getSuffix() {
    Matcher matcher = mimePattern.matcher(typeName);
    if (matcher.matches()) {
      return matcher.group("suffix");
    } else {
      return null;
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof MimeType && ((MimeType) obj) == MIME_WILDCARD) {
      return true;
    }
    return super.equals(obj);
  }
}
