package com.sio.backend.resources.repositories;

import com.sio.backend.resources.entities.TriageColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TriageColorRepository extends JpaRepository<TriageColor, String> {
}
