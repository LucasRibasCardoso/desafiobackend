package com.example.desafiobackend.services.exceptions.cnpjExceptions;

public class CnpjValidationException extends RuntimeException {
  public CnpjValidationException(String message) {
    super(message);
  }
}
