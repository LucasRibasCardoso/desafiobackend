package com.example.desafiobackend.services.exceptions.zipCodeExceptions;

public class ZipCodeFormatInvalidException extends RuntimeException {

  public ZipCodeFormatInvalidException(String message) {
    super(message);
  }

}
