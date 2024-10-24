package com.example.desafiobackend.utills.validators;

import com.example.desafiobackend.services.exceptions.zipCodeExceptions.ZipCodeFormatInvalidException;

public class ZipCodeValidator {

  // valida cep no formato XXXXX-XXX
  private static final String REGEX_ZIP_CODE = "^\\d{5}-\\d{3}$";

  public static void validateFormat (String zipCode) {

    if (!isValidZipCode(zipCode)) {
      throw new ZipCodeFormatInvalidException("Invalid zip code format.");
    }
  }

  private static boolean isValidZipCode (String zipCode) {
    return zipCode.matches(REGEX_ZIP_CODE);
  }

  public static String cleanZipCode (String zipCode) {
    return zipCode.replace("-", "");
  }
}
