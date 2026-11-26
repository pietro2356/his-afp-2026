package com.sio.backend.admissions;

import com.sio.backend.patients.PatientDTO;
import com.sio.backend.resources.arrivalModes.ArrivalModeDTO;
import com.sio.backend.resources.pathology.PathologyDTO;
import com.sio.backend.resources.triageColors.TriageColorDTO;

import java.time.Instant;

public record AdmissionResponseDTO(
    Integer id,
    String braccialetto,
    Instant dataOraIngresso,
    AdmissionStatus stato,
    String noteTriage,
    Instant updatedAt,
    Instant dataOraDimissione,

    PatientDTO paziente,
    PathologyDTO patologia,
    TriageColorDTO triageColor,
    ArrivalModeDTO arrivalMode
) {}
