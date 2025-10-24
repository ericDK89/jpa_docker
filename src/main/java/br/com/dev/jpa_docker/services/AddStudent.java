package br.com.dev.jpa_docker.services;

import br.com.dev.jpa_docker.dtos.StudentDTO;
import br.com.dev.jpa_docker.exceptions.NotFound;
import br.com.dev.jpa_docker.models.House;
import br.com.dev.jpa_docker.models.Student;
import br.com.dev.jpa_docker.repositories.HouseRepository;
import br.com.dev.jpa_docker.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddStudent {

  @Autowired
  StudentRepository studentRepository;

  @Autowired
  HouseRepository houseRepository;

  public void execute(StudentDTO studentDTO) {
    Student student = new Student(studentDTO);
    House house = houseRepository.findByHouse(studentDTO.house())
        .orElseThrow(() -> new NotFound(studentDTO.house()));
    student.setHouse(house);
    studentRepository.save(student);
  }
}
