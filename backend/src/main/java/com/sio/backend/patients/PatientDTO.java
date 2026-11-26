package com.sio.backend.patients;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PatientDTO(
    @NotBlank(message = "Il Codice Fiscale è obbligatorio")
    @Size(max = 16, min = 16, message = "Il Codice Fiscale deve essere di 16 caratteri")
    String codiceFiscale,

    @NotBlank(message = "Il nome è obbligatorio")
    @Size(max = 100, message = "Il nome deve essere lungo massimo 100 caratteri")
    String nome,

    @NotBlank(message = "Il cognome è obbligatorio")
    @Size(max = 100, message = "Il nocognomeme deve essere lungo massimo 100 caratteri")
    String cognome,

    @NotNull(message = "La data di nascita è obbligatoria")
    @PastOrPresent(message = "La data di nascita non può essere nel futuro")
    LocalDate dataNascita,

    @NotNull(message = "Il sesso è obbligatorio")
    PatientSex sex,

    @Size(max = 255, message = "La via può essere lunga massimo 255 caratteri")
    String indirizzoVia,

    @Size(max = 20, message = "Il numero civico può essere lungo massimo 20 caratteri")
    String indirizzoCivico,

    @Size(max = 100, message = "Il comune può essere lungo massimo 100 caratteri")
    String comune,

    @Size(max = 5, message = "La provincia può essre lunga massimo 5 caratteri, usare l'abbreviazione es: Trento -> TN")
    String provincia
) {}
