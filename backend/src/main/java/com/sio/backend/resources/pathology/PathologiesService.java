package com.sio.backend.resources.pathology;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PathologiesService {

    private final PathologiesRepository repo;
    private final PathologiesMapper mapper;

    public PathologiesService(PathologiesRepository repo, PathologiesMapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<PathologiesDTO> getAllPathologies(){
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
        Pathologies entity = mapper.toEntity(dto);
        entity = repo.save(entity);

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
