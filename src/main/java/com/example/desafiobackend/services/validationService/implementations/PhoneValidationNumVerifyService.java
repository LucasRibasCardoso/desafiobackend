package com.example.desafiobackend.services.validationService.implementations;

import com.example.desafiobackend.services.exceptions.globalsExceptions.ExternalServiceUnavailableException;
import com.example.desafiobackend.services.exceptions.phoneExceptions.PhoneValidationException;
import com.example.desafiobackend.services.validationService.interfaces.ValidationService;
import com.example.desafiobackend.utills.validators.PhoneValidator;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PhoneValidationNumVerifyService implements ValidationService<String> {

  @Value("${numverify.api.key}")
  private  String api_key;

  private static final String API_URL = "https://apilayer.net/api/validate";
  private final RestTemplate restTemplate;

  @Autowired
  public PhoneValidationNumVerifyService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public void validate(String phone) {

    PhoneValidator.validateFormat(phone);
    phone = PhoneValidator.cleanPhone(phone);

    String url = UriComponentsBuilder.fromHttpUrl(API_URL)
        .queryParam("access_key",  api_key)
        .queryParam("number", phone)
        .queryParam("country_code", "BR")
        .toUriString();

    try {
      Map response = restTemplate.getForObject(url, Map.class);

      if (response == null || !Boolean.TRUE.equals(response.get("valid"))) {
        throw new PhoneValidationException("Phone is not valid.");
      }
    }
    catch (HttpClientErrorException e) {
      throw new PhoneValidationException("Error when trying to access external service");
    }
    catch (ResourceAccessException e) {
      throw new ExternalServiceUnavailableException("Unable to reach the phone validation service.");
    }

  }
}