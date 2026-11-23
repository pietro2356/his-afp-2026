package com.sio.backend.resources.arrivalModes;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class ArrivalModeService {
    private final ArrivalModeRepository repo;
    private final ArrivalModeMapper mapper;


    public ArrivalModeService(ArrivalModeRepository repo, ArrivalModeMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }


    @Transactional(readOnly = true)
    public List<ArrivalModeDTO> getAllArrivalModes() {
        return repo.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<ArrivalModeDTO> getAllArrivalModesPaginated(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toDTO);
    }

    @Transactional(readOnly = true)
    public ArrivalModeDTO getArrivalModeById(String code) {
        ArrivalMode e = repo.findById(code).orElseThrow(() -> new EntityNotFoundException("Modalità di arrivo con codice " + code + " non trovata"));

        return mapper.toDTO(e);
    }

    @Transactional
    public ArrivalModeDTO createNewArrivalMode(ArrivalModeDTO dto) {
        if (repo.existsById(dto.code())) {
            throw new EntityExistsException("Modalità di arrivo con codice " + dto.code() + " già esistente");
        }

        ArrivalMode entity = mapper.toEntity(dto);
        entity = repo.save(entity);

        return mapper.toDTO(entity);
    }

    @Transactional
    public void deleteArrivalMode(String code) {
        throw new UnsupportedOperationException("Operazione non consentita non si puo eliminare un arrival mode");

    }

    @Transactional
    public ArrivalModeDTO editArrivalMode(ArrivalModeDTO dto) {
        ArrivalMode e = repo.findById(dto.code())
                .orElseThrow(() -> new EntityNotFoundException("Modalità di arrivo con codice: " + dto.code() + " non trovata"));

        e.setDescription(dto.description());

        ArrivalMode savedEntity = repo.save(e);
        return mapper.toDTO(savedEntity);
    }
}
