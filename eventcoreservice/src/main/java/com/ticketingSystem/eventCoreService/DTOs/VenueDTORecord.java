package com.ticketingSystem.eventCoreService.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VenueDTORecord(
        @NotBlank String venue_name,
        @NotNull int seat_section,
        @NotNull int seat_rows,
        @NotNull int seat_columns
) {
}
