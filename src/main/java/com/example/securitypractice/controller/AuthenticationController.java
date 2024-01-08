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

    public ResponseEntity<User> signUp(SignupRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("User already exist");
        }
        User user = userRepository.save(UserMapper.mapSignUpRequestToUser(request));
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
//    public ResponseEntity<User> signup(@RequestBody SignupRequest request) {
//        if(userRepository.existsByEmail(request.getEmail())){
//            throw new RuntimeException("User with this details has already been registered");
//        }
//        User user = new userRepository.save(UserMapper.mapSignUpRequestToUser(request));
//        return new ResponseEntity<>(user, HttpStatus.CREATED);
//        // Return the response directly without involving authenticationService
//        return ResponseEntity.ok(user);
//    }

    //    public ResponseEntity<User> signup(@RequestBody SignupRequest request){
//        return ResponseEntity.ok(authenticationService.signup(request));
//    }
    @PostMapping("login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authenticationService.login(request));
    }
    @PostMapping("refreshToken")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request){
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }

}
