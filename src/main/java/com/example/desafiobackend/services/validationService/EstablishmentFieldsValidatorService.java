package com.example.desafiobackend.services.validationService;

import com.example.desafiobackend.entities.establishment.Establishment;
import com.example.desafiobackend.services.exceptions.InvalidDataException;
import com.example.desafiobackend.services.validationService.interfaces.ValidationFields;
import com.example.desafiobackend.services.validationService.interfaces.ValidationService;
import com.example.desafiobackend.utills.validators.RequiredFieldsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstablishmentFieldsValidatorService implements ValidationFields<Establishment> {

  private final PhoneValidationApiService phoneValidationApiService;
  private final ValidationService<String> CnpjValidationService;

  @Autowired
  public EstablishmentFieldsValidatorService(
      PhoneValidationApiService phoneValidatorApiService,
      ValidationService<String> CnpjValidationService
  ) {
    this.phoneValidationApiService = phoneValidatorApiService;
    this.CnpjValidationService = CnpjValidationService;
  }

  @Override
  public void validateFields(Establishment establishment) {
    validateRequiredFields(establishment);
    CnpjValidationService.validate(establishment.getCnpj());
    phoneValidationApiService.validate(establishment.getPhone());
    validateVacancies(establishment);
  }

  private void validateRequiredFields(Establishment establishment) {
    RequiredFieldsValidator.validate(establishment.getName(), "Establishment name");
    RequiredFieldsValidator.validate(establishment.getPhone(), "Establishment phone");
    RequiredFieldsValidator.validate(establishment.getCnpj(), "CNPJ");
  }

  private void validateVacancies(Establishment establishment) {
    validateNonNegativeVacancy(establishment.getMotorcycleVacancies(), "Motorcycle vacancies");
    validateNonNegativeVacancy(establishment.getCarVacancies(), "Car vacancies");
  }

  private void validateNonNegativeVacancy(Integer vacancies, String fieldName) {
    if (vacancies == null) {
      throw new InvalidDataException(fieldName + " is required.");
    }
    if (vacancies < 0) {
      throw new InvalidDataException(fieldName + " cannot be negative.");
    }
  }

}
