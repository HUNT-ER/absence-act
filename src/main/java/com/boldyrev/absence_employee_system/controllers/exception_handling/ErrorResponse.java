package com.boldyrev.absence_employee_system.controllers.exception_handling;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private String message;

    private LocalDateTime timestamp;
}