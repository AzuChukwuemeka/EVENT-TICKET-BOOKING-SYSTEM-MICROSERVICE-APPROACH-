package com.ticketingSystem.eventCoreService.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketingSystem.eventCoreService.filter.JwtAuthenticationFilter;
import com.ticketingSystem.eventCoreService.filter.JwtUsernamePasswordAuthenticationFilter;
import com.ticketingSystem.eventCoreService.repository.UserRepositoryI;
import com.ticketingSystem.eventCoreService.utils.JwtServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {
    private final UserRepositoryI userRepositoryI;

    public SecurityConfig(UserRepositoryI userRepositoryI) {
        this.userRepositoryI = userRepositoryI;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, UserDetailsService customUserDetails) throws Exception {
        // For this to properly work add filter to actually use manager to check and set context
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(NoOpPasswordEncoder.getInstance());
        daoAuthenticationProvider.setUserDetailsService(customUserDetails);
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider);
        return authenticationManagerBuilder.build();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity,
            AuthenticationManager authenticationManager,
            JwtServiceImpl jwtService
    ) throws Exception {
        httpSecurity
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                        }).authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                        })
                );
        httpSecurity
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/api/v1/admin/**")
                            .hasRole("ADMIN")
                            .requestMatchers("/api/v1/auth/**")
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults());
        httpSecurity.addFilterBefore(
                new JwtUsernamePasswordAuthenticationFilter(authenticationManager,jwtService,new ObjectMapper()),
                UsernamePasswordAuthenticationFilter.class
        );
        httpSecurity.addFilterBefore(
                new JwtAuthenticationFilter(userRepositoryI,authenticationManager,jwtService),
                UsernamePasswordAuthenticationFilter.class
        );
        return httpSecurity.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }
}
