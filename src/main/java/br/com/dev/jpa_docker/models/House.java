package br.com.dev.jpa_docker.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "houses")
public class House {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @JsonAlias("house")
  private String house;
  @JsonAlias("founder")
  private String founder;
  @JsonAlias("colors")
  private List<String> colors;
  @JsonAlias("animal")
  private String animal;
  @OneToMany(mappedBy = "house")
  private List<Student> students;
}
