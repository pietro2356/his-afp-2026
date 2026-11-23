package com.sio.backend.admissions;

import com.sio.backend.patients.Patient;
import com.sio.backend.resources.arrivalModes.ArrivalMode;
import com.sio.backend.resources.pathology.Pathology;
import com.sio.backend.resources.triageColors.TriageColor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "admissions", schema = "sio")
public class Admission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Size(max = 20)
    @NotNull
    @Column(name = "braccialetto", nullable = false, unique = true, length = 20)
    private String braccialetto;

    @Column(name = "data_ora_ingresso", updatable = false)
    private LocalDateTime dataOraIngresso;

    @NotNull
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "stato", nullable = false, columnDefinition = "sio.admission_status")
    private AdmissionStatus stato = AdmissionStatus.ATT;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patologia_code", nullable = false)
    private Pathology patologia;

    // Nel DDL codice_colore ha ON DELETE SET NULL, quindi può essere nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codice_colore")
    private TriageColor codiceColore;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "modalita_arrivo_code", nullable = false)
    private ArrivalMode modalitaArrivo;

    @Column(name = "note_triage", columnDefinition = "TEXT")
    private String noteTriage;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "data_ora_dimissione")
    private LocalDateTime dataOraDimissione;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        if (this.dataOraIngresso == null) {
            this.dataOraIngresso = now;
        }
        if (this.updatedAt == null) {
            this.updatedAt = now;
        }
        if (this.stato == null) {
            this.stato = AdmissionStatus.ATT;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}