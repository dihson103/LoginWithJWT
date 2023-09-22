package com.example.learningpage.service.impl;

import com.example.learningpage.dto.user.UserCreateRequest;
import com.example.learningpage.dto.user.UserDTO;
import com.example.learningpage.entities.UserEntity;
import com.example.learningpage.exception.DataAlreadyExistException;
import com.example.learningpage.repositories.UserRepository;
import com.example.learningpage.service.IUserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getUsers() {
        return userRepository.getAll().stream().
                map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUser(String username) {
        return userRepository.findByAccount(username)
                .map(user -> modelMapper.map(user, UserDTO.class))
                .orElseThrow(() -> new NoSuchElementException("User not found with username: " + username));
    }

    private UserEntity saveUser(UserEntity user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    private Boolean isEmailExist(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    private Boolean isUsernameExist(String username){
        return userRepository.findByAccount(username).isPresent();
    }

    private Boolean isUserExist(String email, String username){
        return isEmailExist(email) || isUsernameExist(username);
    }

    @Override
    public UserDTO createUser(UserCreateRequest userCreateRequest) {
        if(isUserExist(userCreateRequest.getEmail(), userCreateRequest.getAccount())){
            throw new DataAlreadyExistException("User is already exist. Please to change email or username!");
        }
        UserEntity userEntity = saveUser(modelMapper.map(userCreateRequest, UserEntity.class));
        return modelMapper.map(userEntity, UserDTO.class);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Can not find user"));
        return modelMapper.map(userEntity, UserDTO.class);
    }

}
