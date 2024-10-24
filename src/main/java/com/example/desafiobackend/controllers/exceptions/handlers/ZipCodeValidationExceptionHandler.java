package com.example.desafiobackend.controllers.exceptions.handlers;

import com.example.desafiobackend.services.exceptions.zipCodeExceptions.ZipCodeFormatInvalidException;
import com.example.desafiobackend.services.exceptions.zipCodeExceptions.ZipCodeNotFoundException;
import com.example.desafiobackend.services.exceptions.zipCodeExceptions.ZipCodeValidationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ZipCodeValidationExceptionHandler {

  private static final GlobalExceptionHandler GEHANDLER = new GlobalExceptionHandler();

  @ExceptionHandler(ZipCodeValidationException.class)
  public ResponseEntity<StandardError> handleZipCodeValidationException(
      ZipCodeValidationException e, HttpServletRequest request
  ) {
    return GEHANDLER.buildErrorResponse(e, HttpStatus.BAD_REQUEST, "Zip code validation error", request);
  }

  @ExceptionHandler(ZipCodeFormatInvalidException.class)
  public ResponseEntity<StandardError> handleZipCodeFormatInvalidException(
      ZipCodeFormatInvalidException e, HttpServletRequest request) {
    return GEHANDLER.buildErrorResponse(e, HttpStatus.BAD_REQUEST, "Invalid zip code format", request);
  }

  @ExceptionHandler(ZipCodeNotFoundException.class)
  public ResponseEntity<StandardError> handleZipCodeNotFoundException(
      ZipCodeNotFoundException e, HttpServletRequest request
  ) {
    return GEHANDLER.buildErrorResponse(e, HttpStatus.NOT_FOUND, "Zip not found", request);
  }
}
