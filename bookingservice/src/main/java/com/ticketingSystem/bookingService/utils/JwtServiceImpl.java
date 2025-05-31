package com.ticketingSystem.bookingService.utils;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtServiceImpl implements JwtServiceI{
    private Environment environment;
    @Override
    public String generateToken(String username) {
        return com.auth0.jwt.JWT.create()
                .withIssuer(TemporaryJWTKey.issuer)
                .withSubject(username)
                .withIssuedAt(Instant.now())
                .withExpiresAt(Date.from(Instant.now().plus(15, ChronoUnit.DAYS)))
                .sign(Algorithm.HMAC256(TemporaryJWTKey.secret_key));
    }
    public String generateToken(String username, ChronoUnit chronoUnit, int add) {
        return com.auth0.jwt.JWT.create()
                .withIssuer(TemporaryJWTKey.issuer)
                .withSubject(username)
                .withIssuedAt(Instant.now())
                .withExpiresAt(Date.from(Instant.now().plus(add, chronoUnit)))
                .sign(Algorithm.HMAC256(TemporaryJWTKey.secret_key));
    }
    @Override
    public String generateToken(String roleClaim, String username, int add, ChronoUnit chronoUnit) {
        return com.auth0.jwt.JWT.create()
                .withIssuer(TemporaryJWTKey.issuer)
                .withSubject(username)
                .withClaim("ROLE",roleClaim)
                .withIssuedAt(Instant.now())
                .withExpiresAt(Date.from(Instant.now().plus(add, chronoUnit)))
                .sign(Algorithm.HMAC256(TemporaryJWTKey.secret_key));
    }
    @Override
    public DecodedJWT isTokenValid(String token) {
        //Throws UnChecked Exception
        JWTVerifier verifier = com.auth0.jwt.JWT.require(Algorithm.HMAC256(TemporaryJWTKey.secret_key))
                .withIssuer(TemporaryJWTKey.issuer)
                .build();
        return verifier.verify(token);
    }
}
