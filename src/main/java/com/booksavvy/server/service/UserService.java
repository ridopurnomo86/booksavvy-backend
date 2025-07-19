package com.booksavvy.server.service;

import com.booksavvy.server.entity.User;

public interface UserService {
    User findByEmail(String email);
    User saveUser(User user);
    boolean existsByEmail(String email);
}
