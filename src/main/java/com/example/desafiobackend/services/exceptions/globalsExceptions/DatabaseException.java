package com.example.desafiobackend.services.exceptions.globalsExceptions;

import java.io.Serial;

public class DatabaseException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  public DatabaseException(String message) {
    super(message);
  }
}
