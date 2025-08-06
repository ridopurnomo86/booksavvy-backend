package com.booksavvy.server.service;

import java.time.Duration;
import java.util.List;

public interface RedisService {
    <T> T get(String key, Class<T> clazz);
    <T> void set(String key, T data, Duration ttl);
    <T> List<T> getList(String key, Class<T> clazz);
    void delete(String key);
}
