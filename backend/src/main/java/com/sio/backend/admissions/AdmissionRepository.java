package com.sio.backend.admissions;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface AdmissionRepository extends JpaRepository<Admission, Integer> {
    // Verifica se il paziente ha già un episodio aperto (diverso da DIM = Dimesso)
    boolean existsByPatientIdAndStatoNot(Integer patientId, AdmissionStatus stato);

    // Lista pazienti attivi ordinati per gravità (priority asc) e orario di arrivo
    @EntityGraph(attributePaths = {"patient", "patologiaCode", "codiceColore", "modalitaArrivoCode"})
    List<Admission> findByStatoNotOrderByCodiceColorePriorityAscDataOraIngressoAsc(AdmissionStatus stato);

    // Tutti gli accessi (inclusi i dimessi), per storico
    @EntityGraph(attributePaths = {"patient", "patologiaCode", "codiceColore", "modalitaArrivoCode"})
    List<Admission> findAllByOrderByDataOraIngressoDesc();

    // Ricerca admission per braccialetto
    @EntityGraph(attributePaths = {"patient", "patologiaCode", "codiceColore", "modalitaArrivoCode"})
    Optional<Admission> findByBraccialettoIgnoreCase(String braccialetto);

    @EntityGraph(attributePaths = {"patient", "patologiaCode", "codiceColore", "modalitaArrivoCode"})
    Optional<Admission> findWithDetailsById(Integer id);
}
