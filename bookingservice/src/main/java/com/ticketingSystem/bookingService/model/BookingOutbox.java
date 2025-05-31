package com.ticketingSystem.bookingService.model;

import java.util.UUID;

public record BookingOutbox(UUID id, String aggregateType, BookingRecord bookingRecord) {
}
