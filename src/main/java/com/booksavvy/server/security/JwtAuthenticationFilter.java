package com.booksavvy.server.security;


import com.booksavvy.server.dto.user.UserResponse;
import com.booksavvy.server.service.SessionCacheService;
import com.booksavvy.server.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final SessionCacheService sessionCacheService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, SessionCacheService sessionCacheService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.sessionCacheService = sessionCacheService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String token = jwtTokenProvider.resolveToken(request);

            Claims parseToken = jwtTokenProvider.parseToken(token);

            if (token == null || token.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }

            Long userId = parseToken.get("user_id") instanceof Number ? ((Number) parseToken.get("user_id")).longValue() : null;

            String tokenSessionCache = sessionCacheService.getSessionTokenCache(userId);

            if (!Objects.equals(token, tokenSessionCache)) throw new JwtException("Invalid Token");

            UserResponse userResponse = new UserResponse();
            userResponse.setId(userId);
            userResponse.setEmail((String) parseToken.get("email"));

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userResponse, null, List.of());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException err) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Token Expired\", \"type\": \"error\", \"status\": \"error\"}");
        } catch (JwtException | IllegalArgumentException err) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Invalid token\", \"type\": \"error\", \"status\": \"error\"}");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.startsWith("/api/v1/auth/login") || path.startsWith("/api/v1/auth/register");
    }

}