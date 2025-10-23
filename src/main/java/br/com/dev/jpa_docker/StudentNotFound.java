package br.com.dev.jpa_docker;

public class StudentNotFound extends RuntimeException {

  public StudentNotFound(String message) {
    super("Student not found: " + message);
  }
}
