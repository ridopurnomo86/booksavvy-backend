package com.booksavvy.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@Valid @RequestBody LoginRequest auth, HttpServletResponse response) {
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
    public ResponseEntity<Response> logout(HttpServletResponse response) {
        authService.logout(response);
               
        Response responseData = new Response("success", "success", "success");
        
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }
    
}
