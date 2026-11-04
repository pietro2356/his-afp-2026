package com.sio.backend.resources.pathology;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PathologiesService {

    private final PathologiesRepository repo;
    private final PathologiesMapper mapper;

    public PathologiesService(PathologiesRepository repo, PathologiesMapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<PathologiesDTO> getAllPathologies(){
        List<Pathology> list = repo.findAll();
        List<PathologiesDTO> dtos = new ArrayList<>();

        for (Pathology e : list){
            dtos.add(mapper.toDTO(e));
        }

//        return repo.findAll().stream()
//                .map(mapper::toDTO)
//                .toList();

        return dtos;
    }

    public PathologiesDTO getPathologyById(String code){
        Pathology e = repo.findById(code).orElseThrow();

        return mapper.toDTO(e);
    }

    public PathologiesDTO createNewPathology(PathologiesDTO dto){
        Pathology entity = mapper.toEntity(dto);
        entity = repo.save(entity);

        return mapper.toDTO(entity);
    }

    public void deletePathology(String code){
        repo.deleteById(code);
    }

    @Transactional(label = "editPathology")
    public PathologiesDTO editPathology(PathologiesDTO dto){
        Pathology e = repo.findById(dto.code())
            .orElseThrow(() -> new EntityNotFoundException("Patologia con codice: " + dto.code() + " non trovata"));

        e.setDescription(dto.description());

        Pathology savedEntity = repo.save(e);

        return mapper.toDTO(savedEntity);
    }
}
