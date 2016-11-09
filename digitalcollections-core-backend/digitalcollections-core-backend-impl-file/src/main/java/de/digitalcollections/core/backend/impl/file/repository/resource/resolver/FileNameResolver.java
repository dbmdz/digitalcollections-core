package de.digitalcollections.core.backend.impl.file.repository.resource.resolver;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.digitalcollections.core.model.api.MimeType;
import de.digitalcollections.core.model.api.resource.exceptions.ResourceIOException;

public interface FileNameResolver {
  Boolean isResolvable(String identifier);

  List<String> getStrings(String identifier) throws ResourceIOException ;

  /** Return resolved strings that match the given MIME type. **/
  default List<String> getStrings(String identifier, MimeType mimeType) throws ResourceIOException {
    return getStrings(identifier).stream()
        .filter(s -> MimeType.fromFilename(s).equals(mimeType))
        .collect(Collectors.toList());
  }

  /** Resolve the identifier to URI objects. **/
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

  /** Return resolved URIs that match the given MIME type. **/
  default List<URI> getUris(String identifier, MimeType mimeType) throws ResourceIOException {
    return getUris(identifier).stream()
        .filter(u -> MimeType.fromURI(u).equals(mimeType))
        .collect(Collectors.toList());
  }

  /** Resolve the identifier to java.nio.Path objects. **/
  default List<Path> getPaths(String identifier) throws ResourceIOException {
    return getStrings(identifier).stream()
        .map(Paths::get)
        .collect(Collectors.toList());
  }

  /** Return resolved Paths that match the given MIME type. **/
  default List<Path> getPaths(String identifier, MimeType mimeType) throws ResourceIOException {
    return getPaths(identifier).stream()
        .filter(p -> MimeType.fromFilename(p.toString()) == mimeType)
        .collect(Collectors.toList());
  }
}
