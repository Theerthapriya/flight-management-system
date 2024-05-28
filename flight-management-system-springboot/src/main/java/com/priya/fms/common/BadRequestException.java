package com.priya.fms.common;

import lombok.Data;

import java.util.List;
@Data
public class BadRequestException extends RuntimeException{

    private List<Error> errors;
    public BadRequestException(String message, List<Error> errors) {
        super(message);
        this.errors = errors;
    }
}
