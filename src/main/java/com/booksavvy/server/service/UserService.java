package com.booksavvy.server.service;


import com.booksavvy.server.dto.user.UserResponse;
import com.booksavvy.server.entity.User;

public interface UserService {
    User findByEmail(String email);
    UserResponse findByUserId(Long id);
    User saveUser(User user);
    boolean existsByEmail(String email);
}
