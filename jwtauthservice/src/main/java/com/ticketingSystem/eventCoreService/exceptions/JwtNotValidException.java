package com.ticketingSystem.eventCoreService.exceptions;

public class JwtNotValidException extends RuntimeException{
    public JwtNotValidException() {
        super("Jwt Invalid Exception");
    }
}
