package com.example.securitypractice.service;

import com.example.securitypractice.dto.JwtAuthenticationResponse;
import com.example.securitypractice.dto.LoginRequest;
import com.example.securitypractice.dto.RefreshTokenRequest;
import com.example.securitypractice.dto.SignupRequest;
import com.example.securitypractice.model.User;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
//    User signup (SignupRequest request);

    ResponseEntity<User> signUp(SignupRequest request);

    JwtAuthenticationResponse login(LoginRequest request);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest request);
}
