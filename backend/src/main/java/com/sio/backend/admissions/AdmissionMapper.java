package com.sio.backend.admissions;

import com.sio.backend.patients.PatientMapper;
import com.sio.backend.resources.arrivalModes.ArrivalModeMapper;
import com.sio.backend.resources.pathology.PathologyMapper;
import com.sio.backend.resources.triageColors.TriageColorMapper;
import org.springframework.stereotype.Component;

@Component
public class AdmissionMapper {

    private final PatientMapper patientMapper;
    private final PathologyMapper pathologyMapper;
    private final TriageColorMapper triageColorMapper;
    private final ArrivalModeMapper arrivalModeMapper;

    public AdmissionMapper(
            PatientMapper patientMapper,
            PathologyMapper pathologyMapper,
            TriageColorMapper triageColorMapper,
            ArrivalModeMapper arrivalModeMapper
    ) {
        this.patientMapper = patientMapper;
        this.pathologyMapper = pathologyMapper;
        this.triageColorMapper = triageColorMapper;
        this.arrivalModeMapper = arrivalModeMapper;
    }

    public AdmissionResponseDTO toDTO(Admission entity) {
        if (entity == null) return null;

        return new AdmissionResponseDTO(
                entity.getId(),
                entity.getBraccialetto(),
                entity.getDataOraIngresso(),
                entity.getStato(),
                entity.getNoteTriage(),
                entity.getUpdatedAt(),
                entity.getDataOraDimissione(),
                patientMapper.toDTO(entity.getPatient()),
                pathologyMapper.toDTO(entity.getPatologia()),
                triageColorMapper.toDTO(entity.getCodiceColore()),
                arrivalModeMapper.toDTO(entity.getModalitaArrivo())
        );
    }
}