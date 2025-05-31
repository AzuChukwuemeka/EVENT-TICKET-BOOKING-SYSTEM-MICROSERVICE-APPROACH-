package com.ticketingSystem.eventCoreService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ticketingSystem.eventCoreService.DTOs.EventDTORecord;
import com.ticketingSystem.eventCoreService.model.Event;
import com.ticketingSystem.eventCoreService.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/event")
public class EventController {
    private final EventService eventService;
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@Valid @RequestBody EventDTORecord eventDTO, Authentication authentication) throws JsonProcessingException {
        return eventService.createEvent(eventDTO, authentication.getName());
    }
    @GetMapping("/{id}")
    public Event getEventById(@PathVariable("id") String id){
        return eventService.getEventById(UUID.fromString(id));
    }
    @GetMapping("/events")
    public List<Event> getEventsByUser(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Authentication principal){
        return eventService.getEventsByUser(principal.getName(),page,size);
    }
    @GetMapping("/generated/{id}")
    public boolean isEventSeatGenerated(@PathVariable("id") String id){
        return eventService.isEventSeatGenerated(UUID.fromString(id));
    }
}
