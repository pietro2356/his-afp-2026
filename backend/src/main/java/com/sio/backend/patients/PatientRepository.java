package com.sio.backend.patients;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Optional<Patient> findByCodiceFiscaleIgnoreCase(String cf);
    List<Patient> findAllByOrderByNomeAscNomeAsc();
}
