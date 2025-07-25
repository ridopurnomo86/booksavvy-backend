package com.booksavvy.server.service;

import java.util.Optional;

import com.booksavvy.server.entity.User;

public interface UserService {
    User findByEmail(String email);
    Optional<User> findByUserId(Long id);
    User saveUser(User user);
    boolean existsByEmail(String email);
}
