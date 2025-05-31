package com.ticketingSystem.seatService.model;

import java.util.UUID;

public record BookingOutbox(UUID id, String aggregateType, UUID aggregateId, String type, BookingRecord bookingRecord) {
}
