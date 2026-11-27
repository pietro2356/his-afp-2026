package com.sio.backend.admissions;

import com.sio.backend.patients.PatientDTO;
import com.sio.backend.resources.arrivalModes.ArrivalModeDTO;
import com.sio.backend.resources.pathology.PathologyDTO;
import com.sio.backend.resources.triageColors.TriageColorDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(requiredMode = Schema.RequiredMode.REQUIRED)
public record AdmissionResponseDTO(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    Integer id,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String braccialetto,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    Instant dataOraIngresso,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    AdmissionStatus stato,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String noteTriage,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    Instant updatedAt,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    Instant dataOraDimissione,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    PatientDTO paziente,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    PathologyDTO patologia,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    TriageColorDTO triageColor,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    ArrivalModeDTO arrivalMode
) {}
