package com.sio.backend.resources.services;

import com.sio.backend.resources.dtos.PathologyDTO;
import com.sio.backend.resources.mappers.PathologyMapper;
import com.sio.backend.resources.repositories.PathologyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PathologyService {
    private final PathologyRepository repo;
    private final PathologyMapper mapper;

    PathologyService(PathologyRepository repo, PathologyMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<PathologyDTO> getPathologies() {
        return repo.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }
}
