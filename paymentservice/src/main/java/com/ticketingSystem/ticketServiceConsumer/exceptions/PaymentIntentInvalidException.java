package com.ticketingSystem.ticketServiceConsumer.exceptions;

public class PaymentIntentInvalidException extends RuntimeException{
    public PaymentIntentInvalidException() {
        super("PaymentIntent Invalid");
    }
}
