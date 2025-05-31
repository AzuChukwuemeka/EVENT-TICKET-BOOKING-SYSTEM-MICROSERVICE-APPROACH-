package com.ticketingSystem.bookingService.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketingSystem.bookingService.model.BookingOutbox;
import com.ticketingSystem.bookingService.model.BookingRecord;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.naming.CannotProceedException;
import java.util.List;
import java.util.UUID;

@Repository
public class BookingRepositoryImpl implements BookingRepositoryI{
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;
    String BOOKING_INSERT_SQL = "INSERT INTO public.tbl_bookings (" +
            "booking_id, seat_id, username, price, booking_status) " +
            "VALUES (?, ?, ?, ?, ?);";
    String GET_BOOKING = "select * from public.tbl_bookings where username = ?";

    String CHECK_BOOKING_RECORD = "select * from public.tbl_bookings WHERE seat_id=? AND booking_status=true";
    String UPDATE_BOOKING_PAID = "UPDATE public.tbl_bookings " +
            "SET booking_status=true " +
            "WHERE booking_id=?";
    String DELETE_BOOKING_BY_ID = "DELETE FROM public.tbl_bookings " +
            "WHERE booking_id=?;";

    String INSERT_INTO_BOOKING_OUTBOX_TABLE = "INSERT INTO public.tbl_booking_outbox (" +
            "id, aggregate_type, payload) " +
            "VALUES (?, ?, ?);";
    public BookingRepositoryImpl(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }
    @Override
    public void saveBookingToDatabase(BookingRecord booking) {
        Object[] arguments = {booking.bookingId(), booking.seat_id(), booking.username(), booking.price(), booking.bookingStatus()};
        jdbcTemplate.update(BOOKING_INSERT_SQL,arguments);
    }
    @Override
    public boolean updateBookingStatusToPaid(UUID id){
        jdbcTemplate.update(UPDATE_BOOKING_PAID, new Object[]{id});
        return true;
    }
    @Override
    public void saveBookingToBookingOutBoxTable(BookingOutbox bookingOutbox) throws JsonProcessingException {
        Object[] arguments = {
                bookingOutbox.id(),
                bookingOutbox.aggregateType(),
                objectMapper.writeValueAsString(bookingOutbox.bookingRecord()),
        };
        jdbcTemplate.update(INSERT_INTO_BOOKING_OUTBOX_TABLE,arguments);
    }
    @Override
    public void deleteBooking(UUID id) {
        jdbcTemplate.update(DELETE_BOOKING_BY_ID,new Object[]{id});
    }
    @Override
    public void checkSeatHasBeenBooked(UUID id) throws CannotProceedException {
        List<BookingRecord> booking = jdbcTemplate.query(CHECK_BOOKING_RECORD, new BeanPropertyRowMapper<>(BookingRecord.class), new Object[]{id});
        if(!booking.isEmpty()){
            throw new CannotProceedException("Seat Has Already Been Purchased");
        }
    }
    @Override
    public List<BookingRecord> getBooking(String username) {
        return jdbcTemplate.query(GET_BOOKING,new BeanPropertyRowMapper<>(BookingRecord.class),new Object[]{username});
    }
}
