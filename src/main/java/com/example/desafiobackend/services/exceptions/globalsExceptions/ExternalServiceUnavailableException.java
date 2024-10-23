package com.example.desafiobackend.services.exceptions.globalsExceptions;

public class ExternalServiceUnavailableException extends RuntimeException {

  public ExternalServiceUnavailableException(String message) {
    super(message);
  }
}
