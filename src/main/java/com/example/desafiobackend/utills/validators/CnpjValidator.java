package com.example.desafiobackend.utills.validators;

import com.example.desafiobackend.services.exceptions.cnpjExceptions.CnpjFormatInvalidException;

public class CnpjValidator {

  // valida cnpj no formato XX.XXX.XXX/XXXX-XX
  private static final String REGEX_FORMATED_CNPJ = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$";

  // valida cnpj no formato xxxxxxxxxxxxxx
  private static final String REGEX_CLEAN_CNPJ = "^\\d{14}$";

  public static void validate(String cnpj) throws CnpjFormatInvalidException {
    String cleanCnpj = cleanCnpj(cnpj);

    if (!isValidFormat(cnpj) && !isValidFormat(cleanCnpj)) {
      throw new CnpjFormatInvalidException("Invalid CNPJ format.");
    }
  }

  private static boolean isValidFormat(String cnpj) {
    return cnpj.matches(REGEX_FORMATED_CNPJ) || cnpj.matches(REGEX_CLEAN_CNPJ);
  }

  public static String cleanCnpj(String cnpj) {
    return cnpj.replaceAll("[^\\d]", "");
  }

  // aplica mascara XX.XXX.XXX/XXXX-XX no CNPJ
  public static String applyMask(String cnpj) {
    String cleanedCnpj = cleanCnpj(cnpj);

    if (cleanedCnpj.length() != 14){
      throw new CnpjFormatInvalidException("The cnpj must contain 14 digits.");
    }

    return cnpj.replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
  }
}

