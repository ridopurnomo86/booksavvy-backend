package com.booksavvy.server.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.booksavvy.server.config.CookieConfig;
import com.booksavvy.server.service.CookieService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CookieServiceImpl implements CookieService {

    @Override
    public void setCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(CookieConfig.COOKIE_MAX_AGE);
        cookie.setPath(CookieConfig.COOKIE_PATH);
        cookie.setHttpOnly(true);
        cookie.setSecure(CookieConfig.SECURE);
        response.addCookie(cookie);
    }


    @Override
    public Optional<String> getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() == null) return Optional.empty();
        
        for (Cookie cookie: request.getCookies()) {
            if (cookie.getName().equals(name)) {
                return Optional.of(cookie.getValue());
            }
        }
        
        return Optional.empty();
    }


    @Override
    public void clearCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
