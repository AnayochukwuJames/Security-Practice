package com.example.securitypractice.controller;

import com.example.securitypractice.dto.JwtAuthenticationResponse;
import com.example.securitypractice.dto.LoginRequest;
import com.example.securitypractice.dto.RefreshTokenRequest;
import com.example.securitypractice.dto.SignupRequest;
import com.example.securitypractice.mapper.UserMapper;
import com.example.securitypractice.model.User;
import com.example.securitypractice.repository.UserRepository;
import com.example.securitypractice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @PostMapping("/sign-up")

    public ResponseEntity<User> signUp(SignupRequest signupRequest){
        if(userRepository.existsByEmail(signupRequest.getEmail())){
            throw new RuntimeException("User already exist");
        }
        User user = userRepository.save(UserMapper.mapSignUpRequestToUser(signupRequest));
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }
    @PostMapping("refreshToken")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }

}
