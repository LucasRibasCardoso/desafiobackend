package com.example.desafiobackend.repositories;

import com.example.desafiobackend.entities.establishment.Establishment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstablishmentRepository extends JpaRepository<Establishment, Long> {

  Optional<Establishment> findByCnpj(String cnpj);

  List<Establishment> findByName(String name);
}
