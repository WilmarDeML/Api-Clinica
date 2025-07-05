package com.wilmardeml.apimed.modelos.entidades;

import com.wilmardeml.apimed.modelos.enums.MotivoCancelacion;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "consultas")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicoId")
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pacienteId")
    private Paciente paciente;

    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    private MotivoCancelacion motivoCancelacion;

    public void cancelar(MotivoCancelacion motivo) {
        motivoCancelacion = motivo;

    }
}
