package com.sio.backend.admissions;

import com.sio.backend.patients.Patient;
import com.sio.backend.patients.PatientDTO;
import com.sio.backend.patients.PatientMapper;
import com.sio.backend.patients.PatientRepository;
import com.sio.backend.resources.arrivalModes.ArrivalMode;
import com.sio.backend.resources.arrivalModes.ArrivalModeRepository;
import com.sio.backend.resources.pathology.Pathology;
import com.sio.backend.resources.pathology.PathologyRepository;
import com.sio.backend.resources.triageColors.TriageColor;
import com.sio.backend.resources.triageColors.TriageColorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional(readOnly = true)
public class AdmissionService {

    private final AdmissionRepository admissionRepo;
    private final PatientRepository patientRepo;
    private final PathologyRepository pathologyRepo;
    private final TriageColorRepository triageColorRepo;
    private final ArrivalModeRepository arrivalModeRepo;
    private final PatientMapper patientMapper;
    private final AdmissionMapper admissionMapper;

    public AdmissionService(
            AdmissionRepository admissionRepo,
            PatientRepository patientRepo,
            PathologyRepository pathologyRepo,
            TriageColorRepository triageColorRepo,
            ArrivalModeRepository arrivalModeRepo,
            PatientMapper patientMapper,
            AdmissionMapper admissionMapper
    ) {
        this.admissionRepo = admissionRepo;
        this.patientRepo = patientRepo;
        this.pathologyRepo = pathologyRepo;
        this.triageColorRepo = triageColorRepo;
        this.arrivalModeRepo = arrivalModeRepo;
        this.patientMapper = patientMapper;
        this.admissionMapper = admissionMapper;
    }

    /**
     * Restituisce i pazienti attualmente presenti in Pronto Soccorso (stato != DIM),
     * ordinati per priorità del codice colore e orario di arrivo.
     */
    public List<AdmissionResponseDTO> getActiveAdmissions() {
        return admissionRepo
                .findByStatoNotOrderByCodiceColorePriorityAscDataOraIngressoAsc(AdmissionStatus.DIM)
                .stream()
                .map(admissionMapper::toDTO)
                .toList();
    }

    public AdmissionResponseDTO getAdmissionById(Integer id) {
        Admission admission = admissionRepo.findWithDetailsById(id)
                .orElseThrow(() -> new EntityNotFoundException("Accesso con ID " + id + " non trovato."));

        return admissionMapper.toDTO(admission);
    }

    public AdmissionResponseDTO getAdmissionByBraccialetto(String braccialetto) {
        Admission admission = admissionRepo.findByBraccialettoIgnoreCase(braccialetto.trim())
                .orElseThrow(() -> new EntityNotFoundException("Braccialetto '" + braccialetto + "' non trovato."));

        return admissionMapper.toDTO(admission);
    }

    /**
     * Accettazione amministrativa + sanitaria in un'unica transazione ACID:
     * 1. Crea o aggiorna l'anagrafica del paziente (Upsert su codice_fiscale)
     * 2. Verifica che non abbia già un episodio aperto
     * 3. Risolve le Foreign Key verso i dizionari
     * 4. Genera il codice braccialetto e salva l'accesso
     */
    @Transactional
    public AdmissionResponseDTO accettaPaziente(AdmissionRequestDTO request) {
        Patient patient = upsertPatient(request.patient());

        if (admissionRepo.existsByPatientIdAndStatoNot(patient.getId(), AdmissionStatus.DIM)) {
            throw new IllegalArgumentException(
                    "Il paziente (" + patient.getCodiceFiscale() + ") ha già un accesso aperto in Pronto Soccorso."
            );
        }

        Pathology patologia = pathologyRepo.findById(request.patologiaCode().toUpperCase().trim())
                .orElseThrow(() -> new EntityNotFoundException("Patologia non trovata: " + request.patologiaCode()));

        TriageColor codiceColore = triageColorRepo.findById(request.codiceColore().toUpperCase().trim())
                .orElseThrow(() -> new EntityNotFoundException("Codice colore non trovato: " + request.codiceColore()));

        ArrivalMode modalitaArrivo = arrivalModeRepo.findById(request.modalitaArrivoCode().toUpperCase().trim())
                .orElseThrow(() -> new EntityNotFoundException("Modalità di arrivo non trovata: " + request.modalitaArrivoCode()));

        Admission admission = new Admission();
        admission.setPatient(patient);
        admission.setBraccialetto(generaBraccialetto());
        admission.setStato(AdmissionStatus.ATT);
        admission.setPatologia(patologia);
        admission.setCodiceColore(codiceColore);
        admission.setModalitaArrivo(modalitaArrivo);
        admission.setNoteTriage(request.noteTriage());

        Admission saved = admissionRepo.save(admission);
        return admissionMapper.toDTO(saved);
    }

    /**
     * Aggiorna lo stato clinico dell'accesso (ATT -> VIS -> OBI / RIC -> DIM).
     * Se lo stato diventa DIM (Dimesso), valorizza automaticamente data_ora_dimissione.
     */
    @Transactional
    public AdmissionResponseDTO updateStato(Integer id, AdmissionStatus nuovoStato) {
        Admission admission = admissionRepo.findWithDetailsById(id)
                .orElseThrow(() -> new EntityNotFoundException("Accesso con ID " + id + " non trovato."));

        admission.setStato(nuovoStato);

        if (nuovoStato == AdmissionStatus.DIM && admission.getDataOraDimissione() == null) {
            admission.setDataOraDimissione(LocalDateTime.now());
        }

        // Dirty checking: @Transactional esegue l'UPDATE e @PreUpdate aggiorna updated_at
        return admissionMapper.toDTO(admission);
    }

    private Patient upsertPatient(PatientDTO dto) {
        String cfNormalizzato = dto.codiceFiscale().toUpperCase().trim();

        return patientRepo.findByCodiceFiscaleIgnoreCase(cfNormalizzato)
                .map(existing -> {
                    // Dirty checking: aggiorna i dati anagrafici se cambiati
                    existing.setNome(dto.nome());
                    existing.setCognome(dto.cognome());
                    existing.setDataNascita(dto.dataNascita());
                    existing.setSex(dto.sex());
                    existing.setIndirizzoVia(dto.indirizzoVia());
                    existing.setIndirizzoCivico(dto.indirizzoCivico());
                    existing.setComune(dto.comune());
                    existing.setProvincia(dto.provincia());
                    return existing;
                })
                .orElseGet(() -> patientRepo.save(patientMapper.toEntity(dto)));
    }

    // Coerente con il seeding del DDL: es. "2026-4821"
    private String generaBraccialetto() {
        String code;
        do {
            int num = ThreadLocalRandom.current().nextInt(1000, 9999);
            code = Year.now().getValue() + "-" + num;
        } while (admissionRepo.findByBraccialettoIgnoreCase(code).isPresent());
        return code;
    }
}