package de.digitalcollections.core.backend.impl.file.repository.resource.resolver;

import de.digitalcollections.core.model.api.MimeType;
import de.digitalcollections.core.model.api.resource.exceptions.ResourceIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface FileNameResolver {

  Boolean isResolvable(String identifier);

  List<String> getStrings(String identifier) throws ResourceIOException;

  /**
   * Return resolved strings that match the given MIME type.
   *
   * @param identifier file identifier/resolving key
   * @param mimeType target mimetype (resolving subkey)
   * @return list of resolved file uris
   * @throws de.digitalcollections.core.model.api.resource.exceptions.ResourceIOException in case getStrings for key
   * fails
   */
  default List<String> getStrings(String identifier, MimeType mimeType) throws ResourceIOException {
    return getStrings(identifier).stream()
            .filter(s -> MimeType.fromFilename(s).matches(mimeType))
            .collect(Collectors.toList());
  }

  /**
   * Resolve the identifier to URI objects.
   *
   * @param identifier file identifier/resolving key
   * @return list of resolved file uris
   * @throws de.digitalcollections.core.model.api.resource.exceptions.ResourceIOException in case getStrings for key
   * fails
   */
  default List<URI> getUris(String identifier) throws ResourceIOException {
    List<URI> out = new ArrayList<>();
    for (String s : getStrings(identifier)) {
      try {
        out.add(new URI(s));
      } catch (URISyntaxException e) {
        throw new ResourceIOException("Resolved String " + s + "is not a valid URI.");
      }
    }
    return out;
  }

  /**
   * Return resolved URIs that match the given MIME type.
   *
   * @param identifier file identifier/resolving key
   * @param mimeType target mimetype (resolving subkey)
   * @return list of resolved file uris
   * @throws de.digitalcollections.core.model.api.resource.exceptions.ResourceIOException in case getStrings for key
   * fails
   */
  default List<URI> getUris(String identifier, MimeType mimeType) throws ResourceIOException {
    return getUris(identifier).stream()
            .filter(u -> MimeType.fromURI(u).matches(mimeType))
            .collect(Collectors.toList());
  }

  /**
   * Resolve the identifier to java.nio.Path objects.
   *
   * @param identifier file identifier/resolving key
   * @return list of resolved file uris
   * @throws de.digitalcollections.core.model.api.resource.exceptions.ResourceIOException in case getStrings for key
   * fails
   */
  default List<Path> getPaths(String identifier) throws ResourceIOException {
    return getStrings(identifier).stream()
            .map(Paths::get)
            .collect(Collectors.toList());
  }

  /**
   * Return resolved Paths that match the given MIME type.
   *
   * @param identifier file identifier/resolving key
   * @param mimeType target mimetype (resolving subkey)
   * @return list of resolved file uris
   * @throws de.digitalcollections.core.model.api.resource.exceptions.ResourceIOException in case getStrings for key
   * fails
   */
  default List<Path> getPaths(String identifier, MimeType mimeType) throws ResourceIOException {
    return getPaths(identifier).stream()
            .filter(p -> MimeType.fromFilename(p.toString()).matches(mimeType))
            .collect(Collectors.toList());
  }
}
