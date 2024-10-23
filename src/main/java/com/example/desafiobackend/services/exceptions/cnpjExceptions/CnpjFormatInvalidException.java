package com.example.desafiobackend.services.exceptions.cnpjExceptions;

public class CnpjFormatInvalidException extends RuntimeException {

  public CnpjFormatInvalidException(String message) {
    super(message);
  }
}
