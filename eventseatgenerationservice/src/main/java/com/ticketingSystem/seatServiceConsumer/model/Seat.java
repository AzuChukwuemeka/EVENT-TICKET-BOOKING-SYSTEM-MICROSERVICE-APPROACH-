package com.ticketingSystem.seatServiceConsumer.model;

import java.sql.Timestamp;
import java.util.UUID;

public record Seat(UUID seatId, UUID eventId, String rowNo, String colNo, String section, Boolean booked, String eventName, Timestamp eventDate, String venueName) {
}
