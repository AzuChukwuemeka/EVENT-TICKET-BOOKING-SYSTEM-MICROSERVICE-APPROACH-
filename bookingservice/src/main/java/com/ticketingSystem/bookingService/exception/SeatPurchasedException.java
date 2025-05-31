package com.ticketingSystem.bookingService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Seat has been booked, purchase time exceeded")
public class SeatPurchasedException extends RuntimeException{
}
