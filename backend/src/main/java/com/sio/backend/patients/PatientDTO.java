package com.sio.backend.patients;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
        @PastOrPresent(message = "La data di nascita non può essere futura")
        LocalDate dataNascita,

        @NotNull(message = "Il sesso è obbligatorio")
        PatientSex sex,

        @Size(max = 255)
        String indirizzoVia,

        @Size(max = 20)
        String indirizzoCivico,

        @Size(max = 100)
        String comune,

        @Size(max = 5)
        String provincia
) {}