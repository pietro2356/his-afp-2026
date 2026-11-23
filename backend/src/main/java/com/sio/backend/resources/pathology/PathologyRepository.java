package com.sio.backend.resources.pathology;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PathologyRepository extends JpaRepository<Pathology, String> {
}
