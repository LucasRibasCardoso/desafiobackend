package com.example.desafiobackend.configurations.test;

import com.example.desafiobackend.entities.address.Address;
import com.example.desafiobackend.entities.establishment.Establishment;
import com.example.desafiobackend.repositories.EstablishmentRepository;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class TestConfig implements CommandLineRunner {

  @Autowired
  private EstablishmentRepository establishmentRepository;

  @Override
  public void run(String... args) throws Exception {

    Address address01 = new Address(
        1231,
        "Rua José Renato Hadad",
        "69317-107",
        "Santa Luzia",
        "Boa Vista",
        "Roraima"
    );

    Address address02 = new Address(
        4567,
        "Avenida das Américas",
        "22010-001",
        "Rio de Janeiro",
        "RJ",
        "Rio de Janeiro"
    );

    Address address03 = new Address(
        4567,
        "Avenida das Américas",
        "22010-001",
        "Rio de Janeiro",
        "RJ",
        "Rio de Janeiro"
    );


    Establishment establishment01 = new Establishment(
        "Estacionamento Cardoso",
        "64.931.984/0001-07",
        "47992034358",
        15,
        20,
        address02
    );
    Establishment establishment02 = new Establishment(
        "Posto Carretão 2",
        "36.436.218/0001-30",
        "3653-1231",
        50,
        95,
        address01
    );

    Establishment establishment03 = new Establishment(
        "Eletrica Cardoso",
        "36.436.218/0001-10",
        "3653-1231",
        50,
        95,
        address01
    );

    establishment01.setAddress(address01);
    establishment02.setAddress(address02);
    establishment03.setAddress(address03);

    establishmentRepository.saveAll(Arrays.asList(establishment01, establishment02, establishment03));

  }
}
