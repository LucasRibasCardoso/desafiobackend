package com.example.desafiobackend.services.exceptions.globalsExceptions;

public class QuotaReachedException extends RuntimeException {

  public QuotaReachedException(String message) {
    super(message);
  }
}
