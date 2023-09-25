package com.example.learningpage.controller;

import com.example.learningpage.dto.ApiResponse;
import com.example.learningpage.dto.auth.AuthenticationRequest;
import com.example.learningpage.dto.auth.AuthenticationResponse;
import com.example.learningpage.service.IAuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest){
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);
        return ApiResponse.<AuthenticationResponse>builder()
                .message("Authenticate success!")
                .data(authenticationResponse)
                .build();
    }

    @PostMapping("/refresh-access-token")
    public ApiResponse<AuthenticationResponse> refreshAccessToken(HttpServletRequest request,
                                   HttpServletResponse response) throws IOException {
        AuthenticationResponse authenticationResponse = authenticationService.refreshToken(request, response);
        return ApiResponse.<AuthenticationResponse>builder()
                .message("Refresh token success!")
                .data(authenticationResponse)
                .build();
    }
}
