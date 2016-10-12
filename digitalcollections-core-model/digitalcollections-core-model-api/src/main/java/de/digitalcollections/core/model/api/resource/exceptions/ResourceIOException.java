package de.digitalcollections.core.model.api.resource.exceptions;

import java.io.IOException;

public class ResourceIOException extends IOException {

  public ResourceIOException() {
  }

  public ResourceIOException(String message) {
    super(message);
  }

  public ResourceIOException(String message, Throwable cause) {
    super(message, cause);
  }

  public ResourceIOException(Throwable cause) {
    super(cause);
  }
}
