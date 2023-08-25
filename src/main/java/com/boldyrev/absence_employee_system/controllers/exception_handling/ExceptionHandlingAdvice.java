package com.boldyrev.absence_employee_system.controllers.exception_handling;

import com.boldyrev.absence_employee_system.exceptions.EmptyDataException;
import com.boldyrev.absence_employee_system.exceptions.EntityNotFoundException;
import com.boldyrev.absence_employee_system.exceptions.IncorrectSQLResultException;
import jakarta.validation.ValidationException;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlingAdvice {

    @ExceptionHandler({EmptyDataException.class, EntityNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleException(RuntimeException e) {
        log.debug("Error to get entity: {}", e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), LocalDateTime.now()),
            HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IncorrectSQLResultException.class})
    public ResponseEntity<ErrorResponse> handleException(IncorrectSQLResultException e) {
        return ResponseEntity.internalServerError()
            .body(new ErrorResponse("Something went wrong", LocalDateTime.now()));
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ErrorResponse> handleException(ValidationException e) {
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(e.getMessage(), LocalDateTime.now()));
    }
}
