package br.com.dev.jpa_docker.controllers;

import br.com.dev.jpa_docker.dtos.StudentDTO;
import br.com.dev.jpa_docker.services.AddStudent;
import br.com.dev.jpa_docker.services.DeleteStudent;
import br.com.dev.jpa_docker.services.GetStudents;
import br.com.dev.jpa_docker.services.UpdateStudent;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition(
    info = @Info(title = "JPA_DOCKER Project", version = "1.0.0")
)
@Tag(name = "students_controllers",
    description = "Students controller for get, add, update and delete.")
@RestController
@RequestMapping("/students")
public class StudentsControllers {

  @Autowired
  GetStudents getStudents;

  @Autowired
  AddStudent addStudent;

  @Autowired
  UpdateStudent updateStudent;

  @Autowired
  DeleteStudent deleteStudent;

  @Operation(
      summary = "Get students",
      description = "Retrieves a list of students. Can be filtered by name when provided."
  )
  @Parameter(
      name = "name",
      description = "Filter students by name",
      in = ParameterIn.QUERY,
      schema = @Schema(type = "string", example = "Harry"),
      required = false
  )
  @GetMapping
  public ResponseEntity<List<StudentDTO>> getStudents(
      @RequestParam(value = "name", required = false) String name) {
    var response = getStudents.execute(name);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @Operation(
      summary = "Add student",
      description = "Add a student."
  )
  @io.swagger.v3.oas.annotations.parameters.RequestBody(
      description = "Student object to be created",
      required = true,
      content = @Content(
          schema = @Schema(implementation = StudentDTO.class),
          examples = @ExampleObject(
              name = "Student Example",
              summary = "Example of student creation",
              value = """
                  {
                  "fullName": "John Snow",
                  "house": "Ravenclaw",
                  "birthdate": "Feb 24, 1999"
                  }
                  """
          )
      )
  )
  @PostMapping("/add")
  public ResponseEntity<Void> addStudent(
      @Valid @RequestBody StudentDTO studentDTO) {
    addStudent.execute(studentDTO);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Operation(
      summary = "Update student",
      description = "Update a student."
  )
  @Parameter(
      name = "id",
      required = true,
      description = "UUID of the student to be updated",
      example = "550e8400-e29b-41d4-a716-446655440000",
      schema = @Schema(type = "string", format = "UUID")
  )
  @io.swagger.v3.oas.annotations.parameters.RequestBody(
      description = "Student object to be updated",
      required = true,
      content = @Content(
          schema = @Schema(implementation = StudentDTO.class),
          examples = @ExampleObject(
              name = "Student Example",
              summary = "Example of update a student",
              value = """
                  {
                  "fullName": "John Targaryen",
                  "house": "Slytherin",
                  "birthdate": "Jul 01, 2000"
                  }
                  """
          )
      )
  )
  @PutMapping("/update/{id}")
  public ResponseEntity<Void> updateStudent(@RequestBody StudentDTO studentDTO,
      @PathVariable UUID id) {
    updateStudent.execute(id, studentDTO);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Operation(
      summary = "Delete student",
      description = "Permanently deletes a student by their unique identifier"
  )
  @Parameter(
      name = "id",
      required = true,
      description = "UUID of the student to delete",
      example = "550e8400-e29b-41d4-a716-446655440000",
      schema = @Schema(type = "string", format = "UUID")
  )
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Object> deleteStudent(@PathVariable UUID id) {
    deleteStudent.execute(id);
    return ResponseEntity.noContent().build();
  }
}
