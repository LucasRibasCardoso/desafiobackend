package com.example.desafiobackend.services.validationService.implementations;


import com.example.desafiobackend.services.exceptions.cnpjExceptions.CnpjNotFoundException;
import com.example.desafiobackend.services.exceptions.cnpjExceptions.CnpjValidationException;
import com.example.desafiobackend.services.exceptions.ExternalServiceUnavailableException;

import com.example.desafiobackend.services.exceptions.TooManyRequestsException;
import com.example.desafiobackend.services.validationService.interfaces.ValidationService;
import com.example.desafiobackend.utills.validators.CnpjValidator;
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
public class CnpjValidationApiService implements ValidationService<String> {

  private static final String URL_API = "https://open.cnpja.com/office/";
  private final RestTemplate restTemplate;

  @Autowired
  public CnpjValidationApiService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public void validate (String cnpj) {

    CnpjValidator.validateFormat(cnpj);
    cnpj = CnpjValidator.cleanCnpj(cnpj);

    String url = createUrl(cnpj);

    try {
      Map<String, Object> response = restTemplate.getForObject(url, Map.class);
    }
    catch (HttpClientErrorException e) {
      handleHttpClientErrorException(e);
    }
    catch (ResourceAccessException e) {
      throw new ExternalServiceUnavailableException("Unable to access the cnpj validation service.");
    }
  }

  private String createUrl (String cnpj) {
    String url = UriComponentsBuilder.fromHttpUrl(URL_API)
        .path(cnpj)
        .toUriString();
    return url;
  }

  private void handleHttpClientErrorException(HttpClientErrorException e) {
    HttpStatusCode statusCode = e.getStatusCode();

    switch ((HttpStatus) statusCode) {
      case BAD_REQUEST:
        throw new CnpjNotFoundException("The cnpj format is not valid.");
      case NOT_FOUND:
        throw new CnpjNotFoundException("Cnpj not found at the revenue service.");
      case TOO_MANY_REQUESTS:
        throw new TooManyRequestsException("Too many requests. Limit exceeded.");
      default:
        throw new CnpjValidationException(
            "Unexpected error when trying to validate cnpj. Please try again later.");
    }
  }
}
