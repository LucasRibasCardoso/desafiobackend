package com.example.desafiobackend.utills.validators;

import com.example.desafiobackend.services.exceptions.cnpjExceptions.CnpjFormatInvalidException;
import com.example.desafiobackend.services.exceptions.phoneExceptions.PhoneFormatInvalidException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidator {

  private static final String REGEX_TELEFONE =
      "^\\(\\d{2}\\)\\s\\d{5}-\\d{4}$|" +  // Formato celular: (xx) xxxxx-xxxx
      "^\\(\\d{2}\\)\\s\\d{4}-\\d{4}$";   // Formato fixo: (xx) xxxx-xxxx

  public static void validateFormat(String telefone) throws CnpjFormatInvalidException {
    Pattern pattern = Pattern.compile(REGEX_TELEFONE);
    Matcher matcher = pattern.matcher(telefone);

    if (!matcher.matches()) {
      throw new PhoneFormatInvalidException("Invalid phone number format.");
    }
  }

  public static String cleanPhone(String telefone) {
    return telefone.replaceAll("[^\\d]", ""); // Remove tudo que não for número
  }
}
