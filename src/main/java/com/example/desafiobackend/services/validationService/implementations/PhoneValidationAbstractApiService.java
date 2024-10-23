package com.example.desafiobackend.services.validationService.implementations;

import com.example.desafiobackend.services.exceptions.ExternalServiceUnavailableException;
import com.example.desafiobackend.services.exceptions.QuotaReachedException;
import com.example.desafiobackend.services.exceptions.TooManyRequestsException;
import com.example.desafiobackend.services.exceptions.UnauthorizedException;
import com.example.desafiobackend.services.exceptions.cnpjExceptions.CnpjNotFoundException;
import com.example.desafiobackend.services.exceptions.cnpjExceptions.CnpjValidationException;
import com.example.desafiobackend.services.exceptions.phoneExceptions.PhoneNotFoundException;
import com.example.desafiobackend.services.exceptions.phoneExceptions.PhoneValidationException;
import com.example.desafiobackend.services.validationService.interfaces.ValidationService;
import com.example.desafiobackend.utills.validators.PhoneValidator;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PhoneValidationAbstractApiService implements ValidationService<String> {

  @Value("${abstract.api.key}")
  private String api_key;

  private static final String API_URL = "https://phonevalidation.abstractapi.com/v1";
  private final RestTemplate restTemplate;

  @Autowired
  public PhoneValidationAbstractApiService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public void validate(String phone) {

    PhoneValidator.validateFormat(phone);
    phone = PhoneValidator.cleanPhone(phone);

    String url = UriComponentsBuilder.fromHttpUrl(API_URL)
        .queryParam("api_key",  api_key)
        .queryParam("phone", phone)
        .queryParam("country", "BR")
        .toUriString();

    try {
      Map response = restTemplate.getForObject(url, Map.class);

      if (response == null || !Boolean.TRUE.equals(response.get("valid"))) {
        throw new PhoneValidationException("Phone is not valid.");
      }
    }
    catch (HttpClientErrorException e) {
      handleHttpClientErrorException(e);
    }
    catch (ResourceAccessException e) {
      throw new ExternalServiceUnavailableException("Unable to reach the phone validation service.");
    }
  }

  private void handleHttpClientErrorException(HttpClientErrorException e) {
    HttpStatusCode statusCode = e.getStatusCode();

    switch ((HttpStatus) statusCode) {
      case BAD_REQUEST:
        throw new PhoneNotFoundException("Phone not found.");
      case UNAUTHORIZED:
        throw new UnauthorizedException("The request was unacceptable. Verify your api key.");
      case UNPROCESSABLE_ENTITY:
        throw new QuotaReachedException("Quota reached. Insufficient API credits.");
      case TOO_MANY_REQUESTS:
        throw new TooManyRequestsException("Too many requests. Limit exceeded.");
      default:
        throw new PhoneValidationException(
            "Unexpected error when trying to validate phone. Please try again later.");
    }
  }
}
