package com.example.desafiobackend.services.exceptions.globalsExceptions;

public class TooManyRequestsException extends RuntimeException {

  public TooManyRequestsException(String message) {
    super(message);
  }

}
