package com.sio.backend.resources.pathology;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public PathologiesDTO createNewPathology(PathologiesDTO dto){
        Pathologies entity = mapper.toEntity(dto);
        entity = repo.save(entity);

        return mapper.toDTO(entity);
    }

    public void deletePathology(String code){
        repo.deleteById(code);
    }

    public void editPathology(PathologiesDTO dto){
        Optional<Pathologies> entity = repo.findById(dto.code());

        if (entity.isEmpty()) throw new NoSuchElementException("Patologia cercata non trovato");

//        if (!entity.get().getCode().equals(dto.code())) throw new Exception("Codici identificativi non corrispondenti");

        repo.deleteById(entity.get().getCode());
        repo.save(mapper.toEntity(dto));
    }
}

/**
 *      - code
 *      - descr
 */



