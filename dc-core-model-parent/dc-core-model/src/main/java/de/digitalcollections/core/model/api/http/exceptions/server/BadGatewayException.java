package de.digitalcollections.core.model.api.http.exceptions.server;

public class BadGatewayException extends HttpServerException {

  public BadGatewayException(String methodKey, int status, String request) {
    super(methodKey, status, request);
  }

}
