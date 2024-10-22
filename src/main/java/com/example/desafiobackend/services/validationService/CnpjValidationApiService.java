package com.example.desafiobackend.services.validationService;

import com.example.desafiobackend.services.exceptions.CnpjValidationException;
import com.example.desafiobackend.services.exceptions.ExternalServiceUnavailableException;
import com.example.desafiobackend.services.validationService.interfaces.ValidationService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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
    String url = UriComponentsBuilder.fromHttpUrl(URL_API)
        .path(cnpj)
        .toUriString();

    try {
      Map<String, Object> response = restTemplate.getForObject(url, Map.class);

      if (response.containsKey("code")) {
        validateError(response);
      }
    }
    catch (HttpClientErrorException e) {
      throw new CnpjValidationException("Invalid CNPJ format");
    }
    catch (ResourceAccessException e) {
      throw new ExternalServiceUnavailableException("Unable to reach the CNPJ validation service.");
    }
  }

  private void validateError(Map<String, Object> response) {
      if (response.get("code").equals(404)) {
        throw new CnpjValidationException("CNPJ not found");
      }
  }
}
