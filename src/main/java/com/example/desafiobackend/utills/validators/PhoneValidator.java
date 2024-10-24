package com.example.desafiobackend.utills.validators;

import com.example.desafiobackend.services.exceptions.cnpjExceptions.CnpjFormatInvalidException;
import com.example.desafiobackend.services.exceptions.phoneExceptions.PhoneFormatInvalidException;

public class PhoneValidator {

  private static final String REGEX_PHONE =
      "^\\(\\d{2}\\)\\s\\d{5}-\\d{4}$|" +  // Formato telefone celular: (xx) xxxxx-xxxx
      "^\\(\\d{2}\\)\\s\\d{4}-\\d{4}$";   // Formato telefone fixo: (xx) xxxx-xxxx

  public static void validateFormat(String phone) throws CnpjFormatInvalidException {

    if (!isValidFormat(phone)) {
      throw new PhoneFormatInvalidException("Invalid phone number format.");
    }
  }

  private static boolean isValidFormat(String phone) {
    return phone.matches(REGEX_PHONE);
  }

  public static String cleanPhone(String telefone) {
    return telefone.replaceAll("[^\\d]", ""); // Remove tudo que não for número
  }
}
