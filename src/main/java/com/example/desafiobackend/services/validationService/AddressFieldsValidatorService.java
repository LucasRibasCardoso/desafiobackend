package com.example.desafiobackend.services.validationService;

import com.example.desafiobackend.entities.address.Address;
import com.example.desafiobackend.services.validationService.interfaces.ValidationFields;
import com.example.desafiobackend.utills.validators.RequiredFieldsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressFieldsValidatorService implements ValidationFields<Address> {

  private final ZipCodeValidatorApiService zipCodeValidationApiService;

  @Autowired
  public AddressFieldsValidatorService(ZipCodeValidatorApiService zipCodeValidationApiService) {
    this.zipCodeValidationApiService = zipCodeValidationApiService;
  }

  @Override
  public void validateFields(Address address) {
    validateRequiredFields(address);
    zipCodeValidationApiService.validate(address.getZipCode());
  }

  private void validateRequiredFields(Address address) {
    RequiredFieldsValidator.validate(address.getHomeNumber(), "Home number");
    RequiredFieldsValidator.validate(address.getStreet(), "Street");
    RequiredFieldsValidator.validate(address.getZipCode(), "Zip Code");
    RequiredFieldsValidator.validate(address.getNeighborhood(), "Neighborhood");
    RequiredFieldsValidator.validate(address.getCity(), "City");
    RequiredFieldsValidator.validate(address.getState(), "State");
    RequiredFieldsValidator.validate(address.getCountry(), "Country");
  }
}
