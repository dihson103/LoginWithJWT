package com.example.learningpage.service;

import com.example.learningpage.entities.UserEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

public interface IJwtService {
    String generateToken(UserEntity user, Collection<SimpleGrantedAuthority> authorities);
    String generateRefreshToken(UserEntity user, Collection<SimpleGrantedAuthority> authorities);
}
