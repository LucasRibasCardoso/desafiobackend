package com.example.desafiobackend.services.validationService.implementations;


import com.example.desafiobackend.services.exceptions.cnpjExceptions.CnpjNotFoundException;
import com.example.desafiobackend.services.exceptions.cnpjExceptions.CnpjValidationException;
import com.example.desafiobackend.services.exceptions.ExternalServiceUnavailableException;
import com.example.desafiobackend.services.exceptions.InvalidDataException;
import com.example.desafiobackend.services.exceptions.TooManyRequestsException;
import com.example.desafiobackend.services.exceptions.UnauthorizedException;
import com.example.desafiobackend.services.validationService.interfaces.ValidationService;
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
    validateFormatCnpj(cnpj);

    String url = createUrl(cnpj);

    try {
      Map<String, Object> response = restTemplate.getForObject(url, Map.class);
    }
    catch (HttpClientErrorException e) {
      handleHttpClientErrorException(e);
    }
    catch (ResourceAccessException e) {
      throw new ExternalServiceUnavailableException("Unable to reach the CNPJ validation service.");
    }
  }

  private String createUrl (String cnpj) {
    String url = UriComponentsBuilder.fromHttpUrl(URL_API)
        .path(cnpj)
        .toUriString();
    return url;
  }

  private void validateFormatCnpj(String cnpj) {
    if (!cnpj.matches("\\d{14}")){
      throw new InvalidDataException("CNPJ invalid");
    }
  }

  private void handleHttpClientErrorException(HttpClientErrorException e) {
    HttpStatusCode statusCode = e.getStatusCode();

    switch ((HttpStatus) statusCode) {
      case BAD_REQUEST:
        throw new CnpjNotFoundException("The CNPJ format is not valid.");
      case UNAUTHORIZED:
        throw new UnauthorizedException("Unauthorized request. Check your API key.");
      case NOT_FOUND:
        throw new CnpjNotFoundException("CNPJ not found at the revenue service.");
      case TOO_MANY_REQUESTS:
        throw new TooManyRequestsException("Too many requests. Limit exceeded.");
      default:
        throw new CnpjValidationException(
            "An error occurred while trying to validate the CNPJ. Please try again later.");
    }
  }
}
