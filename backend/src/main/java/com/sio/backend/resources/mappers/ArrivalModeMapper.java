package com.sio.backend.resources.mappers;

import com.sio.backend.resources.dtos.ArrivalModeDTO;
import com.sio.backend.resources.entities.ArrivalMode;
import com.sio.backend.resources.entities.TriageColor;
import org.springframework.stereotype.Component;

@Component
public class ArrivalModeMapper {
    public TriageColor toEntity(ArrivalMode arrivalMode) {
        if (arrivalMode == null) return null;
        TriageColor entity = new TriageColor();
        entity.setCode(arrivalMode.getCode());
        entity.setDisplayName(arrivalMode.getDescription());
        return entity;
    }

    public ArrivalModeDTO toDTO(ArrivalMode entity) {
        if (entity == null) return null;
        return new ArrivalModeDTO(
                entity.getCode(),
                entity.getDescription()
        );
    }
}
