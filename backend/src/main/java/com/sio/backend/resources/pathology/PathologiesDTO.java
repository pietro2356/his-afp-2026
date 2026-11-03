package com.sio.backend.resources.pathology;

import jakarta.validation.constraints.NotBlank;

public record PathologiesDTO(
        @NotBlank(message = "Il codice è obbligatorio")
        String code,

        @NotBlank
        String description
) {}
