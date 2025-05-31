package com.ticketingSystem.eventCoreService.controller;

import com.ticketingSystem.eventCoreService.DTOs.VenueDTORecord;
import com.ticketingSystem.eventCoreService.model.Venue;
import com.ticketingSystem.eventCoreService.service.VenueService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/venue")
public class VenueController {
    private final VenueService venueService;
    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }
    @PostMapping("/create")
    public ResponseEntity<?> createVenue(@Valid @RequestBody VenueDTORecord venue){
        return venueService.createVenue(venue);
    };
    @GetMapping("/{id}")
    public Venue getVenueById(@PathVariable String id){
        return venueService.getVenueById(UUID.fromString(id));
    };
    @GetMapping("/venues")
    public List<Venue> getVenueList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return venueService.getPaginatedVenueList(page, size);
    }
}