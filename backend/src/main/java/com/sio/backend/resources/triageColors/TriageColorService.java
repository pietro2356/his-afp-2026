package com.sio.backend.resources.triageColors;

import com.sio.backend.resources.pathology.Pathology;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class TriageColorService {
    private final TriageColorRepository repo;
    private final TriageColorMapper mapper;


    public TriageColorService(TriageColorRepository repo, TriageColorMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<TriageColorDTO> getAllTriageColors(){
        return repo.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }


    @Transactional(readOnly = true)
    public Page<TriageColorDTO> getAllTriageColorsPaginated(Pageable pageable){

        Page<TriageColor> entityPage = repo.findAll(pageable);
        return entityPage.map(mapper::toDTO);
    }

    @Transactional(readOnly = true)
    public TriageColorDTO getTriageColorById(String code){
        TriageColor e = repo.findById(code).orElseThrow(() -> new EntityNotFoundException("Codice paziente con codice "+code+" non trovata"));

        return mapper.toDTO(e);
    }

    public TriageColorDTO createNewTriageColor(TriageColorDTO dto){
        if (repo.existsById(dto.code())){
            throw new EntityExistsException("Triage color con codice " + dto.code() + " già esistente");
        }

        TriageColor entity = mapper.toEntity(dto);
        entity = repo.save(entity);

        return mapper.toDTO(entity);
    }

    public void deleteTriageColor(String code){
        TriageColor e = repo.findById(code)
                .orElseThrow(() -> new EntityNotFoundException("Triage color con codice: " + code + " non trovato"));
        repo.delete(e);
    }


    public TriageColorDTO editTriageColor(TriageColorDTO dto){
        TriageColor e = repo.findById(dto.code())
                .orElseThrow(() -> new EntityNotFoundException("Modalita di arrivo con codice: " + dto.code() + " non trovata"));

        e.setPriority(dto.priority());
        e.setDisplayName(dto.displayName());

        TriageColor savedEntity = repo.save(e);
        return mapper.toDTO(savedEntity);
    }

    @Transactional(readOnly = true)
    public TriageColor getEntityByCode(String code){
        return repo.findById(code.toUpperCase().trim())
                .orElseThrow(() -> new EntityNotFoundException("Impossibile trovare la patologia con il codice: " + code));
    }
}
