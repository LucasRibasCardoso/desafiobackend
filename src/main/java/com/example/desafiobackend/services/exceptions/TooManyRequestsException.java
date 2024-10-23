package com.example.desafiobackend.services.exceptions;

public class TooManyRequestsException extends RuntimeException {

  public TooManyRequestsException(String message) {
    super(message);
  }

}
