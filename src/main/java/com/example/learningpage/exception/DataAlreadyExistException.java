package com.example.learningpage.exception;

public class DataAlreadyExistException extends RuntimeException{
    public DataAlreadyExistException(String message){
        super(message);
    }
}
