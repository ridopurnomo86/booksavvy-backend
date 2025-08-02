package com.booksavvy.server.service.impl;


import com.booksavvy.server.dto.user.UserResponse;
import org.springframework.stereotype.Service;

import com.booksavvy.server.entity.User;
import com.booksavvy.server.exception.UserNotFoundException;
import com.booksavvy.server.repository.UserRepository;
import com.booksavvy.server.service.UserService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("email" + email));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserResponse findByUserId(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("id" + id));

        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setLastLogin(user.getLastLogin());

        return userResponse;
    }

}
