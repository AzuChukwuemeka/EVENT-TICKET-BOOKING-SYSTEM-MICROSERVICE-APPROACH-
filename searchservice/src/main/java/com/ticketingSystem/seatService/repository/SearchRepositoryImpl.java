package com.ticketingSystem.seatService.repository;

import com.ticketingSystem.seatService.model.Event;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class SearchRepositoryImpl implements SearchRepositoryI {
    private final JdbcTemplate jdbcTemplate;
    String GET_EVENT_LIKE_SQL = "SELECT * FROM public.tbl_events WHERE event_name LIKE = ?";
    public SearchRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Event> searchEvent(String event) {
        return jdbcTemplate.query(GET_EVENT_LIKE_SQL,new BeanPropertyRowMapper<>(Event.class),new Object[]{event});
    }
}
