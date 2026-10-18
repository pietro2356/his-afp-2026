package com.sio.backend.resources.repositories;

import com.sio.backend.resources.entities.Pathology;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PathologyRepository extends JpaRepository<Pathology, String> {
}
