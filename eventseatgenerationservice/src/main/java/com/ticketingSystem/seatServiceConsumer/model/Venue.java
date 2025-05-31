package com.ticketingSystem.seatServiceConsumer.model;


import java.util.UUID;

public class Venue {
    public UUID venue_id;
    public String venue_name;
    public int seat_sections;
    public int seat_rows;
    public int seat_columns;

    public Venue() {
    }
    public Venue(UUID venue_id, String venue_name, int seat_sections, int seat_rows, int seat_columns) {
        this.venue_id = venue_id;
        this.venue_name = venue_name;
        this.seat_sections = seat_sections;
        this.seat_rows = seat_rows;
        this.seat_columns = seat_columns;
    }

    public UUID getVenue_id() {
        return venue_id;
    }

    public void setVenue_id(UUID venue_id) {
        this.venue_id = venue_id;
    }

    public String getVenue_name() {
        return venue_name;
    }

    public void setVenue_name(String venue_name) {
        this.venue_name = venue_name;
    }

    public int getSeat_sections() {
        return seat_sections;
    }

    public void setSeat_sections(int seat_sections) {
        this.seat_sections = seat_sections;
    }

    public int getSeat_rows() {
        return seat_rows;
    }

    public void setSeat_rows(int seat_rows) {
        this.seat_rows = seat_rows;
    }

    public int getSeat_columns() {
        return seat_columns;
    }

    public void setSeat_columns(int seat_columns) {
        this.seat_columns = seat_columns;
    }

}
