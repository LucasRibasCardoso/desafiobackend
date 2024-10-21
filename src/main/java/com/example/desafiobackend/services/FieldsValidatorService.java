package com.example.desafiobackend.services;

import com.example.desafiobackend.entities.address.Address;
import com.example.desafiobackend.entities.establishment.Establishment;
import com.example.desafiobackend.services.exceptions.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FieldsValidatorService {

  private final PhoneValidatorApiService phoneValidatorApiService;
  private final ZipCodeValidatorApiService zipCodeValidatorApiService;

  @Autowired
  public FieldsValidatorService(
      PhoneValidatorApiService phoneValidatorApiService, ZipCodeValidatorApiService zipCodeValidatorApiService
  ) {
    this.phoneValidatorApiService = phoneValidatorApiService;
    this.zipCodeValidatorApiService = zipCodeValidatorApiService;
  }

  public void validateEstablishment(Establishment establishment) {
    validateEstablishmentFields(establishment);
    validateAddressFields(establishment.getAddress());
  }

  // Validação dos campos do estabelecimento
  private void validateEstablishmentFields(Establishment establishment) {
    validateRequiredField(establishment.getName(), "Establishment name");
    validateCnpj(establishment);
    phoneValidatorApiService.validatePhone(establishment);
    validateNonNegativeVacancy(establishment.getMotorcycleVacancies(), "Motorcycle vacancies");
    validateNonNegativeVacancy(establishment.getCarVacancies(), "Car vacancies");
  }

  // Validação dos campos do endereço
  private void validateAddressFields(Address address) {
    validateRequiredField(address.getHomeNumber(), "Home number");
    zipCodeValidatorApiService.validateZipCode(address);
    validateRequiredField(address.getStreet(), "Street");
    validateRequiredField(address.getNeighborhood(), "Neighborhood");
    validateRequiredField(address.getCity(), "City");
    validateRequiredField(address.getState(), "State");
    validateRequiredField(address.getCountry(), "Country");
  }

  // Metodo genérico para validar campos obrigatórios
  private void validateRequiredField(Object field, String fieldName) {
    if (field == null || (field instanceof String && ((String) field).isEmpty())) {
      throw new InvalidDataException(fieldName + " is required.");
    }
  }

  // Valida que um campo numérico não é negativo
  private void validateNonNegativeVacancy(Integer vacancies, String fieldName) {
    if (vacancies == null) {
      throw new InvalidDataException(fieldName + " is required.");
    }
    if (vacancies < 0) {
      throw new InvalidDataException(fieldName + " cannot be negative.");
    }
  }

  // Validação do CNPJ
  private void validateCnpj(Establishment establishment) {
    validateRequiredField(establishment.getCnpj(), "CNPJ");
    String cnpj = cleanCnpj(establishment.getCnpj());
    validateCnpjLength(cnpj);
    validateCnpjIsNumeric(cnpj);
    validateCnpjDigits(cnpj);
  }

  private String cleanCnpj(String cnpj) {
    return cnpj.replace(".", "").replace("-", "").replace("/", "");
  }

  private void validateCnpjLength(String cnpj) {
    if (cnpj.length() != 14) {
      throw new InvalidDataException("CNPJ must contain 14 digits.");
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
    if (cnpj.matches("(\\d)\\1{13}")) {
      throw new InvalidDataException("CNPJ with all the same digits is invalid.");
    }
    char dig13 = calculateCnpjCheckDigit(cnpj, 12);
    char dig14 = calculateCnpjCheckDigit(cnpj, 13);
    if (dig13 != cnpj.charAt(12) || dig14 != cnpj.charAt(13)) {
      throw new InvalidDataException("Invalid CNPJ check digits.");
    }
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
