package br.com.dev.jpa_docker.services;

import br.com.dev.jpa_docker.dtos.StudentDTO;
import br.com.dev.jpa_docker.models.House;
import br.com.dev.jpa_docker.models.Student;
import br.com.dev.jpa_docker.repositories.HouseRepository;
import br.com.dev.jpa_docker.repositories.StudentRepository;
import br.com.dev.jpa_docker.utils.API;
import java.net.http.HttpResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Service
public class PopulateStudents {

  @Autowired
  StudentRepository studentRepository;

  @Autowired
  HouseRepository houseRepository;

  @Autowired
  PopulateHouses populateHouses;

  @Autowired
  API api;

  public void execute() {
    List<House> houses = populateHouses.execute();

    final String uri = "https://potterapi-fedeperin.vercel.app/en/characters";
    HttpResponse<String> response = api.execute(uri);

    TypeReference<List<StudentDTO>> typeReference = new TypeReference<List<StudentDTO>>() {
    };

    ObjectMapper objectMapper = new ObjectMapper();

    List<StudentDTO> studentDTOList = objectMapper.readValue(response.body(), typeReference);
    List<Student> students = studentDTOList.stream()
        .map(s -> {
          Student student = new Student(s);

          House studentHouse = houses.stream()
              .filter(h -> h.getHouse().equalsIgnoreCase(s.house()))
              .findFirst().orElse(null);

          student.setHouse(studentHouse);

          return student;
        }).toList();

    studentRepository.saveAll(students);
  }
}
