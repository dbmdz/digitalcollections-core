package de.digitalcollections.core.model.api.http.exceptions.server;

public class ServiceUnavailableException extends HttpServerException {

  public ServiceUnavailableException(String methodKey, int status, String request) {
    super(methodKey, status, request);
  }

}
