package com.ticketingSystem.seatService.repository;

import com.ticketingSystem.seatService.model.Seat;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class SeatRepositoryImpl implements SeatRepositoryI {
    String GET_SEAT_BY_ID = "select * from tbl_seats where seat_id = ?";
    String UPDATE_SEAT_BY_ID_BOOKED = "UPDATE public.tbl_seats SET booked=true WHERE seat_id=?";
    String UPDATE_SEAT_BY_ID_AVAILABLE = "UPDATE public.tbl_seats SET booked=false WHERE seat_id=?";
    String GET_PAGINATED_SEAT_LIST_SQL = "SELECT * FROM tbl_seats " +
            "ORDER BY event_name " +
            "limit ? " +
            "offset ?";
    String GET_AVAILABLE_PAGINATED_SEAT_LIST_SQL = "SELECT * FROM tbl_seats where booked=false " +
            "ORDER BY event_name " +
            "limit ? " +
            "offset ?";

    private final JdbcTemplate jdbcTemplate;
    public SeatRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Seat getSeat(UUID id) {
        return jdbcTemplate.queryForObject(GET_SEAT_BY_ID,new BeanPropertyRowMapper<>(Seat.class));
    }
    @Override
    public List<Seat> getPaginatedAvailableSeatList(int page, int size) {
        int offset;
        if(page != 0){
            offset = Math.abs((page - 1) * size);
        }
        offset = 0;
        return jdbcTemplate.query(GET_AVAILABLE_PAGINATED_SEAT_LIST_SQL,new BeanPropertyRowMapper<>(Seat.class),new Object[]{size,offset});
    }
    @Override
    public List<Seat> getPaginatedSeatList(int page, int size) {
        int offset;
        if(page != 0){
            offset = Math.abs((page - 1) * size);
        }
        offset = 0;
        return jdbcTemplate.query(GET_PAGINATED_SEAT_LIST_SQL,new BeanPropertyRowMapper<>(Seat.class),new Object[]{size,offset});
    }
    @Override
    public int updateSeatToBooked(UUID id) {
        return jdbcTemplate.update(UPDATE_SEAT_BY_ID_BOOKED, new Object[]{id});
    }
    @Override
    public int updateSeatToAvailable(UUID id) {
        return jdbcTemplate.update(UPDATE_SEAT_BY_ID_AVAILABLE, new Object[]{id});
    }
}
