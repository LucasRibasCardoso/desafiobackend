package com.example.desafiobackend.services.validationService;

import com.example.desafiobackend.entities.address.Address;
import com.example.desafiobackend.services.validationService.interfaces.ValidationService;
import com.example.desafiobackend.utills.validators.RequiredFieldsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AddressFieldsValidatorService {

  private final ValidationService<String> zipCodeValidator;

  @Autowired
  public AddressFieldsValidatorService(
      @Qualifier("zipCodeValidationViaCepApiService") ValidationService<String> zipCodeValidator)
  {
    this.zipCodeValidator = zipCodeValidator;
  }

  public void validateFields(Address address) {
    validateRequiredFields(address);
    zipCodeValidator.validate(address.getZipCode());
  }

  private void validateRequiredFields(Address address) {
    RequiredFieldsValidator.validate(address.getHomeNumber(), "Home number");
    RequiredFieldsValidator.validate(address.getStreet(), "Street");
    RequiredFieldsValidator.validate(address.getZipCode(), "Zip Code");
    RequiredFieldsValidator.validate(address.getNeighborhood(), "Neighborhood");
    RequiredFieldsValidator.validate(address.getCity(), "City");
    RequiredFieldsValidator.validate(address.getState(), "State");
  }
}
