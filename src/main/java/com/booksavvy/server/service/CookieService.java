package com.booksavvy.server.service;

import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface CookieService {
    void setCookie(HttpServletResponse response, String name, String value);
    Optional<String> getCookie(HttpServletRequest request, String name);
    void clearCookie(HttpServletResponse response, String name);
}
