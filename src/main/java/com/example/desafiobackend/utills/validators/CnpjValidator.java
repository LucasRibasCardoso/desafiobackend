package com.example.desafiobackend.utills.validators;

import com.example.desafiobackend.services.exceptions.cnpjExceptions.CnpjFormatInvalidException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CnpjValidator {

  // valida cnpj no formato XX.XXX.XXX/XXXX-XX
  private static final String REGEX_CNPJ = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$";

  public static void validateFormat(String cnpj) throws CnpjFormatInvalidException {
    Pattern pattern = Pattern.compile(REGEX_CNPJ);
    Matcher matcher = pattern.matcher(cnpj);

    if (!matcher.matches()) {
      throw new CnpjFormatInvalidException("Invalid CNPJ format.");
    }
  }

  public static String cleanCnpj(String cnpj) {
    return cnpj.replaceAll("[^\\d]", "");
  }
}

