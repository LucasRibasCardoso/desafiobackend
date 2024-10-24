package com.example.desafiobackend.services.validationServices;

import com.example.desafiobackend.entities.establishment.Establishment;
import com.example.desafiobackend.services.exceptions.globalsExceptions.InvalidDataException;
import com.example.desafiobackend.services.validationServices.interfaces.ValidationService;
import com.example.desafiobackend.utills.validators.RequiredFieldsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EstablishmentFieldsValidatorService {

  private final ValidationService<String> phoneValidator;
  private final ValidationService<String> cnpjValidator;

  @Autowired
  public EstablishmentFieldsValidatorService(
      @Qualifier("phoneValidationAbstractApiService") ValidationService<String> phoneValidator,
      @Qualifier("cnpjValidationCnpjJaApiService") ValidationService<String> cnpjValidator
  ) {
    this.phoneValidator = phoneValidator;
    this.cnpjValidator = cnpjValidator;
  }

  public void validateFields(Establishment establishment) {
    validateRequiredFields(establishment);
    cnpjValidator.validate(establishment.getCnpj());
    phoneValidator.validate(establishment.getPhone());
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
