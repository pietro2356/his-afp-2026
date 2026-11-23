package com.sio.backend.admissions;

import com.sio.backend.patients.PatientDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AdmissionRequestDTO(
        @NotNull(message = "I dati anagrafici del paziente sono obbligatori")
        @Valid
        PatientDTO patient,

        @NotBlank(message = "Il codice patologia è obbligatorio")
        @Size(max = 10)
        String patologiaCode,

        @NotBlank(message = "Il codice colore triage è obbligatorio")
        @Size(max = 20)
        String codiceColore,

        @NotBlank(message = "La modalità di arrivo è obbligatoria")
        @Size(max = 10)
        String modalitaArrivoCode,

        String noteTriage
) {}