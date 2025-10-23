package br.com.dev.jpa_docker.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalCustomExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

    var addStudentsErrorMessage = AddStudentsErrorMessage.builder().build();

    addStudentsErrorMessage.setErrors(
        ex.getBindingResult()
            .getFieldErrors().stream()
            .map(f -> AddStudentsErrorMessage.FieldError.builder()
                .field(f.getField())
                .message(f.getDefaultMessage())
                .build())
            .toList()
    );

    return new ResponseEntity<>(addStudentsErrorMessage, ex.getStatusCode());
  }

  @ExceptionHandler(NotFound.class)
  public ResponseEntity<Object> handleHouseNotFound(
      NotFound e,
      HttpStatusCode statusCode
  ) {
    ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(e.getMessage(), statusCode.value());
    return new ResponseEntity<>(errorMessageDTO, HttpStatus.NOT_FOUND);
  }
}
