package com.ticketingSystem.eventCoreService.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ticketingSystem.eventCoreService.repository.UserRepositoryI;
import com.ticketingSystem.eventCoreService.utils.JwtServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserRepositoryI userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtServiceImpl jwtService;
    public JwtAuthenticationFilter(UserRepositoryI userRepository, AuthenticationManager authenticationManager, JwtServiceImpl jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        try{
            DecodedJWT decodedJWT = jwtService.isTokenValid(token.substring(7));
            String role = decodedJWT.getClaim("ROLE").asString();
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    decodedJWT.getSubject(),
                    null,
                    Collections.singleton(new SimpleGrantedAuthority(role))
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request,response);
        }catch(RuntimeException e){
            System.out.println(e);
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Token is Expired Or Been Tampered With");
        }
    }
}
