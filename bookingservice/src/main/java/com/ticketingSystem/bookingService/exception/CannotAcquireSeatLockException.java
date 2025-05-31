package com.ticketingSystem.bookingService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Seat is being purchased by someone else")
public class CannotAcquireSeatLockException extends RuntimeException{
}
