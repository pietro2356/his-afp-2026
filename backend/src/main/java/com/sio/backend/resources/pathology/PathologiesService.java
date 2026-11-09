package com.sio.backend.resources.pathology;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class PathologiesService {
    private final PathologiesRepository repo;
    private final PathologiesMapper mapper;

    public PathologiesService(PathologiesRepository repo, PathologiesMapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<PathologiesDTO> getAllPathologies(){
        log.debug("Recupero di tutte le patologie");
        List<Pathologies> list = repo.findAll();
        List<PathologiesDTO> dtos = new ArrayList<>();

        for (Pathologies e : list){
            dtos.add(mapper.toDTO(e));
        }

//        return repo.findAll().stream()
//                .map(mapper::toDTO)
//                .toList();

        return dtos;
    }

    @Transactional(readOnly = true)
    public Page<PathologiesDTO> getAllPathologiesPaginated(Pageable pageable){
        Page<Pathologies> entityPage = repo.findAll(pageable);

        return entityPage.map(mapper::toDTO);
    }

    @Transactional(readOnly = true)
    public PathologiesDTO getPathologyById(String code){
        Pathologies e = repo.findById(code).orElseThrow();

        return mapper.toDTO(e);
    }

    /**
     * Crea una nuova patologia nel database
     * @param dto
     * @return
     */
    public PathologiesDTO createNewPathology(PathologiesDTO dto){
        log.info("Creazione nuova entità patology con codice: {}", dto.code());
        log.debug("Entità in crezione con codice: {}, e descrzione {}", dto.code(), dto.description());

        if (repo.existsById(dto.code())){
            log.warn("Tentativo di creazione di una patologia con codice già esistente: {}", dto.code());
            throw new IllegalArgumentException("Patologia con codice: " + dto.code() + " già esistente");
        }

        Pathologies entity = mapper.toEntity(dto);
        entity = repo.save(entity);

        log.info("Entità patology con codice: {} creata con successo", entity.getCode());
        return mapper.toDTO(entity);
    }

    public void deletePathology(String code){
        repo.deleteById(code);
    }

    public PathologiesDTO editPathology(PathologiesDTO dto){
        Pathologies e = repo.findById(dto.code())
            .orElseThrow(() -> new EntityNotFoundException("Patologia con codice: " + dto.code() + " non trovata"));

        e.setDescription(dto.description());

        Pathologies savedEntity = repo.save(e);

        return mapper.toDTO(savedEntity);
    }
}
