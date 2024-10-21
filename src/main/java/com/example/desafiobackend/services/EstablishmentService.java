package com.example.desafiobackend.services;

import com.example.desafiobackend.entities.establishment.Establishment;
import com.example.desafiobackend.repositories.EstablishmentRepository;
import com.example.desafiobackend.services.exceptions.DatabaseException;
import com.example.desafiobackend.services.exceptions.InvalidDataException;
import com.example.desafiobackend.services.exceptions.ResourceNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class EstablishmentService {

  private final EstablishmentRepository establishmentRepository;
  private final FieldsValidatorService fieldsValidatorService;

  @Autowired
  public EstablishmentService(
      EstablishmentRepository establishmentRepository, FieldsValidatorService fieldsValidatorService
  ) {
    this.establishmentRepository = establishmentRepository;
    this.fieldsValidatorService = fieldsValidatorService;
  }


  public List<Establishment> findAllEstablishments() {
    return establishmentRepository.findAll();
  }

  public Establishment findEstablishmentById(Long id) {
    Optional<Establishment> establishment = establishmentRepository.findById(id);
    return establishment.orElseThrow(() -> new ResourceNotFoundException(id));
  }

  @Transactional
  public void insertEstablishment(Establishment establishment) {
    if (establishment == null) {
      throw new InvalidDataException("Establishment cannot be null");
    }

    fieldsValidatorService.validateEstablishment(establishment);

    try {
      establishmentRepository.save(establishment);
    }
    catch (OptimisticLockingFailureException e) {
      throw new DatabaseException("Unable to save this establishment.");
    }
  }
}