package br.com.dev.jpa_docker.exceptions;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddStudentsErrorMessage {

  private List<FieldError> errors;

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class FieldError {

    private String field;
    private String message;
  }
}
