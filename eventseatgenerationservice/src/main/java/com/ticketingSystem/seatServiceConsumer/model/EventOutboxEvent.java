package com.ticketingSystem.seatServiceConsumer.model;

import java.util.UUID;

public record EventOutboxEvent(UUID id, String aggregateType, String payload) {
}
