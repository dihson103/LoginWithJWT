package com.example.learningpage.repositories;

import com.example.learningpage.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @Test
    void getAll() {
        List<UserEntity> list = underTest.getAll();
        assertFalse(list.isEmpty());
    }

    @Test
    void getUserEntityById() {
        Integer userId = 1;
        Optional<UserEntity> user = underTest.getUserEntityById(userId);
        assertTrue(user.isPresent());
    }


}