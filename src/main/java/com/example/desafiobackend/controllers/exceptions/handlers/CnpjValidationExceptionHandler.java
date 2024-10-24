package com.example.desafiobackend.controllers.exceptions.handlers;

import com.example.desafiobackend.services.exceptions.cnpjExceptions.CnpjAlreadyExist;
import com.example.desafiobackend.services.exceptions.cnpjExceptions.CnpjFormatInvalidException;
import com.example.desafiobackend.services.exceptions.cnpjExceptions.CnpjNotFoundException;
import com.example.desafiobackend.services.exceptions.cnpjExceptions.CnpjValidationException;
import com.example.desafiobackend.services.exceptions.globalsExceptions.TooManyRequestsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CnpjValidationExceptionHandler {

  private final static GlobalExceptionHandler GEHANDLER = new GlobalExceptionHandler();

  @ExceptionHandler(CnpjValidationException.class)
  public ResponseEntity<StandardError> handleCnpjValidationException(
      CnpjValidationException e, HttpServletRequest request
  ) {
    return GEHANDLER.buildErrorResponse(e, HttpStatus.BAD_REQUEST, "CNPJ validation error", request);
  }

  @ExceptionHandler(CnpjNotFoundException.class)
  public ResponseEntity<StandardError> handleCnpjNotFoundException(
      CnpjNotFoundException e, HttpServletRequest request) {
    return GEHANDLER.buildErrorResponse(e, HttpStatus.NOT_FOUND, "CNPJ not found", request);
  }

  @ExceptionHandler(TooManyRequestsException.class)
  public ResponseEntity<StandardError> handleTooManyRequestsException(
      TooManyRequestsException e, HttpServletRequest request) {
    return GEHANDLER.buildErrorResponse(e, HttpStatus.TOO_MANY_REQUESTS, "Too many requests", request);
  }

  @ExceptionHandler(CnpjFormatInvalidException.class)
  public ResponseEntity<StandardError> handleCnpjFormatInvalidException(
      CnpjFormatInvalidException e, HttpServletRequest request) {
    return GEHANDLER.buildErrorResponse(e, HttpStatus.BAD_REQUEST, "Invalid CNPJ format", request);
  }

  @ExceptionHandler(CnpjAlreadyExist.class)
  public ResponseEntity<StandardError> handleCnpjAlreadyExistException(
      CnpjAlreadyExist e, HttpServletRequest request) {
    return GEHANDLER.buildErrorResponse(e, HttpStatus.CONFLICT, "CNPJ already exist", request);
  }
}
