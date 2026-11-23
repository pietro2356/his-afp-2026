package com.sio.backend.resources.triageColors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TriageColorDTO(
        @NotBlank(message = "Il codice è obbligatorio")
        String code,

        @NotBlank
        String displayName,

        @NotNull
        @Positive
        Integer priority,

        @NotBlank
        String hexValue
) {}
