package com.example.desafiobackend.services;

import com.example.desafiobackend.entities.establishment.Establishment;
import com.example.desafiobackend.repositories.EstablishmentRepository;
import com.example.desafiobackend.services.exceptions.cnpjExceptions.CnpjAlreadyExist;
import com.example.desafiobackend.services.exceptions.globalsExceptions.DatabaseException;
import com.example.desafiobackend.services.exceptions.globalsExceptions.InvalidDataException;
import com.example.desafiobackend.services.exceptions.globalsExceptions.ResourceNotFoundException;
import com.example.desafiobackend.services.validationServices.AddressFieldsValidatorService;
import com.example.desafiobackend.services.validationServices.EstablishmentFieldsValidatorService;
import com.example.desafiobackend.utills.validators.CnpjValidator;
import com.example.desafiobackend.utills.validators.EstablishmentNameValidator;
import com.example.desafiobackend.utills.validators.GlobalsValidators;
import com.example.desafiobackend.utills.validators.RequiredFieldsValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class EstablishmentService {

  private final EstablishmentRepository establishmentRepository;
  private final EstablishmentFieldsValidatorService establishmentFieldsValidator;
  private final AddressFieldsValidatorService addressFieldsValidator;

  @Autowired
  public EstablishmentService(
      EstablishmentRepository establishmentRepository,
      EstablishmentFieldsValidatorService establishmentFieldsValidator,
      AddressFieldsValidatorService addressFieldsValidator
  ) {
    this.establishmentRepository = establishmentRepository;
    this.establishmentFieldsValidator = establishmentFieldsValidator;
    this.addressFieldsValidator = addressFieldsValidator;
  }


  public List<Establishment> findAllEstablishments() {
    return establishmentRepository.findAll();
  }

  public Establishment findEstablishmentById(Long id) {
    Optional<Establishment> establishment = establishmentRepository.findById(id);
    return establishment.orElseThrow(() -> new ResourceNotFoundException(id));
  }

  public Establishment findEstablishmentByCnpj(String cnpj) {
    // remove pontos, barras e hifens
    String cleanCnpj = CnpjValidator.cleanCnpj(cnpj);

    CnpjValidator.validate(cleanCnpj);
    String cnpjWithMask = CnpjValidator.applyMask(cleanCnpj);

    Optional<Establishment> establishment = establishmentRepository.findByCnpj(cnpjWithMask);
    return establishment.orElseThrow(() -> new ResourceNotFoundException());
  }

  public List<Establishment> findEstablishmentsByName(String name) {
    EstablishmentNameValidator.validateName(name);

    String sanitizedName = GlobalsValidators.sanitizedInput(name);
    EstablishmentNameValidator.validateNameLength(sanitizedName);

    List<Establishment> establishments = establishmentRepository.findByNameContainingIgnoreCase(sanitizedName);

    if (establishments.isEmpty()) {
      throw new ResourceNotFoundException("No establishments found with name: " + sanitizedName);
    }
    return establishments;
  }

  @Transactional
  public Establishment insertEstablishment(Establishment establishment) {
    if (establishment == null) {
      throw new InvalidDataException("Establishment cannot be null.");
    }

    if (establishmentRepository.findByCnpj(establishment.getCnpj()).isPresent()) {
      throw new CnpjAlreadyExist("This CNPJ already is register.");
    }

    establishmentFieldsValidator.validateFields(establishment);
    addressFieldsValidator.validateFields(establishment.getAddress());

    try {
      establishmentRepository.save(establishment);
      return establishment;
    }
    catch (OptimisticLockingFailureException e) {
      throw new DatabaseException("Unable to save this establishment.");
    }
  }

  @Transactional
  public void deleteEstablishmentById(Long id) {
    if (!establishmentRepository.existsById(id)) {
      throw new ResourceNotFoundException(id);
    }

    try {
      establishmentRepository.deleteById(id);
    }
    catch (EmptyResultDataAccessException e) {
      throw new ResourceNotFoundException(id);
    }
    catch (DataIntegrityViolationException e) {
      throw new DatabaseException("Unable to delete this establishment.");
    }
  }
}