package com.ticketingSystem.eventCoreService.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.time.LocalTime;
import java.time.ZoneId;

public record EventDTORecord(
        @NotBlank String event_name,
        @NotNull String venue_id,
        @NotNull Boolean on_sale,
        @NotNull Date event_date,
        @NotNull LocalTime start_time,
        @NotNull int normal_seat_price,
        @NotNull ZoneId timezone) {
}
