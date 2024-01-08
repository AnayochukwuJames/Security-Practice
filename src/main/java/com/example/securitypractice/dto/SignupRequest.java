package com.example.securitypractice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class SignupRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
