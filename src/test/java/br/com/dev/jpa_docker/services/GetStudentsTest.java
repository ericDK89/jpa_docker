package br.com.dev.jpa_docker.services;

import static org.mockito.Mockito.when;

import br.com.dev.jpa_docker.dtos.StudentDTO;
import br.com.dev.jpa_docker.models.House;
import br.com.dev.jpa_docker.models.Student;
import br.com.dev.jpa_docker.repositories.StudentRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GetStudentsTest {

  @Mock
  StudentRepository studentRepository;
  @InjectMocks
  GetStudents getStudents;
  private House house;
  private Student student;

  @BeforeEach
  public void generateData() {
    UUID randomUUID = UUID.randomUUID();
    StudentDTO studentDTO = new StudentDTO(
        randomUUID,
        "John Snow",
        "Ravenclaw",
        "Feb 24, 1999"
    );

    this.student = new Student(studentDTO);

    this.house = House.builder()
        .id(UUID.randomUUID())
        .house("Ravenclaw")
        .animal("Raven")
        .colors(List.of("blue", "yellow"))
        .build();

    this.student.setId(randomUUID);
    this.student.setHouse(this.house);
  }

  private Student createAnotherStudent() {
    UUID randomUUID = UUID.randomUUID();
    StudentDTO studentDTO = new StudentDTO(
        randomUUID,
        "John Targaryen",
        "Slytherin",
        "Jul 02, 2000"
    );

    Student newStudent = new Student(studentDTO);

    House newHouse = House.builder()
        .id(UUID.randomUUID())
        .house("Ravenclaw")
        .animal("Raven")
        .colors(List.of("blue", "yellow"))
        .build();

    newStudent.setId(randomUUID);
    newStudent.setHouse(newHouse);

    return newStudent;
  }

  @Test
  public void shouldReturnAllStudentsWhenNameIsNull() {
    Student newStudent = createAnotherStudent();
    List<Student> expectedStudents = List.of(this.student, newStudent);

    when(studentRepository.findAll()).thenReturn(expectedStudents);

    List<StudentDTO> result = getStudents.execute(null);

    Assertions.assertEquals(2, result.size());
    Assertions.assertTrue(result.stream()
        .anyMatch(dto -> dto.id().equals(this.student.getId())));
    Assertions.assertTrue(result.stream()
        .anyMatch(dto -> dto.id().equals(newStudent.getId())));
  }

  @Test
  public void shouldReturnFilteredStudentsWhenNameIsProvided() {
    when(studentRepository
        .findByFullNameContainingIgnoreCase("John Snow"))
        .thenReturn(List.of(this.student));

    List<StudentDTO> result = getStudents.execute("John Snow");

    Assertions.assertEquals(1, result.size());
    Assertions.assertEquals(this.student.getFullName(), result.get(0).fullName());
    Assertions.assertEquals(this.student.getId(), result.get(0).id());
    Assertions.assertEquals(this.student.getHouse().getHouse(), result.get(0).house());
  }
}
