package com.example.desafiobackend.controllers.exceptions;

import com.example.desafiobackend.services.exceptions.cnpjExceptions.CnpjNotFoundException;
import com.example.desafiobackend.services.exceptions.cnpjExceptions.CnpjValidationException;
import com.example.desafiobackend.services.exceptions.DatabaseException;
import com.example.desafiobackend.services.exceptions.ExternalServiceUnavailableException;
import com.example.desafiobackend.services.exceptions.InvalidDataException;
import com.example.desafiobackend.services.exceptions.phoneExceptions.PhoneValidationException;
import com.example.desafiobackend.services.exceptions.ResourceNotFoundException;
import com.example.desafiobackend.services.exceptions.TooManyRequestsException;
import com.example.desafiobackend.services.exceptions.UnauthorizedException;
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


  @ExceptionHandler(PhoneValidationException.class)
  public ResponseEntity<StandardError> handlePhoneValidationException(
      PhoneValidationException e, HttpServletRequest request
  ) {
    return buildErrorResponse(e, HttpStatus.BAD_REQUEST, "Phone validation error", request);
  }

  @ExceptionHandler(ZipCodeValidationException.class)
  public ResponseEntity<StandardError> handleZipCodeValidationException(
      ZipCodeValidationException e, HttpServletRequest request
  ) {
    return buildErrorResponse(e, HttpStatus.BAD_REQUEST, "Zip code validation error", request);
  }

  // API CNPJ
  @ExceptionHandler(CnpjValidationException.class)
  public ResponseEntity<StandardError> handleCnpjValidationException(
      CnpjValidationException e, HttpServletRequest request
  ) {
    return buildErrorResponse(e, HttpStatus.BAD_REQUEST, "Cnpj validation error", request);
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
