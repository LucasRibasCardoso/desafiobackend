package com.example.desafiobackend.utills.validators;

import com.example.desafiobackend.services.exceptions.globalsExceptions.InvalidDataException;

public class EstablishmentNameValidator {

  public static void validateNameLength (String nameSanitized) {
    if (nameSanitized.length() < 2 || nameSanitized.length() > 100) {
      throw new InvalidDataException("The establishment name must be between 2 and 100 characters long.");
    }
  }

  public static void validateName (String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new InvalidDataException("Establishment name is required.");
    }
  }
}
