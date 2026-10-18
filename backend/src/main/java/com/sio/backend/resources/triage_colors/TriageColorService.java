package com.sio.backend.resources.triage_colors;

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
