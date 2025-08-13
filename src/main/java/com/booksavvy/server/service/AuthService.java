package com.booksavvy.server.service;

import com.booksavvy.server.dto.auth.AuthResponse;
import com.booksavvy.server.dto.auth.LoginRequest;
import com.booksavvy.server.dto.auth.RegisterRequest;

import com.booksavvy.server.dto.user.UserResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

public interface AuthService {
    void register(RegisterRequest accountRequest);
    AuthResponse login(HttpServletResponse response, LoginRequest auth);
    boolean verifyPassword(String rawPassword, String hashedPassword);
    void logout(Long userId, HttpServletResponse response);
} 
