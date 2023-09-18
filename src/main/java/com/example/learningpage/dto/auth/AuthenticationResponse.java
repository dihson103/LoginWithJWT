package com.example.learningpage.dto.auth;

import com.example.learningpage.dto.user.UserDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
    private String token;
    private String refreshToken;
    private UserDTO userDTO;
}
