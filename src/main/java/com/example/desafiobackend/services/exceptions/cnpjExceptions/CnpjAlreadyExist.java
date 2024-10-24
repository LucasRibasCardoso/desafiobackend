package com.example.desafiobackend.services.exceptions.cnpjExceptions;

public class CnpjAlreadyExist extends RuntimeException {

  public CnpjAlreadyExist(String message) {
    super(message);
  }

}
