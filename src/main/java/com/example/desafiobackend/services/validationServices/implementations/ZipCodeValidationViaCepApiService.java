package com.example.desafiobackend.services.validationServices.implementations;

import com.example.desafiobackend.services.exceptions.globalsExceptions.ExternalServiceUnavailableException;
import com.example.desafiobackend.services.exceptions.zipCodeExceptions.ZipCodeFormatInvalidException;
import com.example.desafiobackend.services.exceptions.zipCodeExceptions.ZipCodeNotFoundException;
import com.example.desafiobackend.services.exceptions.zipCodeExceptions.ZipCodeValidationException;
import com.example.desafiobackend.services.validationServices.interfaces.ValidationService;
import com.example.desafiobackend.utills.validators.ZipCodeValidator;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ZipCodeValidationViaCepApiService implements ValidationService <String>{

  private static final String URL_API = "https://viacep.com.br/ws/";
  private final RestTemplate restTemplate;

  @Autowired
  public ZipCodeValidationViaCepApiService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public void validate(String zipCode) {
    ZipCodeValidator.validateFormat(zipCode);

    // remove o hífen
    String cleanZipCode = ZipCodeValidator.cleanZipCode(zipCode);

    String url = createUrl(cleanZipCode);

    try {
      Map<String, Object> response = restTemplate.getForObject(url, Map.class);
      validateResponseZipCode(response);
    }
    catch (HttpClientErrorException e) {
      handleHttpClientErrorException(e);
    }
    catch (ResourceAccessException e) {
      throw new ExternalServiceUnavailableException("Unable to reach the zip code validation service.");
    }
  }

  private String createUrl(String zipCode) {
    String url = UriComponentsBuilder.fromHttpUrl(URL_API)
        .path(zipCode + "/" + "json")
        .toUriString();
    return url;
  }

  private void validateResponseZipCode(Map<String, Object> response){
    if (response != null) {
      if (response.containsKey("erro")) {
        throw new ZipCodeNotFoundException("Zip code not found.");
      }
    }
  }

  private void handleHttpClientErrorException(HttpClientErrorException e){
    HttpStatusCode statusCode = e.getStatusCode();

    switch ((HttpStatus) statusCode) {
      case BAD_REQUEST:
        throw new ZipCodeFormatInvalidException("Zip code is invalid.");

      default:
        throw new ZipCodeValidationException(
            "Unexpected error when trying to validate zip code. Please try again later.");
    }
  }
}
