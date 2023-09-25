package com.example.learningpage.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.learningpage.dto.auth.AuthenticationRequest;
import com.example.learningpage.dto.auth.AuthenticationResponse;
import com.example.learningpage.dto.user.UserDTO;
import com.example.learningpage.entities.Role;
import com.example.learningpage.entities.Token;
import com.example.learningpage.entities.TokenType;
import com.example.learningpage.entities.UserEntity;
import com.example.learningpage.exception.WrongTokenException;
import com.example.learningpage.repositories.TokenRepository;
import com.example.learningpage.repositories.UserRepository;
import com.example.learningpage.service.IAuthenticationService;
import com.example.learningpage.service.IJwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final IJwtService jwtService;
    private final ModelMapper modelMapper;
    private final TokenRepository tokenRepository;

    @Value("${security.secretKey}")
    private String Secret_key;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword()
        ));
        UserEntity user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();

        return createTokenHandle(user);
    }

    @Override
    public AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            throw new WrongTokenException("Token is wrong.");
        }
        refreshToken = authHeader.substring(7);
        Algorithm algorithm = Algorithm.HMAC256(Secret_key.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(refreshToken);
        username = decodedJWT.getSubject();

        UserEntity user = userRepository.findByAccount(username).orElseThrow();

        return createTokenHandle(user);
    }

    private AuthenticationResponse createTokenHandle(UserEntity user){
        List<Role> roles = user.getRoles();

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        var jwtToken = jwtService.generateToken(user, authorities);
        var jwtRefreshToken = jwtService.generateRefreshToken(user, authorities);

        revokedAllTokenBefore(user);
        saveToken(user, jwtToken, true);
        saveToken(user, jwtRefreshToken, false);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(jwtRefreshToken)
                .userDTO(modelMapper.map(user, UserDTO.class))
                .build();
    }

    private void saveToken(UserEntity user, String jwtToken, Boolean isAccessToken){
        var token = Token.builder()
                .token(jwtToken)
                .user(user)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .isAccessToken(isAccessToken)
                .build();
        tokenRepository.save(token);
    }

    private void revokedAllTokenBefore(UserEntity user){
        var validUserTokens = tokenRepository.FindAllValidTokenByUser(user.getId());
        if(validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
