package com.example.learningpage.service;

import com.example.learningpage.dto.auth.AuthenticationRequest;
import com.example.learningpage.dto.auth.AuthenticationResponse;

public interface IAuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
