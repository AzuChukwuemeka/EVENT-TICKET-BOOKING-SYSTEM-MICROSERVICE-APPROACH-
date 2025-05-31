package com.ticketingSystem.eventCoreService.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ticketingSystem.eventCoreService.model.UserData;
import com.ticketingSystem.eventCoreService.repository.UserRepositoryI;
import com.ticketingSystem.eventCoreService.utils.JwtServiceI;
import com.ticketingSystem.eventCoreService.utils.UUIDGeneratorI;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepositoryI userRepository;
    private final UUIDGeneratorI uuidGeneratorI;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceI jwtService;
    public UserService(UserRepositoryI userRepository, UUIDGeneratorI uuidGeneratorI, PasswordEncoder passwordEncoder, JwtServiceI jwtService) {
        this.userRepository = userRepository;
        this.uuidGeneratorI = uuidGeneratorI;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    public UserData createUser(UserData userData) {
        String password = userData.getPassword();
        userData.setId(uuidGeneratorI.getUUID());
        userData.setPassword(passwordEncoder.encode(password));
        userData.setBanned(false);
        return userRepository.createUser(userData);
    }
    public UserData getUserById(UUID id) {
        return userRepository.getUserById(id);
    }

    public ResponseEntity<?> refreshToken(String value) {
        jwtService.isTokenValid(value);
        DecodedJWT decode = JWT.decode(value);
        String newToken = jwtService.generateToken(decode.getSubject());
        return ResponseEntity.ok("Access Token: ".concat(newToken));
    }

    public UserData searchUser(String user){
        return userRepository.searchUser(user);
    }
    public boolean banUserById(UUID id){
        return userRepository.banUserById(id);
    }
}
