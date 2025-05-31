package com.ticketingSystem.eventCoreService.controlleradvice;

import com.ticketingSystem.eventCoreService.exceptions.JwtNotValidException;
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
    @ExceptionHandler(JwtNotValidException.class)
    public ResponseEntity<?> handleJwtNotValidException(JwtNotValidException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleSQLException(Exception ex){
        ex.printStackTrace();
        Map<String, String> response = new HashMap<>();
        response.put("Message", "Please Try Again");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
