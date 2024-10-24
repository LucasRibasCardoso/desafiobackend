package com.example.desafiobackend.controllers.exceptions.handlers;

import com.example.desafiobackend.services.exceptions.phoneExceptions.PhoneFormatInvalidException;
import com.example.desafiobackend.services.exceptions.phoneExceptions.PhoneNotFoundException;
import com.example.desafiobackend.services.exceptions.phoneExceptions.PhoneValidationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PhoneValidationExceptionHandler {

  private final static GlobalExceptionHandler GEHANDLER = new GlobalExceptionHandler();

  @ExceptionHandler(PhoneNotFoundException.class)
  public ResponseEntity<StandardError> handlePhoneNotFoundException(
      PhoneNotFoundException e, HttpServletRequest request
  ) {
    return GEHANDLER.buildErrorResponse(e, HttpStatus.NOT_FOUND, "Phone number validation error", request);
  }

  @ExceptionHandler(PhoneFormatInvalidException.class)
  public ResponseEntity<StandardError> handlePhoneFormatInvalidException(
      PhoneFormatInvalidException e, HttpServletRequest request) {
    return GEHANDLER.buildErrorResponse(e, HttpStatus.BAD_REQUEST, "Invalid phone number format", request);
  }


  @ExceptionHandler(PhoneValidationException.class)
  public ResponseEntity<StandardError> handlePhoneValidationException(
      PhoneValidationException e, HttpServletRequest request
  ) {
    return GEHANDLER.buildErrorResponse(e, HttpStatus.BAD_REQUEST, "Phone validation error", request);
  }
}
