package com.example.securitypractice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class LoginRequest {
    private String email;
    private String password;
}
