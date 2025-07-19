package com.booksavvy.server.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.booksavvy.server.dto.auth.AuthResponse;
import com.booksavvy.server.dto.auth.LoginRequest;
import com.booksavvy.server.dto.auth.RegisterRequest;
import com.booksavvy.server.entity.User;
import com.booksavvy.server.provider.JwtTokenProvider;
import com.booksavvy.server.service.AuthService;
import com.booksavvy.server.service.CookieService;
import com.booksavvy.server.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final String cookieAuthName = "access_token";

    @Autowired
    private final CookieService cookieService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(UserService userService, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, CookieService cookieService) {
        this.userService = userService;
        this.cookieService = cookieService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public AuthResponse login(HttpServletResponse response, LoginRequest auth) {
        User user = userService.findByEmail(auth.getEmail());

        this.verifyPassword(auth.getPassword(), user.getPassword());

        Map<String, Object> userData = new HashMap<>();
        userData.put("user_id", user.getId());
        userData.put("email",user.getEmail());

        String token = jwtTokenProvider.generateToken(userData);
        
        cookieService.setCookie(response, cookieAuthName, token);

        user.setLastLogin(LocalDateTime.now());

        return new AuthResponse(token, "Bearer", user.getEmail());
    }

    @Override
    public void register(RegisterRequest accountRequest) {
        String hashedPassword = passwordEncoder.encode(accountRequest.getPassword());

        User user = new User();
        user.setName(accountRequest.getName());
        user.setEmail(accountRequest.getEmail());
        user.setPassword(hashedPassword);

        userService.saveUser(user);
    }

    @Override
    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        boolean verify = passwordEncoder.matches(rawPassword, hashedPassword);

        if (!verify) throw new BadCredentialsException("invalid Credential");

        return verify;
    }

    @Override
    public void logout(HttpServletResponse response) {
        cookieService.clearCookie(response, cookieAuthName);
    }
}
