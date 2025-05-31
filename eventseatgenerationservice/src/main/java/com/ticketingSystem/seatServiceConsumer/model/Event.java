package com.ticketingSystem.seatServiceConsumer.model;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.UUID;

public class Event {
    public UUID eventId;
    public String eventName;
    public UUID venueId;
    public String venueName;
    public int seatSections;
    public int seatRows;
    public int seatColumns;
    public Boolean onSale;
    public Timestamp eventDate;
    public int normalSeatPrice;
    public ZoneId timeZone;
    public boolean seatGenerated;
    public String createdBy;
    public boolean isSeatGenerated() {
        return seatGenerated;
    }

    public void setSeatGenerated(boolean seatGenerated) {
        this.seatGenerated = seatGenerated;
    }

    public ZoneId getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(ZoneId timeZone) {
        this.timeZone = timeZone;
    }

    public UUID getEventId() {
        return eventId;
    }
    public void setEventId(UUID uuid) {
        this.eventId = uuid;
    }
    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public UUID getVenueId() {
        return venueId;
    }

    public void setVenueId(UUID venueId) {
        this.venueId = venueId;
    }
    public Boolean getOnSale() {
        return onSale;
    }
    public void setOnSale(Boolean onSale) {
        this.onSale = onSale;
    }
    public Timestamp getEventDate() {
        return eventDate;
    }
    public void setEventDate(Timestamp eventDate) {
        this.eventDate = eventDate;
    }

    public int getNormalSeatPrice() {
        return normalSeatPrice;
    }

    public void setNormalSeatPrice(int normalSeatPrice) {
        this.normalSeatPrice = normalSeatPrice;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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
    public Event() {
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", venueId=" + venueId +
                ", onSale=" + onSale +
                ", eventDate=" + eventDate +
                ", normalSeatPrice=" + normalSeatPrice +
                ", timeZone=" + timeZone +
                ", seatGenerated=" + seatGenerated +
                ", user='" + createdBy + '\'' +
                '}';
    }
}