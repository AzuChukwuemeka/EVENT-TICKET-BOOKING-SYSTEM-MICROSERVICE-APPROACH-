package com.ticketingSystem.bookingService.model;

import java.util.UUID;

public record BookingRecord(UUID bookingId, UUID seat_id, String username, String price, Boolean bookingStatus) {
}