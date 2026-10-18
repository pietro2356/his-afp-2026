package com.sio.backend.resources.repositories;

import com.sio.backend.resources.entities.ArrivalMode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArrivalModeRepository extends JpaRepository<ArrivalMode, String> {
}
