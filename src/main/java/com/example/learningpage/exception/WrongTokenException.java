package com.example.learningpage.exception;

public class WrongTokenException extends RuntimeException{

    public WrongTokenException(String error){
        super(error);
    }
}
