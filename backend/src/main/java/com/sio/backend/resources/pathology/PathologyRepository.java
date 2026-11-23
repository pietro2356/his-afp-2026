package com.sio.backend.resources.pathology;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaccia Repository per la gestione della persistenza dell'entità {@link Pathology}.
 * Estende {@link JpaRepository} ereditando le operazioni CRUD standard,
 * utilizzando {@link String} come tipo della chiave primaria (codice identificativo).
 */
@Repository
public interface PathologyRepository extends JpaRepository<Pathology, String> {
}
