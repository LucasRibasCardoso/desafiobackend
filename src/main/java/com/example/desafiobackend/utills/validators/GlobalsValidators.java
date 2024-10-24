package com.example.desafiobackend.utills.validators;

public class GlobalsValidators {


  public static String sanitizedInput (String name) {
    return name.replaceAll("[^a-zA-Z0-9\\s]", "");
  }
}
