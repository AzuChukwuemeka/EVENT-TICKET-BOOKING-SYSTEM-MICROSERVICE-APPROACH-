package com.ticketingSystem.seatServiceConsumer.repositories;

import com.ticketingSystem.seatServiceConsumer.model.Event;
import com.ticketingSystem.seatServiceConsumer.model.Seat;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Repository
public class SeatRepositoryImpl implements SeatRepositoryI {
    String SEAT_INSERTION_SQL = "INSERT INTO public.tbl_seats (" +
            "seat_id, row_no, col_no, section, event_id, booked, event_name, event_date, event_venue) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT ON CONSTRAINT unique_seat_constraint DO NOTHING";
    String SEAT_GENERATED_OUTBOX_SQL = "INSERT INTO public.tbl_seat_generated_outbox(" +
            " event_id, payload) " +
            "VALUES (?, ?)";
    String EVENT_BY_ID_CONSUMED_SQL = "SELECT * FROM public.tbl_seat_generated_outbox where event_id = ?";
    private final JdbcTemplate jdbcTemplate;
    public SeatRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void saveSeatBatchToDb(List<Seat> seats) {
        List<Object[]> seatBatchUpdateArguments = getSeatBatchUpdateArguments(seats);
        int[] seats_updated = jdbcTemplate.batchUpdate(SEAT_INSERTION_SQL,seatBatchUpdateArguments);
        int total_rows_updated = Arrays.stream(seats_updated).sum();
        if(total_rows_updated != seats.size()){
            LoggerFactory.getLogger(this.getClass()).warn("Seats were not inserted successfully");
        }
    }

    @Override
    public void saveToSeatOutbox(UUID id, boolean value) {
        jdbcTemplate.update(SEAT_GENERATED_OUTBOX_SQL, new Object[]{id,value});
    }

    @Override
    public Boolean hasEventByIdNotBeenConsumed(UUID id) {
        List<Event> event = jdbcTemplate.query(EVENT_BY_ID_CONSUMED_SQL, new Object[]{id}, new BeanPropertyRowMapper<>(Event.class));
        return event.isEmpty();
    }
    public List<Object[]> getSeatBatchUpdateArguments(List<Seat> seats){
        return seats
                .stream()
                .map(seat -> new Object[]{
                        seat.seatId(),
                        seat.rowNo(),
                        seat.colNo(),
                        seat.section(),
                        seat.eventId(),
                        false,
                        seat.eventName(),
                        seat.eventDate(),
                        seat.venueName()
                })
                .toList();
    }
}
