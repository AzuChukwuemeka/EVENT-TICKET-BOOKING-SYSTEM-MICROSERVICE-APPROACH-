package com.ticketingSystem.seatService.controller;

import com.ticketingSystem.seatService.model.Seat;
import com.ticketingSystem.seatService.service.SeatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/seat")
public class SeatController {
    private final SeatService seatService;
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }
    @GetMapping("/{id}")
    public Seat getSeat(@PathVariable("id") UUID seat_id){
        return seatService.getSeat(seat_id);
    }
    @GetMapping("/availableSeats")
    public List<Seat> getPaginatedAvailableSeatList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return seatService.getPaginatedAvailableSeatList(page,size);
    }
    @GetMapping("/seats")
    public List<Seat> getPaginatedSeatList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return seatService.getPaginatedSeatList(page,size);
    }
}
