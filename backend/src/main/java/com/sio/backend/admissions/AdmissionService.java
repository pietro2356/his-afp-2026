package com.sio.backend.admissions;

import com.sio.backend.patients.Patient;
import com.sio.backend.patients.PatientService;
import com.sio.backend.resources.arrivalModes.ArrivalMode;
import com.sio.backend.resources.arrivalModes.ArrivalModeService;
import com.sio.backend.resources.pathology.Pathology;
import com.sio.backend.resources.pathology.PathologyService;
import com.sio.backend.resources.triageColors.TriageColor;
import com.sio.backend.resources.triageColors.TriageColorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.Year;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
class AdmissionService {
    private final AdmissionRepository repo;
    private final AdmissionMapper mapper;
    private final PatientService ptService;
    private final PathologyService pathoService;
    private final TriageColorService tcService;
    private final ArrivalModeService amService;

    public AdmissionService(
            AdmissionRepository repo,
            AdmissionMapper mapper,
            PatientService ptService,
            PathologyService pathoService,
            TriageColorService tcService,
            ArrivalModeService amService
    ){
        this.repo = repo;
        this.mapper = mapper;
        this.ptService = ptService;
        this.pathoService = pathoService;
        this.tcService = tcService;
        this.amService = amService;
    }


    List<AdmissionResponseDTO> getActiveAdmissions(){
        return repo.findByStatoNotOrderByCodiceColorePriorityAscDataOraIngressoAsc(AdmissionStatus.DIM)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    AdmissionResponseDTO getAdmissionById(Integer id){
        Admission adm = repo.findWithDetailsById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nessun accesso trovato con il seguente id: " + id));

        return mapper.toDTO(adm);
    }

    AdmissionResponseDTO getAdmissionByBraccialetto(String braccialetto){
        Admission adm = repo.findByBraccialettoIgnoreCase(braccialetto.trim())
                .orElseThrow(() -> new EntityNotFoundException("Nessun accesso trovato con il seguente braccialetto: " + braccialetto));

        return mapper.toDTO(adm);
    }

    @Transactional
    public AdmissionResponseDTO accettazionePaziente(AdmissionRequestDTO req){
        /**
         *      1. Delegare upsert del paziente anagrafico -> Ottenere Patient
         *      2. Verifica che il PZ non abbia episodi aperti (stato != DIM)
         *      3. Recupero dei dizionari tramite codice
         *
         *      4. Creazione episodio di accettazione PZ
         */

        // PT.1
        Patient pt = ptService.upsertPatient(req.paziente());

        // PT.2
        if (repo.existsByPatientIdAndStatoNot(pt.getId(),AdmissionStatus.DIM)){
            throw new IllegalArgumentException(
                    "Il paziente con CF: " + pt.getCodiceFiscale() + " ha già un episodio aperto!"
            );
        }

        //PT.3
        Pathology patologia = pathoService.getEntityByCode(req.pathologyCode());
        ArrivalMode modArrivo = amService.getEntityByCode(req.arrivalModeCode());
        TriageColor triageColor = tcService.getEntityByCode(req.triageColorCode());

        //PT.4 - Creazione dell'episodio di accettazione
        Admission adm = new Admission();
        adm.setPatient(pt);
        adm.setPatologiaCode(patologia);
        adm.setModalitaArrivoCode(modArrivo);
        adm.setCodiceColore(triageColor);

        adm.setBraccialetto(generaBraccialetto());
        adm.setStato(AdmissionStatus.ATT);
        adm.setNoteTriage(req.noteTriage());

        return mapper.toDTO(
                repo.save(adm)
        );
    }

    @Transactional
    public AdmissionResponseDTO updateStato(Integer id, AdmissionStatus stato){
        Admission adm = repo.findWithDetailsById(id)
                .orElseThrow(() -> new EntityNotFoundException("Accesso con ID: " + id + " non trovato"));

        if (adm.getStato() == AdmissionStatus.DIM){
            throw new UnsupportedOperationException("Il paziente risulta dimesso. Aprire un nuovo evento");
        }

        if (adm.getStato() == AdmissionStatus.VIS && stato == AdmissionStatus.ATT){
            throw new UnsupportedOperationException("Il paziente risulta attualmete in visita, non è possible metterlo in attesa");
        }

        adm.setStato(stato);

        if (stato == AdmissionStatus.DIM && adm.getDataOraDimissione() == null){
            adm.setDataOraDimissione(Instant.now());
        }

        return mapper.toDTO(adm);
    }

    private String generaBraccialetto(){
        String code;
        do {
            int num = ThreadLocalRandom.current().nextInt(1000, 9999);
            code = Year.now().getValue() + "-" + num;
        } while (repo.findByBraccialettoIgnoreCase(code).isPresent());

        return code;
    }

}
