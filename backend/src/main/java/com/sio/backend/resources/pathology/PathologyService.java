package com.sio.backend.resources.pathology;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PathologyService {

    private final PathologyRepository repo;
    private final PathologyMapper mapper;

    public PathologyService(PathologyRepository repo, PathologyMapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<PathologyDTO> getAllPathologies(){
        return repo.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    public PathologyDTO getPathologyById(String code){
        Pathology e = repo.findById(code).orElseThrow();

        return mapper.toDTO(e);
    }

    public PathologyDTO createNewPathology(PathologyDTO dto){
        if (repo.existsById(dto.code())){
            throw new IllegalArgumentException("Patologia con codice: " + dto.code() + " già esistente");
        }
        Pathology entity = mapper.toEntity(dto);
        entity = repo.save(entity);

        return mapper.toDTO(entity);
    }

    public void deletePathology(String code){
        repo.deleteById(code);
    }

    @Transactional(label = "editPathology")
    public PathologyDTO editPathology(PathologyDTO dto){
        Pathology e = repo.findById(dto.code())
            .orElseThrow(() -> new EntityNotFoundException("Patologia con codice: " + dto.code() + " non trovata"));

        e.setDescription(dto.description());

        Pathology savedEntity = repo.save(e);

        return mapper.toDTO(savedEntity);
    }
}
