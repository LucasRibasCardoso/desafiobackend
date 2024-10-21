package com.example.desafiobackend.controllers;

import com.example.desafiobackend.entities.establishment.Establishment;
import com.example.desafiobackend.services.EstablishmentService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/establishments")
public class EstablishmentController {

  @Autowired
  private EstablishmentService establishmentService;

  @GetMapping
  public ResponseEntity<List<Establishment>> findAllEstablishments() {
    List<Establishment> establishments = establishmentService.findAllEstablishments();
    return ResponseEntity.ok(establishments);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Establishment> findEstablishmentById(@PathVariable Long id) {
    Establishment establishment = establishmentService.findEstablishmentById(id);
    return ResponseEntity.ok(establishment);
  }

  @PostMapping()
  public ResponseEntity<Establishment> createEstablishment(@RequestBody Establishment establishment) {
    establishmentService.insertEstablishment(establishment);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(establishment.getId())
        .toUri();

    return ResponseEntity.created(location)
        .body(establishment);
  }
}
