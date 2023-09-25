package com.example.learningpage.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Token")
@Builder
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private Boolean expired;

    private Boolean revoked;

    private Boolean isAccessToken;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
