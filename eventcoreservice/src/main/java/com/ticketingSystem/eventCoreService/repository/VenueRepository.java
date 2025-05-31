package com.ticketingSystem.eventCoreService.repository;

import com.ticketingSystem.eventCoreService.model.Venue;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class VenueRepository implements VenueRepositoryI {
    private final JdbcTemplate jdbcTemplate;

    String VENUE_CREATION_SQL = "INSERT INTO public.tbl_venues( " +
            "venue_name, venue_id, seat_sections, seat_rows, seat_columns) " +
            "VALUES (?, ?, ?, ?, ?);";
    String GET_VENUE_BY_ID_SQL = "SELECT * " +
            "FROM public.tbl_venues WHERE venue_id = ?;";
    String GET_PAGINATED_VENUE_LIST_SQL = "SELECT * FROM tbl_venues " +
            "ORDER BY venue_name " +
            "limit ? " +
            "offset ?";
    String DELETE_VENUE_BY_ID = "DELETE FROM public.tbl_venues " +
            "WHERE venue_id = ?;";

    public VenueRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Venue createVenue(Venue venue) {
        Object[] arguments = {venue.getVenueName(),venue.getVenueId(),venue.getSeatSections(),venue.getSeatRows(),venue.getSeatColumns()};
        jdbcTemplate.update(VENUE_CREATION_SQL,arguments);
        return venue;
    }

    @Override
    public Venue getVenueById(UUID id) {
        return jdbcTemplate.queryForObject(GET_VENUE_BY_ID_SQL,new BeanPropertyRowMapper<>(Venue.class),new Object[]{id});
    }

    @Override
    public List<Venue> getPaginatedVenueList(int page, int size) {
        int offset;
        if(page != 0){
            offset = Math.abs((page - 1) * size);
        }
        offset = 0;
        return jdbcTemplate.query(
                GET_PAGINATED_VENUE_LIST_SQL,
                new BeanPropertyRowMapper<>(Venue.class),
                new Object[]{size,offset}
        );
    }

    @Override
    public boolean deleteVenueById(UUID id) {
        return (jdbcTemplate.update(DELETE_VENUE_BY_ID, new Object[]{id}) == 1);
    }
}
