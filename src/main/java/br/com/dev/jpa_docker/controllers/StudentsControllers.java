package br.com.dev.jpa_docker.controllers;

import br.com.dev.jpa_docker.dtos.StudentDTO;
import br.com.dev.jpa_docker.services.AddStudent;
import br.com.dev.jpa_docker.services.GetStudents;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentsControllers {

  @Autowired
  GetStudents getStudents;

  @Autowired
  AddStudent addStudent;

  @GetMapping
  public ResponseEntity<Object> getStudents(
      @RequestParam(value = "name", required = false) String name) {
    try {
      var response = getStudents.execute(name);
      return ResponseEntity.ok().body(response);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/add")
  public ResponseEntity<Object> addStudent(@Valid @RequestBody StudentDTO studentDTO) {
    try {
      addStudent.execute(studentDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body("Student added");
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
