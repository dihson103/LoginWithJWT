package com.example.learningpage.controller;

import com.example.learningpage.dto.ApiResponse;
import com.example.learningpage.dto.user.UserCreateRequest;
import com.example.learningpage.dto.user.UserDTO;
import com.example.learningpage.service.IUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final IUserService userService;

    @RequestMapping
    @PreAuthorize("hasAuthority('User')")
    public ApiResponse<List<UserDTO>> getUsers() {
        List<UserDTO> userDTOS = userService.getUsers();
        return ApiResponse.<List<UserDTO>>builder()
                .message("Get users success!")
                .status(HttpStatus.OK)
                .data(userDTOS)
                .build();
    }

    @RequestMapping("{username}")
    @PreAuthorize("hasAuthority('Admin') || #username == authentication.principal")
    public ApiResponse<UserDTO> getUser(@PathVariable String username) {
        UserDTO userDTO = userService.getUser(username);
        return ApiResponse.<UserDTO>builder()
                .message("Get user with username: " + username + " success!")
                .status(HttpStatus.OK)
                .data(userDTO)
                .build();
    }

    public ApiResponse<UserDTO> updateUser(@RequestBody UserCreateRequest user){
        return null;
    }

    @PostMapping()
    public ApiResponse<UserDTO> createUser(@RequestBody @Valid UserCreateRequest user){
        UserDTO userDTO = userService.createUser(user);
        return ApiResponse.<UserDTO>builder()
                .message("Create user success!")
                .status(HttpStatus.OK)
                .data(userDTO)
                .build();
    }

}
