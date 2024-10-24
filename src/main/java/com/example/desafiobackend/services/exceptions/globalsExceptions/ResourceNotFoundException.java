package com.example.desafiobackend.services.exceptions.globalsExceptions;

import java.io.Serial;

public class ResourceNotFoundException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  public ResourceNotFoundException(Object id) {
    super("Resource not found. ID: " + id);
  }

  public ResourceNotFoundException(String message) {
    super(message);
  }

  public ResourceNotFoundException() {
    super("Resource not found.");
  }

}
