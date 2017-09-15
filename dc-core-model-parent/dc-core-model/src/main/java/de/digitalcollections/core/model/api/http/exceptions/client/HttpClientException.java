package de.digitalcollections.core.model.api.http.exceptions.client;

import de.digitalcollections.core.model.api.http.exceptions.HttpException;

public class HttpClientException extends HttpException {

  public HttpClientException(String methodKey, int status, String request) {
    super(methodKey, status, request);
  }

}
