package com.example.learningpage.repositories;

import com.example.learningpage.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT u FROM UserEntity u")
    List<UserEntity> getAll();

    Optional<UserEntity> getUserEntityById(Integer id);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByAccount(String account);

}
