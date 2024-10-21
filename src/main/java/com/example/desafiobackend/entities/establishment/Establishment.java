package com.example.desafiobackend.entities.establishment;

import com.example.desafiobackend.entities.address.Address;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "establishment")
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Establishment implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String cnpj;
  private String phone;
  private Integer motorcycleVacancies;
  private Integer carVacancies;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "address_id", referencedColumnName = "id")
  private Address address;

  public Establishment (
      String name,
      String cnpj,
      String phone,
      Integer motorcycleVacancies,
      Integer carVacancies,
      Address address)
  {

    this.name = name;
    this.cnpj = cnpj;
    this.phone = phone;
    this.motorcycleVacancies = motorcycleVacancies;
    this.carVacancies = carVacancies;
    this.address = address;
  }

}
