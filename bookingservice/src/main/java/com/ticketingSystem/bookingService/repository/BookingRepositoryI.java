package com.ticketingSystem.bookingService.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ticketingSystem.bookingService.model.BookingOutbox;
import com.ticketingSystem.bookingService.model.BookingRecord;

import javax.naming.CannotProceedException;
import java.util.List;
import java.util.UUID;

public interface BookingRepositoryI {
    void saveBookingToDatabase(BookingRecord booking);
    boolean updateBookingStatusToPaid(UUID id);
    void saveBookingToBookingOutBoxTable(BookingOutbox bookingRecord) throws JsonProcessingException;
    void deleteBooking(UUID id);
    void checkSeatHasBeenBooked(UUID id) throws CannotProceedException;
    List<BookingRecord> getBooking(String username);
}
