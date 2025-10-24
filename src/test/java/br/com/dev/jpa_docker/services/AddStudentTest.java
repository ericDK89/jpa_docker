package br.com.dev.jpa_docker.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.dev.jpa_docker.dtos.StudentDTO;
import br.com.dev.jpa_docker.exceptions.NotFound;
import br.com.dev.jpa_docker.models.House;
import br.com.dev.jpa_docker.repositories.HouseRepository;
import br.com.dev.jpa_docker.repositories.StudentRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AddStudentTest {

  @Mock
  StudentRepository studentRepository;

  @Mock
  HouseRepository houseRepository;

  @InjectMocks
  AddStudent addStudent;

  @Test
  public void shouldBeAbleToCreateANewStudent() {
    StudentDTO studentDTO = new StudentDTO(
        UUID.randomUUID(),
        "John Targaryen",
        "Slytherin",
        "Jul 02, 2000"
    );

    House house = House.builder()
        .house("Slytherin")
        .colors(List.of("green", "silver"))
        .animal("Snake")
        .founder("Salazar Slytherin")
        .build();

    houseRepository.save(house);

    when(houseRepository.findByHouse("Slytherin")).thenReturn(Optional.of(house));

    addStudent.execute(studentDTO);

    verify(studentRepository).save(argThat(student ->
        student.getFullName().equals("John Targaryen") &&
            student.getHouse().getHouse().equals("Slytherin") &&
            student.getBirthday().toString().equals("2000-07-02") &&
            student.getId() == null
    ));
  }

  @Test
  public void shouldThrowNotFound() {
    StudentDTO studentDTO = new StudentDTO(
        UUID.randomUUID(),
        "John Targaryen",
        "Slytherin",
        "Jul 02, 2000"
    );

    Assertions.assertThrows(NotFound.class, () -> addStudent.execute(studentDTO));
    verify(studentRepository, never()).save(any());
  }

  @Test
  public void shouldThrowNullPointerException() {
    Assertions.assertThrows(NullPointerException.class, () -> addStudent.execute(null));
    verify(studentRepository, never()).save(any());
  }
}
