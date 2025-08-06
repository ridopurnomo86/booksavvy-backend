package com.booksavvy.server.service.impl;

import com.booksavvy.server.service.RedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class RedisServiceImpl implements RedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public RedisServiceImpl(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        Object raw = redisTemplate.opsForValue().get(key);
        if (raw == null) return null;
        return objectMapper.convertValue(raw, clazz);
    }

    @Override
    public <T> void set(String key, T data, Duration ttl) {
        redisTemplate.opsForValue().set(key, data, ttl);
    }

    @Override
    public <T> List<T> getList(String key, Class<T> clazz) {
        Object raw = redisTemplate.opsForValue().get(key);
        if (raw == null) return Collections.emptyList();
        return objectMapper.convertValue(
                raw,
                objectMapper.getTypeFactory().constructCollectionType(List.class, clazz)
        );
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
