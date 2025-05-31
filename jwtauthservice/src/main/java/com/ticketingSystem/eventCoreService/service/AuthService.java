package com.ticketingSystem.eventCoreService.service;

import com.ticketingSystem.eventCoreService.utils.JwtServiceI;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JwtServiceI jwtService;
    public AuthService(JwtServiceI jwtService) {
        this.jwtService = jwtService;
    }
    public void checkJwtValidity(String token) {
        jwtService.isTokenValid(token);
    }
}
