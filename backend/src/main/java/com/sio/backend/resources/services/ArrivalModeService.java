package com.sio.backend.resources.services;

import com.sio.backend.resources.dtos.ArrivalModeDTO;
import com.sio.backend.resources.mappers.ArrivalModeMapper;
import com.sio.backend.resources.repositories.ArrivalModeRepository;
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
