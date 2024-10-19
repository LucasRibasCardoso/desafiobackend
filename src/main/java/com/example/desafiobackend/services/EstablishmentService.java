package com.example.desafiobackend.services;

import com.example.desafiobackend.entities.establishment.Establishment;
import com.example.desafiobackend.repositories.EstablishmentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstablishmentService {

  private final EstablishmentRepository establishmentRepository;

  @Autowired
  public EstablishmentService(EstablishmentRepository establishmentRepository) {
    this.establishmentRepository = establishmentRepository;
  }

  public List<Establishment> findAll() {
    return establishmentRepository.findAll();
  }
}
