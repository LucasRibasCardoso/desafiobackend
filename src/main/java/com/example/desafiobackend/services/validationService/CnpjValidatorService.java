package com.example.desafiobackend.services.validationService;

import com.example.desafiobackend.services.exceptions.InvalidDataException;
import org.springframework.stereotype.Service;

@Service
public class CnpjValidatorService {

  public void validate(String cnpj) throws InvalidDataException {
    cnpj = removeCnpjFormatting(cnpj);
    validateCnpjLength(cnpj);
    validateCnpjIsNumeric(cnpj);
    validateCnpjDigits(cnpj);
  }

  private String removeCnpjFormatting(String cnpj) {
    return cnpj.replace(".", "").replace("-", "").replace("/", "");
  }

  private void validateCnpjLength(String cnpj) {
    if (cnpj.length() != 14) {
      throw new InvalidDataException("CNPJ must contain exactly 14 digits.");
    }
  }

  private void validateCnpjIsNumeric(String cnpj) {
    try {
      Long.parseLong(cnpj);
    } catch (NumberFormatException e) {
      throw new InvalidDataException("CNPJ must contain only numeric digits.");
    }
  }

  private void validateCnpjDigits(String cnpj) {
    if (hasAllSameDigits(cnpj)) {
      throw new InvalidDataException("CNPJ with all identical digits is invalid.");
    }

    char dig13 = calculateCnpjCheckDigit(cnpj, 12);
    char dig14 = calculateCnpjCheckDigit(cnpj, 13);

    if (dig13 != cnpj.charAt(12) || dig14 != cnpj.charAt(13)) {
      throw new InvalidDataException("Invalid CNPJ check digits.");
    }
  }

  private boolean hasAllSameDigits(String cnpj) {
    return cnpj.matches("(\\d)\\1{13}");
  }

  private char calculateCnpjCheckDigit(String cnpj, int position) {
    int sm = 0;
    int peso = 2;
    for (int i = position - 1; i >= 0; i--) {
      int num = (cnpj.charAt(i) - '0');
      sm += num * peso;
      peso = (peso == 9) ? 2 : peso + 1;
    }
    int r = sm % 11;
    return (r < 2) ? '0' : (char) ((11 - r) + '0');
  }
}
