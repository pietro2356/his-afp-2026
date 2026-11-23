package com.sio.backend.admissions;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Integer> {

    // Verifica se il paziente ha già un episodio aperto (diverso da DIM = Dimesso)
    boolean existsByPatientIdAndStatoNot(Integer patientId, AdmissionStatus stato);

    // Lista pazienti attivi ordinati per gravità (priority asc) e orario di arrivo
    @EntityGraph(attributePaths = {"patient", "patologia", "codiceColore", "modalitaArrivo"})
    List<Admission> findByStatoNotOrderByCodiceColorePriorityAscDataOraIngressoAsc(AdmissionStatus stato);

    // Tutti gli accessi (inclusi i dimessi), per storico
    @EntityGraph(attributePaths = {"patient", "patologia", "codiceColore", "modalitaArrivo"})
    List<Admission> findAllByOrderByDataOraIngressoDesc();

    @EntityGraph(attributePaths = {"patient", "patologia", "codiceColore", "modalitaArrivo"})
    Optional<Admission> findWithDetailsById(Integer id);

    @EntityGraph(attributePaths = {"patient", "patologia", "codiceColore", "modalitaArrivo"})
    Optional<Admission> findByBraccialettoIgnoreCase(String braccialetto);
}