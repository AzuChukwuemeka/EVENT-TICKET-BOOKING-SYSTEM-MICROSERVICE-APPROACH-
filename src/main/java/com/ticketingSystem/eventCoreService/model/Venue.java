package com.ticketingSystem.eventCoreService.model;

import com.ticketingSystem.eventCoreService.DTOs.VenueDTORecord;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class Venue {
    public UUID venueId;
    public String venueName;
    public int seatSections;
    public int seatRows;
    public int seatColumns;

    public Venue() {
    }

    public Venue(UUID venue_id, String venue_name, int seat_sections, int seat_rows, int seat_columns) {
        this.venueId = venue_id;
        this.venueName = venue_name;
        this.seatSections = seat_sections;
        this.seatRows = seat_rows;
        this.seatColumns = seat_columns;
    }

    private void isValidClassTypeForSerialization(Class<?> expectedType, Method setter) throws IllegalAccessException, InvocationTargetException {
        if(expectedType.isInstance("randomValue")){
            setter.invoke(this,"sameString as above");
        }
    }

    public Venue(VenueDTORecord venueDTO) {
        this.setSeatColumns(venueDTO.seat_columns());
        this.setSeatRows(venueDTO.seat_rows());
        this.setSeatSections(venueDTO.seat_section());
        this.setVenueName(venueDTO.venue_name());
    }

    public UUID getVenueId() {
        return venueId;
    }

    public void setVenueId(UUID venueId) {
        this.venueId = venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public int getSeatSections() {
        return seatSections;
    }

    public void setSeatSections(int seatSections) {
        this.seatSections = seatSections;
    }

    public int getSeatRows() {
        return seatRows;
    }

    public void setSeatRows(int seatRows) {
        this.seatRows = seatRows;
    }

    public int getSeatColumns() {
        return seatColumns;
    }

    public void setSeatColumns(int seatColumns) {
        this.seatColumns = seatColumns;
    }

}
