package com.sio.backend.resources.arrivalModes;

import org.springframework.stereotype.Component;

@Component
public class ArrivalModeMapper {
    public ArrivalModeDTO toDTO(ArrivalMode entity){
        if (entity == null) return null;

        return new ArrivalModeDTO(
                entity.getCode(),
                entity.getDescription()
        );
    }

    public ArrivalMode toEntity(ArrivalModeDTO dto){
        if (dto == null) return null;

        ArrivalMode entity = new ArrivalMode();
        entity.setCode(dto.code());
        entity.setDescription(dto.description());

        return entity;
    }
}
