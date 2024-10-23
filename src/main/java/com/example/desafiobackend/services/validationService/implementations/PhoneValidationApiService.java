package com.example.desafiobackend.services.validationService.implementations;

import com.example.desafiobackend.services.exceptions.ExternalServiceUnavailableException;
import com.example.desafiobackend.services.exceptions.phoneExceptions.PhoneValidationException;
import com.example.desafiobackend.services.validationService.interfaces.ValidationService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PhoneValidationApiService implements ValidationService<String> {

  @Value("${numverify.api.key}")
  private  String api_key;

  private static final String API_URL = "https://apilayer.net/api/validate";
  private final RestTemplate restTemplate;

  @Autowired
  public PhoneValidationApiService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public void validate(String phone) {
    String url = UriComponentsBuilder.fromHttpUrl(API_URL)
        .queryParam("access_key",  api_key)
        .queryParam("number", phone)
        .queryParam("country_code", "BR")
        .toUriString();

    try {
      Map<String, Object> response = restTemplate.getForObject(url, Map.class);

      if (response == null || !(boolean) response.get("valid")) {
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
