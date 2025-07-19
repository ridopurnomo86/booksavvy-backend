package com.booksavvy.server.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String token_type = "Bearer";
    private String email;
}
