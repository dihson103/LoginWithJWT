package com.example.learningpage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(WrongTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleWrongTokenException(WrongTokenException exception){
        Map<String, String> errors = new HashMap<>();
        errors.put("message", exception.getMessage());
        return errors;
    }

    @ExceptionHandler(Exception.class)
    public Map<String, String> handleException(Exception exception){
        Map<String, String> errors = new HashMap<>();
        errors.put("message", exception.getMessage());
        exception.printStackTrace();
        return errors;
    }
}
