package com.booksavvy.server.service;

import com.booksavvy.server.dto.auth.AuthResponse;
import com.booksavvy.server.dto.auth.LoginRequest;
import com.booksavvy.server.dto.auth.RegisterRequest;

import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    void register(RegisterRequest accountRequest);
    AuthResponse login(HttpServletResponse response, LoginRequest auth);
    boolean verifyPassword(String rawPassword, String hashedPassword);
    void logout(HttpServletResponse response);
} 
