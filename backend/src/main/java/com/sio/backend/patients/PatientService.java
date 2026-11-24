package com.sio.backend.patients;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
class PatientService {
    private final PatientRepository repo;
    private final PatientMapper mapper;

    public PatientService(PatientRepository repo, PatientMapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }

    public PatientDTO getPatientByCodiceFiscale(String cf){
        Patient ptTmp = repo.findByCodiceFiscaleIgnoreCase(cf)
                .orElseThrow(() -> new EntityNotFoundException("Paziente con CF: " + cf +" non trovato" ));

        return mapper.toDTO(ptTmp);
    }

    /**
     *
     */
    public List<PatientDTO> getPatientByNominativo(String nome, String cognome, LocalDate dataNascita, PatientSex sex){
        return repo.findByNomeIgnoreCaseAndCognomeIgnoreCaseAndDataNascitaAndSex(
                nome,
                cognome,
                dataNascita,
                sex
        )
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional
    public Patient upsertPatient(PatientDTO pt){
        return repo.findByCodiceFiscaleIgnoreCase(pt.codiceFiscale().toUpperCase().trim())
                .map(existPz -> {
                    existPz.setNome(pt.nome());
                    existPz.setCognome(pt.cognome());
                    existPz.setDataNascita(pt.dataNascita());
                    existPz.setSex(pt.sex());

                    return existPz;
                })
                .orElseGet(() -> repo.save(mapper.toEntity(pt)));
    }
}
