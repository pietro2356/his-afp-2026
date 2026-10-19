package com.sio.backend.resources.triage_colors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TriageColorRepository extends JpaRepository<TriageColor, String> { }
