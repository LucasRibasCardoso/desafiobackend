package com.example.desafiobackend.utills.validators;

import com.example.desafiobackend.services.exceptions.zipCodeExceptions.ZipCodeFormatInvalidException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZipCodeValidator {

  // valida cep no formato XXXXX-XXX
  private static final String REGEX_CEP = "^\\d{5}-\\d{3}$";

  public static void validateFormat (String zipCode) {
    Pattern pattern = Pattern.compile(REGEX_CEP);
    Matcher matcher = pattern.matcher(zipCode);

    if (!matcher.matches()) {
      throw new ZipCodeFormatInvalidException("Invalid zip code format.");
    }
  }

  public static String cleanZipCode (String zipCode) {
    return zipCode.replace("-", "");
  }
}
