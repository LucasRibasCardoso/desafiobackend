package com.example.desafiobackend.controllers;

import com.example.desafiobackend.entities.establishment.Establishment;
import com.example.desafiobackend.services.EstablishmentService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/api/establishments")
public class EstablishmentController {

  private final EstablishmentService establishmentService;

  @Autowired
  public EstablishmentController(EstablishmentService establishmentService) {
    this.establishmentService = establishmentService;
  }

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

  @GetMapping(value = "/cnpj/{cnpj}")
  public ResponseEntity<Establishment> findEstablishmentByCnpj(@PathVariable String cnpj) {
    Establishment establishment = establishmentService.findEstablishmentByCnpj(cnpj);
    return ResponseEntity.ok(establishment);
  }

  @GetMapping(value = "/search")
  public ResponseEntity<List<Establishment>> findEstablishmentsByEstablishmentId(@RequestParam String name){
    List<Establishment> establishments = establishmentService.findEstablishmentsByName(name);
    return ResponseEntity.ok(establishments);
  }


  @PostMapping()
  public ResponseEntity<Establishment> createEstablishment(@RequestBody Establishment establishment) {
    establishment = establishmentService.insertEstablishment(establishment);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(establishment.getId())
        .toUri();

    return ResponseEntity.created(uri).body(establishment);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> deleteEstablishment(@PathVariable Long id) {
    establishmentService.deleteEstablishmentById(id);
    return ResponseEntity.ok().build();
  }

}
