package de.digitalcollections.core.model.api.http.exceptions;

public class HttpException extends RuntimeException {

  private final int status;

  public HttpException(String methodKey, int status, String request) {
    super(String.format("Got %d for backend call %s.%n⤷ %s", status, methodKey, request));
    this.status = status;
  }

  public int getStatus() {
    return status;
  }

}
