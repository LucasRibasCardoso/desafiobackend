package com.example.desafiobackend.services.exceptions.globalsExceptions;

public class UnauthorizedException extends RuntimeException {

  public UnauthorizedException(String message) {
    super(message);
  }

}
