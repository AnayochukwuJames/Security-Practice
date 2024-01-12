package com.example.securitypractice.service.serviceImp;

import com.example.securitypractice.dto.JwtAuthenticationResponse;
import com.example.securitypractice.dto.LoginRequest;
import com.example.securitypractice.dto.RefreshTokenRequest;
import com.example.securitypractice.dto.SignupRequest;
import com.example.securitypractice.mapper.UserMapper;
import com.example.securitypractice.model.User;
import com.example.securitypractice.repository.UserRepository;
import com.example.securitypractice.service.AuthenticationService;
import com.example.securitypractice.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {
    private final UserRepository userRepository;
    private  AuthenticationManager authenticationManager;
    private final JWTService jwtService;
@Override
    public ResponseEntity<User> signUp(SignupRequest signupRequest){
        if(userRepository.existsByEmail(signupRequest.getEmail())){
            throw new RuntimeException("User with this details has already been registered");
        }
        User user = userRepository.save(UserMapper.mapSignUpRequestToUser(signupRequest));
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    public JwtAuthenticationResponse login(LoginRequest loginRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest
                .getEmail(), loginRequest.getPassword()));
        var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() ->
                new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user));

        var jwt = jwtService.generateToken(user);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());

        return jwtAuthenticationResponse;
    }
//return null;
}
