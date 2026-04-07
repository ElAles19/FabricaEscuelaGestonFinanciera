package com.pruebareservas.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaDTO {
    private Long id;
    private Long usuarioId;
    private String usuarioNombre;
    private Long espacioId;
    private String espacioNombre;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String estado;
    private LocalDateTime fechaCreacion;
}
