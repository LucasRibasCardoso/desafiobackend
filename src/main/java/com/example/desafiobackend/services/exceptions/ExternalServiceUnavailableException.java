package com.example.desafiobackend.services.exceptions;

public class ExternalServiceUnavailableException extends RuntimeException {

  public ExternalServiceUnavailableException(String message) {
    super(message);
  }
}
