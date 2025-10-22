package br.com.dev.jpa_docker.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Entity
@Data
public class House {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private final UUID id;

  @JsonAlias("house")
  private final String house;
  @JsonAlias("founder")
  private final String founder;
  @JsonAlias("colors")
  private final List<String> colors;
  @JsonAlias("animal")
  private final String animal;
}
