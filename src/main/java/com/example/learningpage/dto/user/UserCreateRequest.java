package com.example.learningpage.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest{

    @NotBlank(message = "Username can not be blank!")
    private String account;

    @Email(message = "Email is not valid!")
    private String email;

    @NotBlank(message = "Password can not be blank!")
    private String password;

    @NotBlank(message = "Firstname can not be blank!")
    private String firstName;

    @NotBlank(message = "Lastname can not be blank!")
    private String lastName;
}
