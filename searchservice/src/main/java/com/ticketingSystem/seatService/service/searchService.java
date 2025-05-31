package com.ticketingSystem.seatService.service;

import com.ticketingSystem.seatService.model.Event;
import com.ticketingSystem.seatService.repository.SearchRepositoryI;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class searchService {
    private final SearchRepositoryI searchRepository;
    public searchService(SearchRepositoryI searchRepository) {
        this.searchRepository = searchRepository;
    }
    public List<Event> getEvent(String event){
        return searchRepository.searchEvent(event);
    }
}
