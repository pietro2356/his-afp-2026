package com.sio.backend.resources.pathology;

import jakarta.validation.constraints.NotBlank;

public record PathologyDTO(
        @NotBlank(message = "Il codice è obbligatorio")
        String code,

        @NotBlank
        String description
) {}
