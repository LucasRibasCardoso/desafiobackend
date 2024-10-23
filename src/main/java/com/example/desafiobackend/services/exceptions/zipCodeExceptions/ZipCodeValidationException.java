package com.example.desafiobackend.services.exceptions.zipCodeExceptions;

public class ZipCodeValidationException extends RuntimeException {
  public ZipCodeValidationException(String message) {
    super(message);
  }
}
