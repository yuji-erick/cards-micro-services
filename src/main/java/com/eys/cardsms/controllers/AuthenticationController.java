package com.eys.cardsms.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eys.cardsms.utils.JWTUtils;

@RestController
@RequestMapping({"/auth"})
public class AuthenticationController {
	
	private static final String SYSTEMATIC_USER = "SYSTEMATIC_USER";
	
	private final JWTUtils jwtUtils;

    public AuthenticationController(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }
    
    @PostMapping("/token")
    public ResponseEntity<?> login() {
        String token = jwtUtils.generateToken(SYSTEMATIC_USER);
        return ResponseEntity.ok(Map.of("token", token));
    }

}
