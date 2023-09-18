package com.example.learningpage.service;

import com.example.learningpage.dto.user.UserCreateRequest;
import com.example.learningpage.dto.user.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IUserServiceTest {

    @Autowired
    private IUserService userService;

    @Test
    void createUser() {
        UserCreateRequest userCreateRequest = new UserCreateRequest(
                "dinhson",
                "dinhson@gmail.com",
                "12345",
                "son",
                "Nguyen"
        );
        UserDTO userDTO = userService.createUser(userCreateRequest);
        assertFalse(userDTO.getAccount().equals(null));
    }
}