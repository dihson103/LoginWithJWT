package com.example.learningpage.service;

import com.example.learningpage.dto.auth.AuthenticationRequest;
import com.example.learningpage.dto.auth.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IAuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

    AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
