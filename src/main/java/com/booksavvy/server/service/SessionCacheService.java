package com.booksavvy.server.service;

public interface SessionCacheService {
    void saveSessionCache(Long userId, String timestamp, String token);
    String getSessionTokenCache(Long userId);
    void deleteSessionCache(Long userId);
}
