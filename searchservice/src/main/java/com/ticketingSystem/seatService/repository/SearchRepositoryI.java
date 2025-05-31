package com.ticketingSystem.seatService.repository;

import com.ticketingSystem.seatService.model.Event;

import java.util.List;

public interface SearchRepositoryI {
    List<Event> searchEvent(String event);
}
