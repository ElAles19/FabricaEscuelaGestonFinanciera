package com.pruebareservas.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarioDTO {
    private Long espacioId;
    private String espacioNombre;
    private LocalDate fecha;
    private List<FranjasDTO> franjas;
    private Integer totalDisponibles;
    private Integer totalOcupados;
    private Boolean tieneDisponibilidad;
}
