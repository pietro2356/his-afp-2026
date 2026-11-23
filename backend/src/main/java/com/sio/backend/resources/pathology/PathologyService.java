package com.sio.backend.resources.pathology;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class PathologyService {
    private final PathologyRepository repo;
    private final PathologyMapper mapper;

    public PathologyService(PathologyRepository repo, PathologyMapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<PathologyDTO> getAllPathologies(){
        log.debug("Recupero di tutte le patologie");
        List<Pathology> list = repo.findAll();
        List<PathologyDTO> dtos = new ArrayList<>();

        for (Pathology e : list){
            dtos.add(mapper.toDTO(e));
        }

//        return repo.findAll().stream()
//                .map(mapper::toDTO)
//                .toList();

        return dtos;
    }

    @Transactional(readOnly = true)
    public Page<PathologyDTO> getAllPathologiesPaginated(Pageable pageable){
        Page<Pathology> entityPage = repo.findAll(pageable);

        return entityPage.map(mapper::toDTO);
    }

    @Transactional(readOnly = true)
    public PathologyDTO getPathologyById(String code){
        Pathology e = repo.findById(code).orElseThrow();

        return mapper.toDTO(e);
    }

    public PathologyDTO createNewPathology(PathologyDTO dto){
        log.info("Creazione nuova entità patology con codice: {}", dto.code());
        log.debug("Entità in crezione con codice: {}, e descrzione {}", dto.code(), dto.description());

        if (repo.existsById(dto.code())){
            log.warn("Tentativo di creazione di una patologia con codice già esistente: {}", dto.code());
            throw new IllegalArgumentException("Patologia con codice: " + dto.code() + " già esistente");
        }

        Pathology entity = mapper.toEntity(dto);
        entity = repo.save(entity);

        log.info("Entità patology con codice: {} creata con successo", entity.getCode());
        return mapper.toDTO(entity);
    }

    public void deletePathology(String code){
        repo.deleteById(code);
    }

    public PathologyDTO editPathology(PathologyDTO dto){
        Pathology e = repo.findById(dto.code())
            .orElseThrow(() -> new EntityNotFoundException("Patologia con codice: " + dto.code() + " non trovata"));

        e.setDescription(dto.description());

        Pathology savedEntity = repo.save(e);

        return mapper.toDTO(savedEntity);
    }
}
