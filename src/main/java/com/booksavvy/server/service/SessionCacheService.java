package com.booksavvy.server.service;

public interface SessionCacheService {
    void saveLastLogin(Long userId, String timestamp);
    String getLastLogin(Long userId);
    void removeLastLogin(Long userId);
}
