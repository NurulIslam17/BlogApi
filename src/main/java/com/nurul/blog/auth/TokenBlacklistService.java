package com.nurul.blog.auth;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistService {
    private final Map<String, Long> blacklist = new ConcurrentHashMap<>();

    public void blacklistToken(String token) {
        blacklist.put(token, System.currentTimeMillis());
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklist.containsKey(token);
    }
}
