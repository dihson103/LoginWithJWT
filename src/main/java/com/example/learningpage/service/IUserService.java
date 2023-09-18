package com.example.learningpage.service;

import com.example.learningpage.dto.user.UserCreateRequest;
import com.example.learningpage.dto.user.UserDTO;

import java.util.List;

public interface IUserService {

    List<UserDTO> getUsers();

    UserDTO getUser(String username);

    UserDTO createUser(UserCreateRequest userCreateRequest);

    UserDTO getUserByEmail(String email);

}
