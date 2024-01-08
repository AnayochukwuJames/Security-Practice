package com.example.securitypractice.service;

import com.example.securitypractice.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;

public interface JWTService {
    String extractUserName (String token);

    String generateToken (UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);

    String generateRefreshToken(Map<String, Object> extraClaim, UserDetails userDetails);
}
