package com.ticketingSystem.eventCoreService.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ticketingSystem.eventCoreService.model.Event;
import com.ticketingSystem.eventCoreService.model.EventOutboxEvent;

import java.util.List;
import java.util.UUID;

public interface EventRepositoryI {
    Event saveEvent(Event event, String user);
    Event getEventById(UUID id);
    void saveToEventOutboxTable(EventOutboxEvent outboxEvent) throws JsonProcessingException;
    List<Event> getPaginatedEventList(int page, int size);
    List<Event> getEventsByUser(String user, int page, int size);
    boolean deleteEventById(UUID uuid);
    void updateEventToSuccessfullyGenerated(UUID id);
}