package com.example.learningpage.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.util.List;

@Builder
public record ApiResponse<T>(
        String message,
        HttpStatus status,
        T data
) {

}
