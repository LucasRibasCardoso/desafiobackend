package com.example.desafiobackend.services.exceptions;

public class QuotaReachedException extends RuntimeException {

  public QuotaReachedException(String message) {
    super(message);
  }
}
