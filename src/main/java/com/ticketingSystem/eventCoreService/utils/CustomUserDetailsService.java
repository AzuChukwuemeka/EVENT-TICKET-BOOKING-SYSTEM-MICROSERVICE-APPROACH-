package com.ticketingSystem.eventCoreService.utils;

import com.ticketingSystem.eventCoreService.model.UserData;
import com.ticketingSystem.eventCoreService.repository.UserRepositoryI;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepositoryI userRepository;
    public CustomUserDetailsService(UserRepositoryI userRepositoryI) {
        this.userRepository = userRepositoryI;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData user = userRepository.getUserByUsername(username);
        return new User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}
