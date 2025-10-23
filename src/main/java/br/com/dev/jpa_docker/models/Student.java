package br.com.dev.jpa_docker.models;

import br.com.dev.jpa_docker.dtos.StudentDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Data
@Table(name = "students")
@NoArgsConstructor
public class Student {

  @Length(min = 3, max = 255)
  private String fullName;
  private LocalDate birthday;
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "house_id")
  private House house;

  public Student(StudentDTO studentDTO) {
    this.fullName = studentDTO.fullName();
    this.birthday = formatBirthdate(studentDTO.birthdate());
  }

  private LocalDate formatBirthdate(String birthdate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
    return LocalDate.parse(birthdate, formatter);
  }
}
