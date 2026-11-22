package com.sio.backend.patients;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PatientDTO(
        Integer id,

        @NotBlank(message = "Il codice fiscale è obbligatorio")
        @Size(min = 16, max = 16, message = "Il codice fiscale deve essere di 16 caratteri")
        String codiceFiscale,

        @NotBlank(message = "Il nome è obbligatorio")
        @Size(max = 100)
        String nome,

        @NotBlank(message = "Il cognome è obbligatorio")
        @Size(max = 100)
        String cognome,

        @NotNull(message = "La data di nascita è obbligatoria")
        @PastOrPresent(message = "La data di nascita non può essere nel futuro")
        LocalDate dataNascita,

        @NotBlank(message = "Il sesso è obbligatorio")
        @Pattern(regexp = "^[MF]$", message = "Il sesso deve essere M o F")
        String sesso
) {}