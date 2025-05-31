package com.ticketingSystem.bookingService.controlleradvice;

import com.ticketingSystem.bookingService.exception.CannotAcquireSeatLockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.CannotProceedException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomControllerAdvice {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<?> handleDuplicateKeyException(DuplicateKeyException ex){
        log.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(CannotAcquireSeatLockException.class)
    public ResponseEntity<?> handleDuplicateKeyException(CannotAcquireSeatLockException ex){
        log.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Seat is Being Booked");
    }
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> handleDataAccessException(DataAccessException ex){
        log.warn(ex.getMessage());
        Map<String, String> response = new HashMap<>();
        response.put("Message", "Were having Trouble Accessing Data Right Now, Please Try Again Later");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<?> handleSQLException(SQLException ex){
        log.warn(ex.getMessage());
        Map<String, String> response = new HashMap<>();
        response.put("Message", "Were having Trouble Accessing Data Right Now, Please Try Again Later");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(CannotProceedException.class)
    public ResponseEntity<?> handleCannotProceedException(CannotProceedException ex){
        log.warn(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Seat has Already Been Booked");
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleSQLException(Exception ex){
        ex.printStackTrace();
        log.info(ex.getMessage());
        Map<String, String> response = new HashMap<>();
        response.put("Message", "Please Try Again");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
