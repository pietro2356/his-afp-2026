package com.sio.backend.resources.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "triage_colors") // , schema = "sio"
public class TriageColor {
    @Id
    @Size(max = 20)
    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @Size(max = 50)
    @NotNull
    @Column(name = "display_name", nullable = false, length = 50)
    private String displayName;

    @NotNull
    @Column(name = "priority", nullable = false)
    private Integer priority;

    @Size(max = 7)
    @NotNull
    @Column(name = "hex_value", nullable = false, length = 7)
    private String hexValue;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;
}
