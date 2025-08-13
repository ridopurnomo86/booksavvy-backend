package com.booksavvy.server.security;

import com.booksavvy.server.service.SessionCacheService;
import com.booksavvy.server.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final SessionCacheService sessionCacheService;


    public SecurityConfig(
            JwtTokenProvider jwtTokenProvider,
            SessionCacheService sessionCacheService
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.sessionCacheService = sessionCacheService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                auth
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/v1/book").permitAll()
                .anyRequest()
                .authenticated())
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, sessionCacheService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
