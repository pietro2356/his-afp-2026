package com.sio.backend.patients;

import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public PatientDTO toDTO(Patient entity) {
        if (entity == null) return null;

        return new PatientDTO(
                entity.getId(),
                entity.getCodiceFiscale(),
                entity.getNome(),
                entity.getCognome(),
                entity.getDataNascita(),
                entity.getSex(),
                entity.getIndirizzoVia(),
                entity.getIndirizzoCivico(),
                entity.getComune(),
                entity.getProvincia()
        );
    }

    public Patient toEntity(PatientDTO dto) {
        if (dto == null) return null;

        Patient entity = new Patient();
        entity.setId(dto.id());
        entity.setCodiceFiscale(dto.codiceFiscale() != null ? dto.codiceFiscale().toUpperCase().trim() : null);
        entity.setNome(dto.nome());
        entity.setCognome(dto.cognome());
        entity.setDataNascita(dto.dataNascita());
        entity.setSex(dto.sex() != null ? dto.sex() : PatientSex.M);
        entity.setIndirizzoVia(dto.indirizzoVia());
        entity.setIndirizzoCivico(dto.indirizzoCivico());
        entity.setComune(dto.comune());
        entity.setProvincia(dto.provincia());
        return entity;
    }
}