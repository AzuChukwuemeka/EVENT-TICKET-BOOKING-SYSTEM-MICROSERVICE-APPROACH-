package com.ticketingSystem.eventCoreService.model;

import com.ticketingSystem.eventCoreService.DTOs.EventDTORecord;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.*;
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

    public Event(EventDTORecord record) {
        this.eventName = record.event_name();
        this.onSale = record.on_sale();
        this.venueId = UUID.fromString(record.venue_id());
        this.normalSeatPrice = record.normal_seat_price();
        this.timeZone = record.timezone();
        //Change date and time to time zone respective timestamps;
        this.eventDate = convertStartTimeToTimestamp(record.event_date(),record.start_time(),record.timezone());
    }

    private Timestamp convertStartTimeToTimestamp(Date eventDate, LocalTime time, ZoneId timezone) {
        LocalDateTime localDateTime = LocalDateTime.of(eventDate.toLocalDate(), time);
        ZonedDateTime zonedDateTime = localDateTime.atZone(timezone);
        return Timestamp.from(zonedDateTime.withZoneSameInstant(ZoneOffset.UTC).toInstant());
    }

    public Event(UUID event_id, String event_name, UUID venue_id, Boolean on_sale, Timestamp event_date, int normal_seat_price, String createdBy) {
        this.eventId = event_id;
        this.eventName = event_name;
        this.venueId = venue_id;
        this.onSale = on_sale;
        this.eventDate = event_date;
        this.normalSeatPrice = normal_seat_price;
        this.createdBy = createdBy;
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