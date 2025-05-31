package com.ticketingSystem.eventCoreService.controller;

import com.ticketingSystem.eventCoreService.model.Event;
import com.ticketingSystem.eventCoreService.model.UserData;
import com.ticketingSystem.eventCoreService.model.Venue;
import com.ticketingSystem.eventCoreService.service.EventService;
import com.ticketingSystem.eventCoreService.service.UserService;
import com.ticketingSystem.eventCoreService.service.VenueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {
    private final EventService eventService;
    private final VenueService venueService;
    private final UserService userService;
    public AdminController(EventService eventService, VenueService venueService, UserService userService) {
        this.eventService = eventService;
        this.venueService = venueService;
        this.userService = userService;
    }
    @GetMapping("/events")
    public List<Event> getPaginatedEventList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return eventService.getPaginatedEventList(page,size);
    }
    @GetMapping("/venues")
    public List<Venue> getVenueList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return venueService.getPaginatedVenueList(page, size);
    }
    @DeleteMapping("/del/event/{id}")
    public boolean deleteEventById(@PathVariable("id") String id){
        return eventService.deleteEventById(UUID.fromString(id));
    }
    @DeleteMapping("/del/venue/{id}")
    public ResponseEntity<?> deleteVenueById(@PathVariable String id){
        boolean success = venueService.deleteVenueById(UUID.fromString(id));
        return ResponseEntity.ok(success);
    }
    @PostMapping("/ban/user/{id}")
    public boolean banUserById(@PathVariable String id){
        return userService.banUserById(UUID.fromString(id));
    };
    @GetMapping("/search/user")
    public UserData getUser(@RequestParam String user){
        return userService.searchUser(user);
    };
}
