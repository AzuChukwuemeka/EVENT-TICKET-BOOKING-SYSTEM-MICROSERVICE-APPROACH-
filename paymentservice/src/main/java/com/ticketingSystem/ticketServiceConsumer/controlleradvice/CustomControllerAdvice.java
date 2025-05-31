package com.ticketingSystem.ticketServiceConsumer.controlleradvice;

import com.ticketingSystem.ticketServiceConsumer.exceptions.PaymentIntentInvalidException;
import com.ticketingSystem.ticketServiceConsumer.exceptions.PaymentProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomControllerAdvice {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @ExceptionHandler(PaymentIntentInvalidException.class)
    public ResponseEntity<?> handlePaymentIntentInvalidException(PaymentIntentInvalidException ex){
        log.warn(ex.getMessage());
        Map<String, String> response = new HashMap<>();
        response.put("Message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(PaymentProcessingException.class)
    public ResponseEntity<?> handlePaymentProcessingException(PaymentProcessingException ex){
        log.warn(ex.getMessage());
        Map<String, String> response = new HashMap<>();
        response.put("Message", "Couldn't Process Payment");
        response.put("Possible Reasons", "Seat has been booked / Purchasing time exceeded");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleSQLException(Exception ex){
        ex.printStackTrace();
        log.info(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
