package com.ticketingSystem.eventCoreService.model;

import java.util.UUID;

public record SeatGeneratedStatus(UUID event_id, boolean payload) {
}
