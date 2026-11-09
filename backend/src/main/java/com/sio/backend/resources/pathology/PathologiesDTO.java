package com.sio.backend.resources.pathology;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO per il trasferimento dei dati riguardanti la Patologia")
public record PathologiesDTO(

        @Schema(
                description = "Codice univoco della patologia",
                example = "C02",
                requiredMode = Schema.RequiredMode.REQUIRED,
                pattern = "C[0-9]{2,}+$"
        )
        @NotBlank(message = "Il codice è obbligatorio")
        String code,

        @Schema(
                description = "Descrizione della patologia",
                example = "Cardiologica",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank
        String description
) {}
