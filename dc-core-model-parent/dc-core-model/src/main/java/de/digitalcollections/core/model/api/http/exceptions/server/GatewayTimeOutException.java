package de.digitalcollections.core.model.api.http.exceptions.server;

public class GatewayTimeOutException extends HttpServerException {

  public GatewayTimeOutException(String methodKey, int status, String request) {
    super(methodKey, status, request);
  }

}
