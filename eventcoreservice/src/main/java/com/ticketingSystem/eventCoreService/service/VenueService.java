package com.ticketingSystem.eventCoreService.service;

import com.ticketingSystem.eventCoreService.DTOs.VenueDTORecord;
import com.ticketingSystem.eventCoreService.model.Venue;
import com.ticketingSystem.eventCoreService.repository.VenueRepositoryI;
import com.ticketingSystem.eventCoreService.utils.UUIDGeneratorI;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Service
public class VenueService {
    private final VenueRepositoryI venueRepositoryI;
    private final UUIDGeneratorI uuidGeneratorI;
    public VenueService(VenueRepositoryI venueRepositoryI, UUIDGeneratorI uuidGeneratorI) {
        this.venueRepositoryI = venueRepositoryI;
        this.uuidGeneratorI = uuidGeneratorI;
    }
    public ResponseEntity<?> createVenue(VenueDTORecord venue ){
        Venue createdVenue = getCreatedVenue(venue);
        createdVenue.setVenueId(uuidGeneratorI.getUUID());
        venueRepositoryI.createVenue(createdVenue);
        String GeneratedURI = "http://localhost:8080/api/v1/venue/".concat(String.valueOf(createdVenue.getVenueId()));
        return ResponseEntity
                .created(URI.create(GeneratedURI))
                .build();
    }

    private Venue getCreatedVenue(VenueDTORecord venue) {
        return new Venue(venue);
    }

    public Venue getVenueById(UUID id) {
        return venueRepositoryI.getVenueById(id);
    }
    public List<Venue> getPaginatedVenueList(int size, int limit) {
        return venueRepositoryI.getPaginatedVenueList(size,limit);
    }
    public boolean deleteVenueById(UUID id) {
        return venueRepositoryI.deleteVenueById(id);
    }
}
