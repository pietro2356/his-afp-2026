package com.sio.backend.resources.mappers;

import com.sio.backend.resources.dtos.TriageColorDTO;
import com.sio.backend.resources.entities.TriageColor;
import org.springframework.stereotype.Component;

@Component
public class TriageColorMapper {
    public TriageColor toEntity(TriageColorDTO dto) {
        if (dto == null) return null;
        TriageColor entity = new TriageColor();
        entity.setCode(dto.code());
        entity.setDisplayName(dto.displayName());
        entity.setHexValue(dto.hexValue());
        entity.setPriority(dto.priority());

        return entity;
    }

    public  TriageColorDTO toDTO(TriageColor entity) {
        if (entity == null) return null;

        return new TriageColorDTO(
                entity.getCode(),
                entity.getDisplayName(),
                entity.getPriority(),
                entity.getHexValue()
        );
    }
}
