package com.booksavvy.server.controller;

import com.booksavvy.server.dto.user.UserResponse;
import com.booksavvy.server.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booksavvy.server.dto.common.Response;
import com.booksavvy.server.dto.auth.AuthResponse;
import com.booksavvy.server.dto.auth.LoginRequest;
import com.booksavvy.server.dto.auth.RegisterRequest;
import com.booksavvy.server.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Duration;


@RestController
@RequestMapping("api/v1/auth")
@Validated
public class AuthController {
    private final AuthService authService;
    private final RateLimiterService rateLimiterService;

    public AuthController(AuthService authService, RateLimiterService rateLimiterService) {
        this.authService = authService;
        this.rateLimiterService = rateLimiterService;
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@Valid @RequestBody LoginRequest auth, HttpServletResponse response, HttpServletRequest request) {
        rateLimiterService.isAllowed(request,10, Duration.ofHours(1));

        AuthResponse authResponse = authService.login(response,auth);

        String token = authResponse.getToken();

        Response responseData = new Response("success", "success", "success", authResponse);
        
        return ResponseEntity.status(HttpStatus.OK)
        .header("Authorization",  "Bearer " + token)
        .body(responseData);
    }
    
    @PostMapping("/register")
    public ResponseEntity<Response> register(@Valid @RequestBody RegisterRequest auth) {
        authService.register(auth);
               
        Response responseData = new Response("success", "success", "success");
        
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Response> logout(@AuthenticationPrincipal UserResponse user,HttpServletResponse response) {

        authService.logout(user.getId(),response);
               
        Response responseData = new Response("success", "success", "success");
        
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }
    
}
