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
//    private  PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private  AuthenticationManager authenticationManager;
    private final JWTService jwtService;
@Override
    public ResponseEntity<User> signUp(SignupRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("User with this details has already been registered");
        }
        User user = userRepository.save(UserMapper.mapSignUpRequestToUser(request));
        return new ResponseEntity<>(user, HttpStatus.CREATED);

//    public User signup (SignupRequest request){
//        User user = new User();
//        user.setFirstName(request.getFirstName());
//        user.setLastName(request.getLastName());
//        user.setEmail(request.getEmail());
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        return userRepository.save(user);
    }
    public JwtAuthenticationResponse login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request
                .getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest request){
        String userEmail = jwtService.extractUserName(request.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if (jwtService.isTokenValid(request.getToken(), user));

        var jwt = jwtService.generateToken(user);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(request.getToken());

        return jwtAuthenticationResponse;
    }
//return null;
}
