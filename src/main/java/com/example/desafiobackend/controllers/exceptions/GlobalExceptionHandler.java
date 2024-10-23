package com.example.desafiobackend.controllers.exceptions;

import com.example.desafiobackend.services.exceptions.globalsExceptions.QuotaReachedException;
import com.example.desafiobackend.services.exceptions.cnpjExceptions.CnpjFormatInvalidException;
import com.example.desafiobackend.services.exceptions.cnpjExceptions.CnpjNotFoundException;
import com.example.desafiobackend.services.exceptions.cnpjExceptions.CnpjValidationException;
import com.example.desafiobackend.services.exceptions.globalsExceptions.DatabaseException;
import com.example.desafiobackend.services.exceptions.globalsExceptions.ExternalServiceUnavailableException;
import com.example.desafiobackend.services.exceptions.globalsExceptions.InvalidDataException;
import com.example.desafiobackend.services.exceptions.phoneExceptions.PhoneFormatInvalidException;
import com.example.desafiobackend.services.exceptions.phoneExceptions.PhoneNotFoundException;
import com.example.desafiobackend.services.exceptions.phoneExceptions.PhoneValidationException;
import com.example.desafiobackend.services.exceptions.globalsExceptions.ResourceNotFoundException;
import com.example.desafiobackend.services.exceptions.globalsExceptions.TooManyRequestsException;
import com.example.desafiobackend.services.exceptions.globalsExceptions.UnauthorizedException;
import com.example.desafiobackend.services.exceptions.zipCodeExceptions.ZipCodeFormatInvalidException;
import com.example.desafiobackend.services.exceptions.zipCodeExceptions.ZipCodeNotFoundException;
import com.example.desafiobackend.services.exceptions.zipCodeExceptions.ZipCodeValidationException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<StandardError> handleResourceNotFoundException(
      ResourceNotFoundException e, HttpServletRequest request
  ) {
    return buildErrorResponse(e, HttpStatus.NOT_FOUND, "Resource not found", request);
  }

  @ExceptionHandler(DatabaseException.class)
  public ResponseEntity<StandardError> handleDatabaseException(
      DatabaseException e, HttpServletRequest request
  ) {
    return buildErrorResponse(e, HttpStatus.BAD_REQUEST, "Database error", request);
  }

  @ExceptionHandler(InvalidDataException.class)
  public ResponseEntity<StandardError> handleInvalidDataException(
      InvalidDataException e, HttpServletRequest request
  ) {
    return buildErrorResponse(e, HttpStatus.UNPROCESSABLE_ENTITY, "Invalid data", request);
  }

  @ExceptionHandler(ExternalServiceUnavailableException.class)
  public ResponseEntity<StandardError> handleExternalServiceUnavailableException(
      ExternalServiceUnavailableException e, HttpServletRequest request
  ) {
    return buildErrorResponse(e, HttpStatus.SERVICE_UNAVAILABLE, "Service unavailable", request);
  }

  @ExceptionHandler(QuotaReachedException.class)
  public ResponseEntity<StandardError> handleQuotaReachedException(
      QuotaReachedException e, HttpServletRequest request
  ) {
    return buildErrorResponse(e, HttpStatus.UNPROCESSABLE_ENTITY, "Quota reached", request);
  }

  // API TELEFONE
  @ExceptionHandler(PhoneNotFoundException.class)
  public ResponseEntity<StandardError> handlePhoneNotFoundException(
      PhoneNotFoundException e, HttpServletRequest request
  ) {
    return buildErrorResponse(e, HttpStatus.NOT_FOUND, "Phone number validation error", request);
  }

  @ExceptionHandler(PhoneFormatInvalidException.class)
  public ResponseEntity<StandardError> handlePhoneFormatInvalidException(
      PhoneFormatInvalidException e, HttpServletRequest request) {
    return buildErrorResponse(e, HttpStatus.BAD_REQUEST, "Invalid phone number format", request);
  }


  @ExceptionHandler(PhoneValidationException.class)
  public ResponseEntity<StandardError> handlePhoneValidationException(
      PhoneValidationException e, HttpServletRequest request
  ) {
    return buildErrorResponse(e, HttpStatus.BAD_REQUEST, "Phone validation error", request);
  }

  // API CEP ---------------
  @ExceptionHandler(ZipCodeValidationException.class)
  public ResponseEntity<StandardError> handleZipCodeValidationException(
      ZipCodeValidationException e, HttpServletRequest request
  ) {
    return buildErrorResponse(e, HttpStatus.BAD_REQUEST, "Zip code validation error", request);
  }

  @ExceptionHandler(ZipCodeFormatInvalidException.class)
  public ResponseEntity<StandardError> handleZipCodeFormatInvalidException(
      ZipCodeFormatInvalidException e, HttpServletRequest request) {
    return buildErrorResponse(e, HttpStatus.BAD_REQUEST, "Invalid zip code format", request);
  }

  @ExceptionHandler(ZipCodeNotFoundException.class)
  public ResponseEntity<StandardError> handleZipCodeNotFoundException(
      ZipCodeNotFoundException e, HttpServletRequest request
  ) {
    return buildErrorResponse(e, HttpStatus.NOT_FOUND, "Zip code validation error", request);
  }
  // API CNPJ -----------------------------------------------------------------------------------
  @ExceptionHandler(CnpjValidationException.class)
  public ResponseEntity<StandardError> handleCnpjValidationException(
      CnpjValidationException e, HttpServletRequest request
  ) {
    return buildErrorResponse(e, HttpStatus.NOT_FOUND, "CNPJ validation error", request);
  }

  @ExceptionHandler(CnpjNotFoundException.class)
  public ResponseEntity<StandardError> handleCnpjNotFoundException(
      CnpjNotFoundException e, HttpServletRequest request) {
    return buildErrorResponse(e, HttpStatus.NOT_FOUND, "CNPJ not found", request);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<StandardError> handleUnauthorizedException(
      UnauthorizedException e, HttpServletRequest request) {
    return buildErrorResponse(e, HttpStatus.UNAUTHORIZED, "Unauthorized", request);
  }

  @ExceptionHandler(TooManyRequestsException.class)
  public ResponseEntity<StandardError> handleTooManyRequestsException(
      TooManyRequestsException e, HttpServletRequest request) {
    return buildErrorResponse(e, HttpStatus.TOO_MANY_REQUESTS, "Too many requests", request);
  }

  @ExceptionHandler(CnpjFormatInvalidException.class)
  public ResponseEntity<StandardError> handleCnpjFormatInvalidException(
      CnpjFormatInvalidException e, HttpServletRequest request) {
    return buildErrorResponse(e, HttpStatus.BAD_REQUEST, "Invalid CNPJ format", request);
  }


  // Metodo auxiliar para criar uma resposta padronizada de erro
  private ResponseEntity<StandardError> buildErrorResponse(
      Exception e, HttpStatus status, String error, HttpServletRequest request
  ) {
    Instant timestamp = Instant.now();
    String message = e.getMessage();
    String path = request.getRequestURI();

    StandardError standardError = new StandardError(timestamp, status.value(), error, message, path);
    return ResponseEntity.status(status).body(standardError);
  }
}
