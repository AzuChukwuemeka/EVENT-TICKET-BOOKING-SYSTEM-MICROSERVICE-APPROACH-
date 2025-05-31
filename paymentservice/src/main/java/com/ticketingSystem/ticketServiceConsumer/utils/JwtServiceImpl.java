package com.ticketingSystem.ticketServiceConsumer.utils;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class JwtServiceImpl implements JwtServiceI{
    private Environment environment;
    @Override
    public DecodedJWT getDecodedJwt(String token) {
        //Throws UnChecked Exception
        JWTVerifier verifier = com.auth0.jwt.JWT.require(Algorithm.HMAC256(TemporaryJWTKey.secret_key))
                .withIssuer(TemporaryJWTKey.issuer)
                .build();
        return verifier.verify(token);
    }
}
