package com.booksavvy.server.service.impl;
import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.booksavvy.server.service.SessionCacheService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SessionCacheServiceImpl implements SessionCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public SessionCacheServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    public void saveLastLogin(Long userId, String timestamp) {
        String key = "session:user:" + userId;

        redisTemplate.opsForHash().put(key, "lastLogin", timestamp);

        redisTemplate.expire(key, Duration.ofHours(24));
    }

    public String getLastLogin(Long userId) {
        String key = "session:user:" + userId;

        return (String) redisTemplate.opsForHash().get(key, "lastLogin");
    }

    public void removeLastLogin(Long userId) {
        String key = "session:user:" + userId;

        redisTemplate.opsForHash().delete(key, "lastLogin");
    }
}
