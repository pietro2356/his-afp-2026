package com.sio.backend.resources.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TriageColorDTO(
    @NotBlank()
    String code,

    @NotBlank()
    String displayName,

    @NotNull()
    Integer priority,

    @NotBlank()
    String hexValue
){}
