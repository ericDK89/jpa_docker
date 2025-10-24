package br.com.dev.jpa_docker.services;

import br.com.dev.jpa_docker.repositories.StudentRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteStudent {

  @Autowired
  StudentRepository studentRepository;

  public void execute(UUID id) {
    try {
      studentRepository.deleteById(id);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException();
    }
  }
}
