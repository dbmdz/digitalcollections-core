package de.digitalcollections.core.backend.impl.file.repository.resource.resolver;

import java.net.URI;
import java.nio.file.Path;
import de.digitalcollections.core.model.api.resource.exceptions.ResourceIOException;

public interface FileNameResolver {

  Boolean isResolvable(String fileName);

  String getString(String fileName) throws ResourceIOException;

  URI getUri(String fileName) throws ResourceIOException;

  Path getPath(String fileName) throws ResourceIOException;
}
