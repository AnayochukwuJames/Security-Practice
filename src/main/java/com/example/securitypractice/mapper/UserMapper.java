package com.example.securitypractice.mapper;

import com.example.securitypractice.dto.SignupRequest;
import com.example.securitypractice.model.User;

import java.util.Base64;

public class UserMapper {

    public static User mapSignUpRequestToUser(SignupRequest request) {
        String encodedPassword = Base64.getEncoder().encodeToString(request.getPassword().getBytes());
//        System.out.println("Encoded Password: " + encodedPassword)
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(encodedPassword)
                .build();
    }

}