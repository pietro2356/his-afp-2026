package com.sio.backend.resources.pathology;

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
