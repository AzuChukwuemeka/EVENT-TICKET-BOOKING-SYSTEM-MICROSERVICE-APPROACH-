package com.ticketingSystem.eventCoreService.filter;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketingSystem.eventCoreService.model.UserData;
import com.ticketingSystem.eventCoreService.utils.JwtServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Optional;

public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtServiceImpl jwtService;
    private final ObjectMapper objectMapper;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public JwtUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtServiceImpl jwtService, ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (request.getMethod().equalsIgnoreCase("post")) {
            UserData userData = null;
            try {
                userData = objectMapper.readValue(request.getInputStream(), UserData.class);
            } catch (IOException e) {
                log.warn(e.getMessage());
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                try {
                    response.getWriter().write(e.getMessage());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(userData.getEmail(), userData.getPassword());
            return authenticationManager.authenticate(authResult);
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain, Authentication
                    authResult
    ) throws IOException, ServletException {
        try{
            String loginJWT = jwtService.generateToken(extractAuthority(authResult), authResult.getName(),1, ChronoUnit.DAYS);
            String refreshJWT = jwtService.generateToken(authResult.getName(), ChronoUnit.DAYS,7);
            Cookie refreshToken = new Cookie("refreshToken", refreshJWT);
            refreshToken.setHttpOnly(true);
            response.setHeader("Authorization","Bearer ".concat(loginJWT));
            response.addCookie(refreshToken);
            response.getWriter().write("Login Successful");
            response.setStatus(HttpServletResponse.SC_OK);
        }catch(JWTCreationException ex){
            log.warn("Error Creating JWT Token");
            throw new RuntimeException("ERROR LOGGING IN USER, PLEASE TRY AGAIN LATER");
        }
    }

    private String extractAuthority(Authentication authResult) {
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        Optional<String> authority = Optional.of(authorities.stream().toList().get(0).getAuthority());
        return authority.orElseThrow(() -> {
            log.warn("User Authority is null");
            return new RuntimeException("Error Logging in User");
        });
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.warn(failed.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        try {
            response.getWriter().write(failed.getMessage());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
