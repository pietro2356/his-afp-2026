package com.sio.backend.patients;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class PatientService {
    private final PatientRepository ptRepo;
    private final PatientMapper ptMapper;

    public PatientService(PatientRepository ptRepo, PatientMapper ptMapper){
        this.ptMapper = ptMapper;
        this.ptRepo = ptRepo;
    }

    public List<PatientDTO> getAllPatient(){
        return ptRepo.findAllByOrderByNomeAscNomeAsc()
                .stream()
                .map(ptMapper::toDTO)
                .toList();
    }

    public PatientDTO getPatientById(Integer id){
        Patient pt = ptRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Paziente con ID: " + id + " non trovato"
                ));

        return ptMapper.toDTO(pt);
    }

    public PatientDTO getPatientByCF(String cf){
        Patient patient = ptRepo.findByCodiceFiscaleIgnoreCase(cf.trim())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Nessun paziente trovato con codice fiscale: " + cf
                ));

        return ptMapper.toDTO(patient);
    }
}
