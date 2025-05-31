package com.ticketingSystem.seatServiceConsumer.model;

import java.util.UUID;

public record SeatOutboxEvent(UUID id, String aggregateType, String type, Event payload) {
}
