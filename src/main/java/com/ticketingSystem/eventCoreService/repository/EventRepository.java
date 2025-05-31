package com.ticketingSystem.eventCoreService.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketingSystem.eventCoreService.model.Event;
import com.ticketingSystem.eventCoreService.model.EventOutboxEvent;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Repository
public class EventRepository implements EventRepositoryI {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    String EVENT_CREATION_SQL = "INSERT INTO public.tbl_events (" +
            "event_id, event_name, venue_id, on_sale, event_date, normal_seat_prce, timezone, seat_generated, created_by) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?);";
    String GET_EVENT_BY_ID_SQL = "SELECT * FROM public.tbl_events WHERE event_id = ?";
    String GET_EVENT_BY_USER_ID_SQL = "SELECT * FROM public.tbl_events WHERE created_by = ? "+
            "ORDER BY event_name " +
            "limit ? " +
            "offset ?";
    String GET_PAGINATED_EVENT_LIST_SQL = "SELECT * FROM tbl_events " +
            "ORDER BY event_name " +
            "limit ? " +
            "offset ?";
    String DELETE_EVENT_BY_ID = "DELETE FROM public.tbl_events " +
            "WHERE event_id = ?;";
    String UPDATE_EVENT_GENERATION_TRUE = "UPDATE public.tbl_events " +
            "SET seat_generated=true " +
            "WHERE event_id=? ";
    String SAVE_TO_OUTBOX_TABLE = "INSERT INTO public.tbl_event_outbox (" +
            " id, aggregate_type, payload)" +
            " VALUES (?, ?, ?);";
    public EventRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }
    @Override
    public Event saveEvent(Event event, String user) {
        Object[] arguments = {
                event.getEventId(),
                event.getEventName(),
                event.getVenueId(),
                event.getOnSale(),
                event.getEventDate(),
                event.getNormalSeatPrice(),
                event.getTimeZone().toString(),
                event.isSeatGenerated(),
                user
        };
        jdbcTemplate.update(EVENT_CREATION_SQL,arguments);
        return event;
    }
    @Override
    public Event getEventById(UUID id) {
        return jdbcTemplate.queryForObject(
                GET_EVENT_BY_ID_SQL,
                new BeanPropertyRowMapper<>(Event.class),
                new Object[]{id});
    }
    @Override
    public void saveToEventOutboxTable(EventOutboxEvent outboxEvent) throws JsonProcessingException {
        System.out.println("In the Event Outbox saving method");
        Object[] arguments = {
                outboxEvent.id(),
                outboxEvent.aggregateType(),
                objectMapper.writeValueAsString(outboxEvent.payload())
        };
        System.out.println(Arrays.toString(arguments));
        jdbcTemplate.update(SAVE_TO_OUTBOX_TABLE,arguments);
    }

    @Override
    public List<Event> getPaginatedEventList(int page, int size) {
        int offset;
        if(page != 0){
            offset = Math.abs((page - 1) * size);
        }
        offset = 0;
        return jdbcTemplate.query(
                GET_PAGINATED_EVENT_LIST_SQL,
                new BeanPropertyRowMapper<>(Event.class),
                new Object[]{size,offset}
        );
    }

    @Override
    public List<Event> getEventsByUser(String user, int page, int size) {
        int offset;
        if(page != 0){
            offset = Math.abs((page - 1) * size);
        }
        offset = 0;

        return jdbcTemplate.query(
                GET_EVENT_BY_USER_ID_SQL,
                new BeanPropertyRowMapper<>(Event.class),
                new Object[]{user,size,offset}
        );
    }
    @Override
    public boolean deleteEventById(UUID id) {
        return (jdbcTemplate.update(DELETE_EVENT_BY_ID,new Object[]{id}) == 1);
    }
    @Override
    public void updateEventToSuccessfullyGenerated(UUID id) {
        jdbcTemplate.update(UPDATE_EVENT_GENERATION_TRUE,new Object[]{id});
    }
}
