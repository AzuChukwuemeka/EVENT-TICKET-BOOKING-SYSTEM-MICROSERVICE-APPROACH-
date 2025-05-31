package com.ticketingSystem.seatService.model;

import java.util.UUID;

public record BookingRecord(UUID bookingId, UUID seatId, String username, String price, Boolean bookingStatus) {
}