package com.ticketingSystem.bookingService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ticketingSystem.bookingService.DTO.BookingDTORecord;
import com.ticketingSystem.bookingService.DTO.PaymentIntentDTO;
import com.ticketingSystem.bookingService.model.BookingRecord;
import com.ticketingSystem.bookingService.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import javax.naming.CannotProceedException;
import java.util.List;

@RestController
@RequestMapping("api/v1/booking")
public class BookingController {
    private final BookingService bookingService;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    @PostMapping("/book_seat")
    public PaymentIntentDTO bookSeat(@Valid @RequestBody BookingDTORecord bookingDTO, @RequestHeader("Authorization") String user_jwt) throws CannotProceedException, JsonProcessingException {
        String jwtToken = user_jwt.substring(7);
        return bookingService.bookSeat(bookingDTO, jwtToken);
    }
    @GetMapping("/bookings")
    public List<BookingRecord> getUserBooking(@RequestHeader("Authorization") String user_jwt){
        return bookingService.getBookingRecords(user_jwt);
    }
    @PostMapping("/webhook/payment_successful")
    public void paymentSuccessful(@Valid @RequestBody PaymentIntentDTO paymentIntentDTO, @RequestHeader("Authorization") String user_jwt) throws JsonProcessingException {
        String jwtToken = user_jwt.substring(7);
        bookingService.paymentSuccessful(jwtToken, paymentIntentDTO);
    }
    @PostMapping("/webhook/payment_failed")
    public void paymentFailed(@Valid @RequestBody PaymentIntentDTO paymentIntentDTO){
        bookingService.paymentFailed(paymentIntentDTO);
    }
}
