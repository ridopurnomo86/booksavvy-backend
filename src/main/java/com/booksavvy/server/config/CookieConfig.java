package com.booksavvy.server.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class CookieConfig {
    public static final int COOKIE_MAX_AGE = 86400; // 1 day
    public static final String COOKIE_PATH = "/";
    public static final boolean SECURE = true;
    public static final String SAME_SITE = "Strict";
}