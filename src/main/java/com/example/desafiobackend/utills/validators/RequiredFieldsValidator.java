package com.example.desafiobackend.utills.validators;

import com.example.desafiobackend.services.exceptions.InvalidDataException;

public class RequiredFieldsValidator {

  public static void validate(Object field, String fieldName) {
    if (field == null || (field instanceof String && ((String) field).isEmpty())) {
      throw new InvalidDataException(fieldName + " is required.");
    }
  }
}
