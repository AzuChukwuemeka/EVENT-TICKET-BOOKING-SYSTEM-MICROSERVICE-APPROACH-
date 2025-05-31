package com.ticketingSystem.eventCoreService.repository;

import com.ticketingSystem.eventCoreService.model.Venue;

import java.util.List;
import java.util.UUID;

public interface VenueRepositoryI {
    Venue createVenue(Venue venue);
    Venue getVenueById(UUID id);
    List<Venue> getPaginatedVenueList(int size, int limit);
    boolean deleteVenueById(UUID id);
}
