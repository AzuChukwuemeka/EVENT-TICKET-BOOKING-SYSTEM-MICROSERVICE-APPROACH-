package com.ticketingSystem.bookingService.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LocalUUIDGenerator implements UUIDGeneratorI {
    //Use Snowflake Image Later
    @Override
    public UUID getUUID() {
        return UUID.randomUUID();
    }
}
