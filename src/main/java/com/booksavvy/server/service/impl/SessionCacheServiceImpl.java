package com.booksavvy.server.service.impl;

import com.booksavvy.server.service.SessionCacheService;
import jakarta.transaction.Transactional;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Transactional
public class SessionCacheServiceImpl implements SessionCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public SessionCacheServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveSessionCache(Long userId, String timestamp, String token) {
        String key = "session:user:" + userId;

        redisTemplate.opsForHash().put(key, "lastLogin", timestamp);
        redisTemplate.opsForHash().put(key, "token", token);

        redisTemplate.expire(key, Duration.ofHours(6));
    }

    @Override
    public String getSessionTokenCache(Long userId) {
        String key = "session:user:" + userId;

        return (String) redisTemplate.opsForHash().get(key, "token");
    }

    @Override
    public void deleteSessionCache(Long userId) {
        String key = "session:user:" + userId;

        redisTemplate.opsForHash().delete(key, "lastLogin");
    }
}
