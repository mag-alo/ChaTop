package com.openclassrooms.chatop_backend.security;

import org.springframework.stereotype.Component;

@Component
public class JwtHelper {
    private final JwtUtil jwtUtil;

    public JwtHelper(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public String extractEmailFromAuthHeader(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            return jwtUtil.extractEmail(jwt);
        }
        return null;
    }
}