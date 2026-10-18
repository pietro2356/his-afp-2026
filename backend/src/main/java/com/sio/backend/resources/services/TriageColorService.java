package com.sio.backend.resources.services;

import com.sio.backend.resources.dtos.TriageColorDTO;
import com.sio.backend.resources.mappers.TriageColorMapper;
import com.sio.backend.resources.repositories.TriageColorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TriageColorService {
    private final TriageColorRepository  repo;

    private final TriageColorMapper mapper;

    public TriageColorService(TriageColorRepository repo, TriageColorMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<TriageColorDTO> getTriageColors() {
        return repo.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }
}
