package br.com.dev.jpa_docker.services;

import br.com.dev.jpa_docker.dtos.StudentDTO;
import br.com.dev.jpa_docker.models.Student;
import br.com.dev.jpa_docker.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddStudent {

  @Autowired
  StudentRepository studentRepository;

  public void execute(StudentDTO studentDTO) {
    Student student = new Student(studentDTO);
    studentRepository.save(student);
  }
}
