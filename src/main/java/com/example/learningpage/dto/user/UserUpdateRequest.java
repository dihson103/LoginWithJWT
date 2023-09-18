package com.example.learningpage.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    @NotBlank(message = "Username can not be blank!")
    private String account;

    @NotBlank(message = "Password can not be blank!")
    private String email;

    @NotBlank(message = "Firstname can not be blank!")
    private String firstName;

    @NotBlank(message = "Lastname can not be blank!")
    private String lastName;
}
