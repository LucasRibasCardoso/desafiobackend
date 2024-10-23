package com.example.desafiobackend.entities.address;

import com.example.desafiobackend.entities.establishment.Establishment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "address")
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Address implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer homeNumber;
  private String street;
  private String zipCode;
  private String neighborhood;
  private String city;
  private String state;


  @JsonIgnore
  @OneToOne(mappedBy = "address")
  private Establishment establishment;

  public Address(
      Integer homeNumber,
      String street,
      String zipCode,
      String neighborhood,
      String state,
      String city
  ) {
    this.homeNumber = homeNumber;
    this.street = street;
    this.zipCode = zipCode;
    this.neighborhood = neighborhood;
    this.state = state;
    this.city = city;
  }
}
