package com.sio.backend.admissions;

import com.sio.backend.patients.PatientDTO;
import com.sio.backend.resources.arrivalModes.ArrivalModeDTO;
import com.sio.backend.resources.pathology.PathologyDTO;
import com.sio.backend.resources.triageColors.TriageColorDTO;

import java.time.LocalDateTime;

public record AdmissionResponseDTO(
        Integer id,
        String braccialetto,
        LocalDateTime dataOraIngresso,
        AdmissionStatus stato,
        String noteTriage,
        LocalDateTime updatedAt,
        LocalDateTime dataOraDimissione,
        PatientDTO patient,
        PathologyDTO patologia,
        TriageColorDTO codiceColore,
        ArrivalModeDTO modalitaArrivo
) {}