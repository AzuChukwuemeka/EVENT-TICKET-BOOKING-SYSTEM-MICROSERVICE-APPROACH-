package com.ticketingSystem.seatService.controller;

import com.ticketingSystem.seatService.model.Event;
import com.ticketingSystem.seatService.service.searchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/search")
public class searchController {
    private final searchService searchService;
    public searchController(searchService searchService) {
        this.searchService = searchService;
    }
    @GetMapping("/search/{event}")
    public List<Event> getEvent(@PathVariable("event") String event){
        return searchService.getEvent(event);
    }
}
