package com.ticketingSystem.eventCoreService.controller;

import com.ticketingSystem.eventCoreService.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @GetMapping("/validate")
    public void checkJwtValidity(@RequestHeader("Authorization") String jwtToken){
        String token = jwtToken.substring(7);
        authService.checkJwtValidity(token);
    }
}
