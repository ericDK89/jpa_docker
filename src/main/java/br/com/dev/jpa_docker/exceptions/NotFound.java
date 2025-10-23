package br.com.dev.jpa_docker.exceptions;

public class NotFound extends RuntimeException {

  public NotFound(String message) {
    super("Not found: " + message);
  }
}
