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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "The field 'name' can't be empty.")
  private String name;

  @NotBlank(message = "The field 'CNPJ' can't be empty.")
  private String cnpj;

  @NotBlank(message = "The field 'phone' can't be empty.")
  private String phone;

  @NotNull(message = "The field 'motorcycle vacancies' can't be empty.")
  private Integer motorcycleVacancies;

  @NotNull(message = "The field 'car vacancies' can't be empty.")
  private Integer carVacancies;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "address_id",referencedColumnName = "id")
  private Address address;

  public Establishment (
      String name,
      String cnpj,
      String phone,
      Integer motorcycleVacancies,
      Integer carVacancies)
  {

    this.name = name;
    this.cnpj = cnpj;
    this.phone = phone;
    this.motorcycleVacancies = motorcycleVacancies;
    this.carVacancies = carVacancies;
  }
}
