package com.sio.backend.resources.ArrivalMode;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArrivalModeService {
    private final ArrivalModeRepository repo;
    private final ArrivalModeMapper mapper;

    ArrivalModeService(ArrivalModeRepository repo, ArrivalModeMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<ArrivalModeDTO> getArrivalModes() {
        return repo.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }
}
