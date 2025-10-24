package br.com.dev.jpa_docker.exceptions;

import java.time.LocalDateTime;

public record ErrorMessageDTO(String error, int status, String instance, LocalDateTime time) {

}
