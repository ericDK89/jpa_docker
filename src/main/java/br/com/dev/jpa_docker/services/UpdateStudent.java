package br.com.dev.jpa_docker.services;

import br.com.dev.jpa_docker.dtos.StudentDTO;
import br.com.dev.jpa_docker.exceptions.NotFound;
import br.com.dev.jpa_docker.models.House;
import br.com.dev.jpa_docker.models.Student;
import br.com.dev.jpa_docker.repositories.HouseRepository;
import br.com.dev.jpa_docker.repositories.StudentRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateStudent {

  @Autowired
  StudentRepository studentRepository;

  @Autowired
  HouseRepository houseRepository;

  public void execute(UUID id, StudentDTO studentDTO) {
    Student student = studentRepository.findStudentById(id)
        .orElseThrow(() -> new NotFound(studentDTO.fullName()));

    if (studentDTO.fullName() != null && !studentDTO.fullName().isEmpty()) {
      student.setFullName(studentDTO.fullName());
    }

    if (studentDTO.house() != null && !studentDTO.house().isEmpty()) {
      House house = houseRepository.findByHouse(studentDTO.house())
          .orElseThrow(() -> new NotFound(studentDTO.house()));
      student.setHouse(house);
    }

    if (studentDTO.birthdate() != null && !studentDTO.birthdate().isEmpty()) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
      student.setBirthday(LocalDate.parse(studentDTO.birthdate(), formatter));
    }

    studentRepository.save(student);
  }
}
