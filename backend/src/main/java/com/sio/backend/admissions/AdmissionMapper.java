package com.sio.backend.admissions;

import com.sio.backend.patients.PatientMapper;
import com.sio.backend.resources.arrivalModes.ArrivalModeMapper;
import com.sio.backend.resources.pathology.PathologyMapper;
import com.sio.backend.resources.triageColors.TriageColorMapper;
import org.springframework.stereotype.Component;

@Component
class AdmissionMapper {
    private final PatientMapper ptMapper;
    private final PathologyMapper pathoMapper;
    private final TriageColorMapper tcMapper;
    private final ArrivalModeMapper amMapper;

    public AdmissionMapper(
            PatientMapper ptMapper,
            PathologyMapper pathoMapper,
            TriageColorMapper tcMapper,
            ArrivalModeMapper amMapper
    ){
        this.ptMapper = ptMapper;
        this.pathoMapper = pathoMapper;
        this.tcMapper = tcMapper;
        this.amMapper = amMapper;
    }

    public AdmissionResponseDTO toDTO(Admission adm){
        if (adm == null) return null;

        return new AdmissionResponseDTO(
                adm.getId(),
                adm.getBraccialetto(),
                adm.getDataOraIngresso(),
                adm.getStato(),
                adm.getNoteTriage(),
                adm.getUpdatedAt(),
                adm.getDataOraDimissione(),

                ptMapper.toDTO(adm.getPatient()),
                pathoMapper.toDTO(adm.getPatologiaCode()),
                tcMapper.toDTO(adm.getCodiceColore()),
                amMapper.toDTO(adm.getModalitaArrivoCode())
        );
    }
}
