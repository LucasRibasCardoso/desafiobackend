package com.example.desafiobackend.services;

import com.example.desafiobackend.entities.establishment.Establishment;
import com.example.desafiobackend.services.exceptions.ExternalServiceUnavailableException;
import com.example.desafiobackend.services.exceptions.InvalidDataException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PhoneValidatorApiService {

  @Value("${numverify.api.key}")
  private  String api_key;

  private static final String API_URL = "https://apilayer.net/api/validate";
  private final RestTemplate restTemplate;

  @Autowired
  public PhoneValidatorApiService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public void validatePhone(Establishment establishment) {
    String phone = establishment.getPhone();

    if (phone == null || phone.isEmpty()) {
      throw new InvalidDataException("Phone number is required.");
    }

    String url = UriComponentsBuilder.fromHttpUrl(API_URL)
        .queryParam("access_key",  api_key)
        .queryParam("number", phone)
        .queryParam("country_code", "BR")
        .toUriString();

    try {
      Map<String, Object> response = restTemplate.getForObject(url, Map.class);

      if (response == null || !(boolean) response.get("valid")) {
        throw new InvalidDataException("Phone is not valid.");
      }
    }
    catch (HttpClientErrorException e) {
      throw new InvalidDataException("Phone not found.");
    }
    catch (ResourceAccessException e) {
      throw new ExternalServiceUnavailableException("Unable to reach the phone validation service.");
    }

  }
}
