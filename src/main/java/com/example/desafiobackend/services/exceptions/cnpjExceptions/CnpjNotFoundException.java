package com.example.desafiobackend.services.exceptions.cnpjExceptions;

public class CnpjNotFoundException extends RuntimeException {

  public CnpjNotFoundException(String message) {
    super(message);
  }

}
