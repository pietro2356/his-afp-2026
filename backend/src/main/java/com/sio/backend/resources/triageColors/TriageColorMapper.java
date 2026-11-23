package com.sio.backend.resources.triageColors;

import org.springframework.stereotype.Component;

@Component
public class TriageColorMapper {
    public TriageColorDTO toDTO(TriageColor entity){
        if (entity == null) return null;

        return new TriageColorDTO(
                entity.getCode(),
                entity.getDisplayName(),
                entity.getPriority(),
                entity.getHexValue()
        );
    }

    public TriageColor toEntity(TriageColorDTO dto){
        if (dto == null) return null;

        TriageColor entity = new TriageColor();
        entity.setCode(dto.code());
        entity.setDisplayName(dto.displayName());
        entity.setPriority(dto.priority());
        entity.setHexValue(dto.hexValue());

        return entity;
    }
}
