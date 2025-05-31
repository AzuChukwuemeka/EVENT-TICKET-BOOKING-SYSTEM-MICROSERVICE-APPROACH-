package com.ticketingSystem.seatServiceConsumer.service;

import com.ticketingSystem.seatServiceConsumer.model.Event;
import com.ticketingSystem.seatServiceConsumer.model.Seat;
import com.ticketingSystem.seatServiceConsumer.model.Venue;
import com.ticketingSystem.seatServiceConsumer.repositories.SeatRepositoryI;
import com.ticketingSystem.seatServiceConsumer.repositories.VenueRepositoryI;
import com.ticketingSystem.seatServiceConsumer.utils.UUIDGeneratorI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class seatService implements SeatServiceI{
    private final SeatRepositoryI seatRepository;
    private final VenueRepositoryI venueRepository;
    private final UUIDGeneratorI uuidGenerator;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public seatService(SeatRepositoryI seatRepository, VenueRepositoryI venueRepository, UUIDGeneratorI uuidGenerator) {
        this.seatRepository = seatRepository;
        this.venueRepository = venueRepository;
        this.uuidGenerator = uuidGenerator;
    }
    @Override
    public void generateSeats(Event eventCreated) {
        //Could be better include venue details in message information to avoid extra calls counterintuitive to dual write problem, fix later
        if(eventNotBeenProcessed(eventCreated)){
            //Using upserts to handle half committed kafka messages
            generateSeatListAndSaveToDb(eventCreated);
            seatRepository.saveToSeatOutbox(eventCreated.eventId,true);
        }else{
            logger.warn("Replaying Handled Event UUID: ".concat(eventCreated.eventId.toString()));
        }
    }
    private void generateSeatListAndSaveToDb(Event eventCreated) {
        int seat_count = 0;
        int seatSections = eventCreated.getSeatSections();
        int seatRows = eventCreated.getSeatRows();
        int seatColumns = eventCreated.getSeatColumns();
        List<Seat> generatedSeats = new ArrayList<>();
        System.out.println("generating seats");
        for(int section = 1; section <= seatSections; section++){
            for(int rows = 1; rows <= seatRows; rows++){
                for(int columns = 1; columns < seatColumns; columns++){
                    seat_count++;
                    Seat seat = createSeat(section, rows, columns, eventCreated);
                    generatedSeats.add(seat);
                    if((seat_count % 200) == 0){
                        seatRepository.saveSeatBatchToDb(generatedSeats);
                        generatedSeats.clear();
                    }
                }
            }
        }
        if(!generatedSeats.isEmpty()){
            seatRepository.saveSeatBatchToDb(generatedSeats);
        }
    }
    private Seat createSeat(int section, int rows, int columns, Event eventCreated) {
        //Could be better
        return new Seat(
                uuidGenerator.getUUID(),
                eventCreated.eventId,
                Integer.toString(rows),
                Integer.toString(columns),
                getSectionValue(section),
                false,
                eventCreated.eventName,
                eventCreated.eventDate,
                eventCreated.getVenueName()
        );
    }

    private String getSectionValue(int section) {
        if(section <= 26){
            return String.valueOf((char) ('A' + section - 1));
        }else{
            int newSectionValue = section % 26;
            int length_beyond_z = (int) Math.floor(section / 26);
            if(section % 26 == 0){
                return String
                        .valueOf((char) ('A' + 25))
                        .concat(String.valueOf(length_beyond_z - 1));
            }
            return String
                    .valueOf((char) ('A' + Math.abs(newSectionValue - 1)))
                    .concat(String.valueOf(length_beyond_z));
        }
    }
    private boolean eventNotBeenProcessed(Event eventCreated) {
        return Boolean.TRUE.equals(seatRepository.hasEventByIdNotBeenConsumed(eventCreated.eventId));
    }

    public Venue getVenueById(UUID id){
        return venueRepository.getVenueById(id);
    }
}
