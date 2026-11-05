package com.sio.backend.resources.pathology;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class PathologiesService {

//    private static final Logger logger = LoggerFactory.getLogger(PathologiesService.class);

    private final PathologiesRepository repo;
    private final PathologiesMapper mapper;

    public PathologiesService(PathologiesRepository repo, PathologiesMapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }

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

    public PathologiesDTO getPathologyById(String code){
        Pathologies e = repo.findById(code).orElseThrow();

        return mapper.toDTO(e);
    }

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

    @Transactional(label = "editPathology")
    public PathologiesDTO editPathology(PathologiesDTO dto){
        Pathologies e = repo.findById(dto.code())
            .orElseThrow(() -> new EntityNotFoundException("Patologia con codice: " + dto.code() + " non trovata"));

        e.setDescription(dto.description());

        Pathologies savedEntity = repo.save(e);

        return mapper.toDTO(savedEntity);
    }
}
