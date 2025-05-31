package com.ticketingSystem.eventCoreService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ticketingSystem.eventCoreService.DTOs.EventDTORecord;
import com.ticketingSystem.eventCoreService.model.Event;
import com.ticketingSystem.eventCoreService.model.EventOutboxEvent;
import com.ticketingSystem.eventCoreService.model.Venue;
import com.ticketingSystem.eventCoreService.repository.EventRepositoryI;
import com.ticketingSystem.eventCoreService.repository.VenueRepositoryI;
import com.ticketingSystem.eventCoreService.utils.OutboxEventTypes;
import com.ticketingSystem.eventCoreService.utils.UUIDGeneratorI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {
    private final EventRepositoryI eventRepository;
    private final VenueRepositoryI venueRepository;
    private final UUIDGeneratorI uuidGenerator;
    private final Logger logger;
    public EventService(EventRepositoryI eventRepository, VenueRepositoryI venueRepository, UUIDGeneratorI uuidGenerator) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
        this.uuidGenerator = uuidGenerator;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }
    @Transactional
    public ResponseEntity<?> createEvent(EventDTORecord eventDTO, String user) throws JsonProcessingException {
        Venue venue = venueRepository.getVenueById(UUID.fromString(eventDTO.venue_id()));
        Event savedEvent = eventRepository.saveEvent(createEventModel(eventDTO), user);
        savedEvent.setVenueName(venue.venueName);
        savedEvent.setSeatSections(venue.getSeatSections());
        savedEvent.setSeatColumns(venue.getSeatColumns());
        savedEvent.setSeatRows(venue.getSeatRows());
        EventOutboxEvent eventOutboxEvent = createdEventOutboxEvent(savedEvent);
        eventRepository.saveToEventOutboxTable(eventOutboxEvent);
        String generatedURI = "http://localhost:8080/api/v1/event/".concat(savedEvent.getEventId().toString());
        logger.info("Event Created with uuid: ".concat(savedEvent.getEventId().toString()));
        return ResponseEntity
                .created(URI.create(generatedURI))
                .build();
    }

    public Event getEventById(UUID id) {
        return eventRepository.getEventById(id);
    }
    public List<Event> getPaginatedEventList(int page, int size) {
        return eventRepository.getPaginatedEventList(page,size);
    }

    public boolean deleteEventById(UUID uuid) {
        logger.info("Deleted Event with ID: ".concat(uuid.toString()));
        return eventRepository.deleteEventById(uuid);
    }
    private Event createEventModel(EventDTORecord eventDTO) {
        Event createdEvent = new Event(eventDTO);
        createdEvent.setEventId(uuidGenerator.getUUID());
        createdEvent.setSeatGenerated(false);
        return createdEvent;
    }
    public boolean isEventSeatGenerated(UUID id) {
        //Change by calling seat Service to check if seat generated
        return eventRepository.getEventById(id).isSeatGenerated();
    }
    public List<Event> getEventsByUser(String user, int size, int page){
        return eventRepository.getEventsByUser(user, page, size);
    }
    private EventOutboxEvent createdEventOutboxEvent(Event event) {
        return new EventOutboxEvent(uuidGenerator.getUUID(), OutboxEventTypes.eventCreated, event);
    }

}
