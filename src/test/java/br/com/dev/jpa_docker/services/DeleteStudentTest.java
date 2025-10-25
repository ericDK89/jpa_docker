package br.com.dev.jpa_docker.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import br.com.dev.jpa_docker.repositories.StudentRepository;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DeleteStudentTest {

  @Mock
  StudentRepository studentRepository;

  @InjectMocks
  DeleteStudent deleteStudent;

  @Test
  public void shouldDeleteStudentById() {
    UUID uuid = UUID.randomUUID();

    deleteStudent.execute(uuid);

    verify(studentRepository, times(1)).deleteById(uuid);
  }
}
