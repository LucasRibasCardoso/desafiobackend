package com.example.desafiobackend.services;

import com.example.desafiobackend.entities.establishment.Establishment;
import com.example.desafiobackend.services.exceptions.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FieldsValidatorService {

  private final PhoneValidatorApiService phoneValidatorApiService;

  public FieldsValidatorService(PhoneValidatorApiService phoneValidatorApiService) {
    this.phoneValidatorApiService = phoneValidatorApiService;
  }
  
  // Valida os campos de Establishment
  public void validateEstablishment(Establishment establishment) {
    validateName(establishment);
    validateCnpj(establishment);
    phoneValidatorApiService.validatePhone(establishment);
    validateMotorcycleVacancies(establishment);
    validateCarVacancies(establishment);
  }

  private void validateName (Establishment establishment) {
    if (establishment.getName() == null || establishment.getName().isEmpty()) {
      throw new InvalidDataException("Establishment name is required.");
    }
  }

  private void validateCnpj (Establishment establishment) {
    if (establishment.getName() == null || establishment.getName().isEmpty()) {
      throw new InvalidDataException("Establishment name is required.");
    }

    String cnpj = establishment.getCnpj()
        .replace(".", "")
        .replace("-", "")
        .replace("/", "");

    if (cnpj.length() != 14) {
      throw new InvalidDataException("CNPJ must contain 14 digits.");
    }

    try {
      Long.parseLong(cnpj);
    }
    catch (NumberFormatException e) {
      throw new InvalidDataException("CNPJ must contain only numeric digits.");
    }

    // Verifica se os dígitos são todos iguais
    if (cnpj.matches("(\\d)\\1{13}")) {
      throw new InvalidDataException("CNPJ with all the same digits is invalid.");
    }

    char dig13, dig14;
    int sm, i, r, num, peso;

    // Cálculo do primeiro dígito verificador (dig13)
    sm = 0;
    peso = 2;
    for (i = 11; i >= 0; i--) {
      num = (cnpj.charAt(i) - '0');
      sm += num * peso;
      peso = (peso == 9) ? 2 : peso + 1;
    }
    r = sm % 11;
    dig13 = (r < 2) ? '0' : (char) ((11 - r) + '0');

    // Cálculo do segundo dígito verificador (dig14)
    sm = 0;
    peso = 2;
    for (i = 12; i >= 0; i--) {
      num = (cnpj.charAt(i) - '0');
      sm += num * peso;
      peso = (peso == 9) ? 2 : peso + 1;
    }
    r = sm % 11;
    dig14 = (r < 2) ? '0' : (char) ((11 - r) + '0');

    // Verifica se os dígitos verificadores estão corretos
    if (dig13 != cnpj.charAt(12) || dig14 != cnpj.charAt(13)) {
      throw new InvalidDataException("Invalid CNPJ check digits.");
    }
  }

  private void validateMotorcycleVacancies (Establishment establishment) {
    if (establishment.getMotorcycleVacancies() == null) {
      throw new InvalidDataException("Motorcycle vacancies is required.");
    }

    if (establishment.getMotorcycleVacancies() < 0) {
      throw new InvalidDataException("Motorcycle vacancies cannot be negative.");
    }
  }

  private void validateCarVacancies (Establishment establishment) {
    if (establishment.getCarVacancies() == null) {
      throw new InvalidDataException("Car vacancies is required.");
    }

    if (establishment.getCarVacancies() < 0) {
      throw new InvalidDataException("Car vacancies cannot be negative.");
    }
  }

}