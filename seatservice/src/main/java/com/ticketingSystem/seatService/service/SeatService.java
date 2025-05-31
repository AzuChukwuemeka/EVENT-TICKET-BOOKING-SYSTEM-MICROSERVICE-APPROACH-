package com.ticketingSystem.seatService.service;

import com.ticketingSystem.seatService.model.Seat;
import com.ticketingSystem.seatService.repository.SeatRepositoryI;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SeatService {
    private final SeatRepositoryI seatRepository;
    public SeatService(SeatRepositoryI seatRepository) {
        this.seatRepository = seatRepository;
    }
    public Seat getSeat(UUID id){
        return seatRepository.getSeat(id);
    }
    public void updateSeatToBooked(UUID id){
        seatRepository.updateSeatToBooked(id);
    }
    public int updateSeatToAvailable(UUID id){
        return seatRepository.updateSeatToAvailable(id);
    }
    public List<Seat> getPaginatedSeatList(int page, int size){
        return seatRepository.getPaginatedSeatList(page,size);
    }
    public List<Seat> getPaginatedAvailableSeatList(int page, int size){
        return seatRepository.getPaginatedAvailableSeatList(page,size);
    }
}
