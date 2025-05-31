package com.ticketingSystem.seatServiceConsumer.utils;

import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.temporal.ChronoUnit;

public interface JwtServiceI {
    String generateToken(String username);
    String generateToken(String roleClaim, String username, int add, ChronoUnit chronoUnit);
    DecodedJWT isTokenValid(String token);
}
