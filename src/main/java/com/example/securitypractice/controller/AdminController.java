package com.example.securitypractice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/vi/admin")
@RequiredArgsConstructor
public class AminController {

    @GetMapping("")
public ResponseEntity <String> Hello(){
        return ResponseEntity.ok("Hello to Admin Dashboard");
    }
}
