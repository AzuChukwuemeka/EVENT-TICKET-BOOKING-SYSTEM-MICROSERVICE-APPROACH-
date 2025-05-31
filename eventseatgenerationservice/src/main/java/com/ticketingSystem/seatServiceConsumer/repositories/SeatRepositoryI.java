package com.ticketingSystem.seatServiceConsumer.repositories;

import com.ticketingSystem.seatServiceConsumer.model.Seat;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SeatRepositoryI {
    public void saveSeatBatchToDb(List<Seat> seats);
    public void saveToSeatOutbox(UUID id, boolean value);
    public Boolean hasEventByIdNotBeenConsumed(UUID id);
}
