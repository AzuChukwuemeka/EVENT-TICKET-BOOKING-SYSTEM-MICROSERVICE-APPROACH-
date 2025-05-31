package com.ticketingSystem.eventCoreService.repository;

import com.ticketingSystem.eventCoreService.model.UserData;

import java.util.UUID;

public interface UserRepositoryI {

    UserData createUser(UserData userData);
    UserData getUserById(UUID id);
    UserData getUserByUsername(String username);
    UserData searchUser(String user);
    boolean banUserById(UUID id);
}
