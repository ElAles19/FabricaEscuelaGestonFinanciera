package com.pruebareservas.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FranjasDTO {
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private String estado; // DISPONIBLE, OCUPADO
    private Long reservaId; // null si es DISPONIBLE
}
