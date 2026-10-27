package com.sio.backend.resources.ArrivalMode;

import jakarta.validation.constraints.NotBlank;

public record ArrivalModeDTO(
    @NotBlank
    String code,
    @NotBlank
    String description
) {}
