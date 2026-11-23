package com.sio.backend.patients;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

interface PatientRepository extends JpaRepository<Patient, Integer> {
    Optional<Patient> findByCodiceFiscaleIgnoreCase(String cf);
    List<Patient> findAllByOrderByNomeAsc();

    List<Patient> findByNomeIgnoreCaseAndCognomeIgnoreCaseAndDataNascitaAndSex(
            String nome,
            String cognome,
            LocalDate dataNascita,
            PatientSex sex
    );
}
