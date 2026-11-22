package com.sio.backend.patients;

import org.springframework.stereotype.Component;

@Component
public class PatientMapper {
    public PatientDTO toDTO(Patient pt){
        return new PatientDTO(
                pt.getId(),
                pt.getCodiceFiscale(),
                pt.getNome(),
                pt.getCognome(),
                pt.getDataNascita(),
                pt.getSex()
        );
    }

    public Patient toEntity(PatientDTO dto){
        if (dto == null) return null;

        Patient entity = new Patient();
        entity.setId(dto.id());
        entity.setCodiceFiscale(dto.codiceFiscale() != null ? dto.codiceFiscale().toUpperCase().trim() : null);
        entity.setNome(dto.nome());
        entity.setCognome(dto.cognome());
        entity.setDataNascita(dto.dataNascita());
        entity.setSex(dto.sesso());

        return entity;
    }
}
