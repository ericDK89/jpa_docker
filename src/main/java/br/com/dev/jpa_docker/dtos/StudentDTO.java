package br.com.dev.jpa_docker.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotEmpty;

public record StudentDTO(
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
