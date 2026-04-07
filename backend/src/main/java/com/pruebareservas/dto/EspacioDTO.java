package com.pruebareservas.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EspacioDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private Integer capacidad;
    private String estado;
    private LocalDateTime fechaCreacion;
}
