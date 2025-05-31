package com.ticketingSystem.seatServiceConsumer.kafkaEventMessage;

import java.sql.Timestamp;
import java.util.UUID;

public record EventCreated(UUID eventId, UUID venueId, String eventName, Timestamp eventDate, Boolean onSale, int normalSeatPrice) {
}
