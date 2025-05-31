package com.ticketingSystem.seatServiceConsumer.repositories;

import com.ticketingSystem.seatServiceConsumer.model.Venue;

import java.util.UUID;

public interface VenueRepositoryI {
    Venue getVenueById(UUID id);
}
