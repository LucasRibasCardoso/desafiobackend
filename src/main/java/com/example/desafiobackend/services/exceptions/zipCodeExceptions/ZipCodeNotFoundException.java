package com.example.desafiobackend.services.exceptions.zipCodeExceptions;

public class ZipCodeNotFoundException extends RuntimeException {

  public ZipCodeNotFoundException(String message) {
    super(message);
  }

}
