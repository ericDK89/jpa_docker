package br.com.dev.jpa_docker.services;

import br.com.dev.jpa_docker.dtos.StudentDTO;
import br.com.dev.jpa_docker.models.Student;
import br.com.dev.jpa_docker.repositories.StudentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetStudents {

  @Autowired
  StudentRepository studentRepository;

  public List<StudentDTO> execute(String name) {

    if (name == null || name.isEmpty()) {
      return formatList(studentRepository.findAll());
    }

    return formatList(studentRepository.findByFullNameContainingIgnoreCase(name));
  }

  private List<StudentDTO> formatList(List<Student> students) {
    return students.stream()
        .map(s -> new StudentDTO(
            s.getFullName(),
            s.getHouse().getHouse(),
            s.getBirthday().toString()
        ))
        .toList();
  }
}
