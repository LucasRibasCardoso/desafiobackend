package com.example.desafiobackend.controllers.exceptions;

import com.example.desafiobackend.services.exceptions.DatabaseException;
import com.example.desafiobackend.services.exceptions.ExternalServiceUnavailableException;
import com.example.desafiobackend.services.exceptions.InvalidDataException;
import com.example.desafiobackend.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<StandardError> resourceNotFound(
      ResourceNotFoundException e, HttpServletRequest request
  ) {
    Instant instant = Instant.now();
    int status = HttpStatus.NOT_FOUND.value();
    String error = "Resource not found";
    String message = e.getMessage();
    String path = request.getRequestURI();

    StandardError standardError = new StandardError(instant, status, error, message, path);

    return ResponseEntity.status(status).body(standardError);
  }

  @ExceptionHandler(DatabaseException.class)
  public ResponseEntity<StandardError> databaseException(DatabaseException e, HttpServletRequest request) {

    Instant instant = Instant.now();
    int status = HttpStatus.BAD_REQUEST.value();
    String error = "Database error";
    String message = e.getMessage();
    String path = request.getRequestURI();

    StandardError standardError = new StandardError(instant, status, error, message, path);

    return ResponseEntity.status(status).body(standardError);
  }

  @ExceptionHandler(InvalidDataException.class)
  public ResponseEntity<StandardError> invalidDataException(
      InvalidDataException e, HttpServletRequest request
  ) {
    Instant instant = Instant.now();
    int status = HttpStatus.UNPROCESSABLE_ENTITY.value();
    String error = "Invalid data";
    String message = e.getMessage();
    String path = request.getRequestURI();

    StandardError standardError = new StandardError(instant, status, error, message, path);

    return ResponseEntity.status(status).body(standardError);
  }

  @ExceptionHandler(ExternalServiceUnavailableException.class)
  public ResponseEntity<StandardError> externalServiceUnavailableException(
      ExternalServiceUnavailableException e, HttpServletRequest request)
  {
      Instant instant = Instant.now();
      int status = HttpStatus.SERVICE_UNAVAILABLE.value();
      String error = "Service unavailable";
      String message = e.getMessage();
      String path = request.getRequestURI();

      StandardError standardError = new StandardError(instant, status, error, message, path);

      return ResponseEntity.status(status).body(standardError);
  }

}
