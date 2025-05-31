package com.ticketingSystem.eventCoreService.controller;

import com.ticketingSystem.eventCoreService.model.UserData;
import com.ticketingSystem.eventCoreService.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class UserController {

    private final UserService userServiceImpl;
    public UserController(UserService userService) {
        this.userServiceImpl = userService;
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@CookieValue("refreshtoken") String refreshCookie){
        return userServiceImpl.refreshToken(refreshCookie);
    }
    @PostMapping("/create")
    public UserData createUser(@Valid @RequestBody UserData userData){
        userData.setRole("USER");
        return userServiceImpl.createUser(userData);
    };
}
