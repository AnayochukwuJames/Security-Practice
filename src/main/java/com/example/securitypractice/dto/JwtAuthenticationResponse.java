package com.example.securitypractice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class JwtAuthenticationResponse {
    private String token;
    private String refreshToken;
}
