package com.example.desafiobackend.controllers;

import com.example.desafiobackend.entities.establishment.Establishment;
import com.example.desafiobackend.services.EstablishmentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/establishment")
public class EstablishmentController {

  private final EstablishmentService establishmentService;

  @Autowired
  public EstablishmentController(EstablishmentService establishmentService) {
    this.establishmentService = establishmentService;
  }

  @GetMapping
  public ResponseEntity<List<Establishment>> findAllEstablishments() {
   List<Establishment> establishments = establishmentService.findAll();
   return ResponseEntity.ok(establishments);
  }
}
