package com.ticketingSystem.ticketServiceConsumer.utils;


import com.auth0.jwt.interfaces.DecodedJWT;

public interface JwtServiceI {
    DecodedJWT getDecodedJwt(String token);
}
