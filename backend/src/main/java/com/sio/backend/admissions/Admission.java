package com.sio.backend.admissions;

import com.sio.backend.patients.Patient;
import com.sio.backend.resources.arrivalModes.ArrivalMode;
import com.sio.backend.resources.pathology.Pathology;
import com.sio.backend.resources.triageColors.TriageColor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.time.Instant;

@Entity
@Table(name = "admissions")
@Getter
@Setter
public class Admission {
    @Column(name = "data_ora_dimissione")
    private Instant dataOraDimissione;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "note_triage", length = Integer.MAX_VALUE)
    private String noteTriage;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "modalita_arrivo_code")
    private ArrivalMode modalitaArrivoCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "codice_colore")
    private TriageColor codiceColore;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "patologia_code")
    private Pathology patologiaCode;

    @NotNull
    @ColumnDefault("'ATT'")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    @Column(name = "stato", columnDefinition = "admission_status not null")
    private AdmissionStatus stato;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "data_ora_ingresso")
    private Instant dataOraIngresso;

    @Size(max = 20)
    @NotNull
    @Column(name = "braccialetto", nullable = false, length = 20, unique = true)
    private String braccialetto;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @PrePersist
    protected void onCreate(){
        if (this.dataOraIngresso == null){
            this.dataOraIngresso = Instant.now();
        }

        if (this.updatedAt == null){
            this.updatedAt = Instant.now();
        }

        if (this.stato == null){
            this.stato = AdmissionStatus.ATT;
        }
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = Instant.now();
    }
}
