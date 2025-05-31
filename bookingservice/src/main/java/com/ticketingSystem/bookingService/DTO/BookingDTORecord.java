package com.ticketingSystem.bookingService.DTO;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record BookingDTORecord(UUID seat_id, @NotBlank String price) {
}
