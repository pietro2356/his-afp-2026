package com.sio.backend.resources.pathology;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Data Transfer Object (DTO) in formato record per il trasferimento
 * dei dati relativi alle patologie cliniche nel sistema SIO.
 */
@Schema(description = "DTO per il trasferimento dei dati riguardanti la Patologia")
public record PathologyDTO(

        @Schema(
                description = "Codice univoco della patologia",
                example = "C02",
                requiredMode = Schema.RequiredMode.REQUIRED,
                pattern = "C[0-9]{2,}+$"
        )
        @NotBlank(message = "Il codice è obbligatorio")
        @Pattern(regexp = "^C[0-9]{2,}+$", message = "Il codice patologia deve rispettare la codifica Cxx")
        String code,

        @Schema(
                description = "Descrizione della patologia",
                example = "Cardiologica",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank
        String description
) {}
