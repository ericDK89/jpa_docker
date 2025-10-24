package br.com.dev.jpa_docker.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotEmpty;
import java.util.UUID;

public record StudentDTO(
    UUID id,
    @JsonAlias("fullName")
    @NotEmpty
    String fullName,
    @JsonAlias("hogwartsHouse")
    @NotEmpty
    String house,
    @JsonAlias("birthdate")
    @NotEmpty
    String birthdate
) {

}
