package com.boldyrev.absence_employee_system.controllers.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class CustomResponse {

    private Object body;

    private String message;

    public CustomResponse(Object body) {
        this.body = body;
    }

    public CustomResponse(String message) {
        this.message = message;
    }

    public CustomResponse(Object body, String message) {
        this.body = body;
        this.message = message;
    }
}

