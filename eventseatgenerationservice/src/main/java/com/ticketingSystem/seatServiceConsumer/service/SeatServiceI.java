package com.ticketingSystem.seatServiceConsumer.service;

import com.ticketingSystem.seatServiceConsumer.model.Event;
import com.ticketingSystem.seatServiceConsumer.model.Venue;

import java.util.UUID;

public interface SeatServiceI {
    public void generateSeats(Event eventCreated);
    public Venue getVenueById(UUID id);
}
