package com.sio.backend.resources.pathology;

import org.springframework.stereotype.Component;

@Component
public class PathologyMapper {
    public PathologyDTO toDTO(Pathology entity) {
        if (entity == null) return null;
        return new PathologyDTO(entity.getCode(), entity.getDescription());
    }

    public Pathology toEntity(PathologyDTO dto) {
        if (dto == null) return null;
        Pathology entity = new Pathology();
        entity.setCode(dto.code());
        entity.setDescription(dto.description());
        return entity;
    }
}
