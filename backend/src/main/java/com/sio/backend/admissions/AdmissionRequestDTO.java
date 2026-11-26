package com.sio.backend.admissions;

import com.sio.backend.patients.PatientDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AdmissionRequestDTO(
        @Valid
        @NotNull(message = "I dati del paziente sono obbligatori")
        PatientDTO paziente,

        @NotBlank(message = "Il codice della patologia è obbligatorio")
        @Pattern(regexp = "^C[0-9]{2,}+$", message = "Il codice patologia deve rispettare la codifica Cxx")
        String pathologyCode,

        @NotBlank(message = "Il codice della modalità di arrivo è obbligatorio")
        @Size(max = 10)
        String arrivalModeCode,

        @NotBlank(message = "Il codice colore del triage è obbligatorio")
        @Size(max = 20)
        String triageColorCode,

        String noteTriage
) {}
