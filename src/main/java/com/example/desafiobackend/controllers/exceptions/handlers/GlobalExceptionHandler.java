package com.example.desafiobackend.controllers.exceptions.handlers;

import com.example.desafiobackend.services.exceptions.globalsExceptions.QuotaReachedException;
import com.example.desafiobackend.services.exceptions.globalsExceptions.DatabaseException;
import com.example.desafiobackend.services.exceptions.globalsExceptions.ExternalServiceUnavailableException;
import com.example.desafiobackend.services.exceptions.globalsExceptions.InvalidDataException;
import com.example.desafiobackend.services.exceptions.globalsExceptions.ResourceNotFoundException;
import com.example.desafiobackend.services.exceptions.globalsExceptions.UnauthorizedException;
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

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<StandardError> handleUnauthorizedException(
      UnauthorizedException e, HttpServletRequest request) {
    return buildErrorResponse(e, HttpStatus.UNAUTHORIZED, "Unauthorized", request);
  }

  // Metodo auxiliar para criar uma resposta padronizada de erro
  protected ResponseEntity<StandardError> buildErrorResponse(
      Exception e, HttpStatus status, String error, HttpServletRequest request
  ) {
    Instant timestamp = Instant.now();
    String message = e.getMessage();
    String path = request.getRequestURI();

    StandardError standardError = new StandardError(timestamp, status.value(), error, message, path);
    return ResponseEntity.status(status).body(standardError);
  }
}
