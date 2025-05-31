package com.ticketingSystem.eventCoreService.model;

import java.util.UUID;

public record EventOutboxEvent(UUID id, String aggregateType, Event payload) {
}
