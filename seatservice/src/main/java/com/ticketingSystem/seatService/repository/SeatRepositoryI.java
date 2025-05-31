package com.ticketingSystem.seatService.repository;

import com.ticketingSystem.seatService.model.Seat;

import java.util.List;
import java.util.UUID;

public interface SeatRepositoryI {
    Seat getSeat(UUID id);
    List<Seat> getPaginatedAvailableSeatList(int page, int size);
    List<Seat> getPaginatedSeatList(int page, int size);
    int updateSeatToBooked(UUID id);
    int updateSeatToAvailable(UUID id);
}
