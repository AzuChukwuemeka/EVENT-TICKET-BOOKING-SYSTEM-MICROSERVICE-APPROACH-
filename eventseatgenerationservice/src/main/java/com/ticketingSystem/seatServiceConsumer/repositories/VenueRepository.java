package com.ticketingSystem.seatServiceConsumer.repositories;

import com.ticketingSystem.seatServiceConsumer.model.Venue;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Repository
public class VenueRepository implements VenueRepositoryI {
    //Going to switch to get venue via restTemplate
    String VENUE_HTTP_GET_REQUEST = "http://localhost:9000/api/v1/venue/";
    private final RestTemplate restTemplate;
    public VenueRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Override
    public Venue getVenueById(UUID id) {
        String venue = VENUE_HTTP_GET_REQUEST.concat(id.toString());
        return restTemplate.getForObject(venue, Venue.class);
    }
}
