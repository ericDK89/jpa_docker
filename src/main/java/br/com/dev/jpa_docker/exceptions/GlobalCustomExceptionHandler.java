package br.com.dev.jpa_docker.exceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
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

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorMessageDTO> handleIllegalArgumentException(
      IllegalArgumentException e, WebRequest webRequest) {
    ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(e.getMessage(),
        HttpStatus.BAD_REQUEST.value(),
        webRequest.getDescription(true).replace("uri=", ""),
        LocalDateTime.now()
    );
    return new ResponseEntity<>(errorMessageDTO, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotFound.class)
  public ResponseEntity<ErrorMessageDTO> handleHouseNotFound(
      NotFound e,
      WebRequest webRequest
  ) {
    ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(e.getMessage(),
        HttpStatus.NOT_FOUND.value(),
        webRequest.getDescription(true).replace("uri=", ""),
        LocalDateTime.now());
    return new ResponseEntity<>(errorMessageDTO, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(DateTimeParseException.class)
  public ResponseEntity<ErrorMessageDTO> handleDateTimeParseException(
      DateTimeParseException e,
      WebRequest webRequest
  ) {
    ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(e.getMessage(),
        HttpStatus.BAD_REQUEST.value(),
        webRequest.getDescription(false).replace("uri=", ""),
        LocalDateTime.now());
    return new ResponseEntity<>(errorMessageDTO, HttpStatus.BAD_REQUEST);
  }
}
