package com.example.desafiobackend.services.validationService;

import com.example.desafiobackend.services.exceptions.ExternalServiceUnavailableException;
import com.example.desafiobackend.services.exceptions.InvalidDataException;
import com.example.desafiobackend.services.exceptions.ResourceNotFoundException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ZipCodeValidatorApiService {

  private static final String URL_API = "https://viacep.com.br/ws/";
  private final RestTemplate restTemplate;

  @Autowired
  public ZipCodeValidatorApiService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public void validate(String zipCode) {
    String url = UriComponentsBuilder.fromHttpUrl(URL_API)
        .path(zipCode + "/" + "json")
        .toUriString();

    try {
      Map<String, Object> response = restTemplate.getForObject(url, Map.class);

      if (response != null && response.containsKey("erro")) {
        throw new InvalidDataException("Zip code is invalid.");
      }
    }
    catch (HttpClientErrorException e) {
      throw new InvalidDataException("Zip code not found.");
    }
    catch (ResourceAccessException e) {
      throw new ExternalServiceUnavailableException("Unable to reach the zip code validation service.");
    }
  }

}
