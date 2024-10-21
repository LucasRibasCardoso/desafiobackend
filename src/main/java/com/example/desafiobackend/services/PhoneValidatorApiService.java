package com.example.desafiobackend.services;

import com.example.desafiobackend.entities.establishment.Establishment;
import com.example.desafiobackend.services.exceptions.InvalidDataException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PhoneValidatorApiService {

  @Value("${numverify.api.key}")
  private  String api_key;

  private static final String API_URL = "https://apilayer.net/api/validate";

  private final RestTemplate restTemplate;

  public PhoneValidatorApiService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public void validatePhone(Establishment establishment) {
    if (establishment.getPhone() == null || establishment.getPhone().isEmpty()) {
      throw new InvalidDataException("Phone number is required.");
    }

    String phone = establishment.getPhone();

    String url = UriComponentsBuilder.fromHttpUrl(API_URL)
        .queryParam("access_key",  api_key)
        .queryParam("number", phone)
        .queryParam("country_code", "BR")
        .toUriString();

    Map<String, Object> response = restTemplate.getForObject(url, Map.class);

    if (response == null || !(boolean) response.get("valid")) {
      throw new InvalidDataException("Phone is not valid.");
    }
  }
}
