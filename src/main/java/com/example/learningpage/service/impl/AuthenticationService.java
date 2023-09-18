package com.example.learningpage.service.impl;

import com.example.learningpage.dto.auth.AuthenticationRequest;
import com.example.learningpage.dto.auth.AuthenticationResponse;
import com.example.learningpage.dto.user.UserDTO;
import com.example.learningpage.entities.Role;
import com.example.learningpage.entities.UserEntity;
import com.example.learningpage.repositories.UserRepository;
import com.example.learningpage.service.IAuthenticationService;
import com.example.learningpage.service.IJwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

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

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword()
        ));
        UserEntity user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
        List<Role> roles = user.getRoles();

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        var jwtToken = jwtService.generateToken(user, authorities);
        var jwtRefreshToken = jwtService.generateRefreshToken(user, authorities);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(jwtRefreshToken)
                .userDTO(modelMapper.map(user, UserDTO.class))
                .build();
    }
}
