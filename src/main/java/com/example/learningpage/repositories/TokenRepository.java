package com.example.learningpage.repositories;

import com.example.learningpage.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("""
        SELECT t FROM Token t WHERE t.user.id = :userId AND (t.expired = false OR t.revoked = false)
    """)
    List<Token> FindAllValidTokenByUser(Integer userId);

    Optional<Token> findByToken(String token);
}
