package br.com.dev.jpa_docker.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.dev.jpa_docker.dtos.StudentDTO;
import br.com.dev.jpa_docker.exceptions.NotFound;
import br.com.dev.jpa_docker.models.House;
import br.com.dev.jpa_docker.models.Student;
import br.com.dev.jpa_docker.repositories.HouseRepository;
import br.com.dev.jpa_docker.repositories.StudentRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UpdateStudentsTest {

  @Mock
  StudentRepository studentRepository;

  @Mock
  HouseRepository houseRepository;

  @InjectMocks
  UpdateStudent updateStudent;

  private Student student;
  private House ravenclaw;
  private House slytherin;

  @BeforeEach
  public void generateData() {
    ravenclaw = House.builder()
        .id(UUID.randomUUID())
        .animal("Raven")
        .founder("Rowena Ravenclaw")
        .house("Ravenclaw")
        .colors(List.of("blue", "yellow"))
        .build();

    slytherin = House.builder()
        .id(UUID.randomUUID())
        .animal("Snake")
        .founder("Salazar Slytherin")
        .house("Slytherin")
        .colors(List.of("green", "silver"))
        .build();

    UUID studentId = UUID.randomUUID();
    StudentDTO studentDTO = new StudentDTO(
        studentId,
        "John Snow",
        "Ravenclaw",
        "Feb 24, 1999"
    );

    this.student = new Student(studentDTO);
    this.student.setId(studentId);
    this.student.setHouse(ravenclaw);
  }

  @Test
  public void shouldBeAbleToFullyUpdateStudent() {
    StudentDTO updateStudentDTO = new StudentDTO(
        null,
        "John Targeryam",
        "Slytherin",
        "Jul 02, 2000"
    );

    when(houseRepository.findByHouse("Slytherin")).thenReturn(Optional.of(this.slytherin));
    when(studentRepository.findStudentById(this.student.getId())).thenReturn(
        Optional.of(this.student));

    when(studentRepository.save(this.student)).thenAnswer(
        invocationOnMock -> invocationOnMock.getArgument(0));

    updateStudent.execute(this.student.getId(), updateStudentDTO);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");

    verify(studentRepository).save(argThat(savedStudent ->
        "John Targeryam" .equals(savedStudent.getFullName()) &&
            slytherin.equals(savedStudent.getHouse()) &&
            LocalDate.parse("Jul 02, 2000", formatter).equals(savedStudent.getBirthday()))
    );
  }

  @Test
  public void shouldThrowIfStudentNotFound() {
    StudentDTO updateStudentDTO = new StudentDTO(
        null,
        "John Targeryam",
        "Slytherin",
        "Jul 02, 2000"
    );

    when(studentRepository.findStudentById(this.student.getId())).thenReturn(Optional.empty());

    Assertions.assertThrows(NotFound.class,
        () -> updateStudent.execute(this.student.getId(), updateStudentDTO));

    verify(studentRepository, never()).save(any());
  }

  @Test
  public void shouldThrowIfHouseNotFound() {
    StudentDTO updateStudentDTO = new StudentDTO(
        null,
        "John Targeryam",
        "NoHouse",
        "Jul 02, 2000"
    );

    when(studentRepository.findStudentById(this.student.getId())).thenReturn(
        Optional.of(this.student));

    when(houseRepository.findByHouse("NoHouse")).thenReturn(Optional.empty());

    Assertions.assertThrows(NotFound.class,
        () -> updateStudent.execute(this.student.getId(), updateStudentDTO));

    verify(studentRepository, never()).save(any());
  }

}
