package com.pruebareservas.controller;

import com.pruebareservas.dto.ApiResponseDTO;
import com.pruebareservas.dto.CalendarioDTO;
import com.pruebareservas.service.CalendarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/calendario")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CalendarioController {

    private final CalendarioService calendarioService;

    @GetMapping("/dia/{espacioId}")
    public ResponseEntity<ApiResponseDTO<CalendarioDTO>> obtenerCalendarioDia(
            @PathVariable Long espacioId,
            @RequestParam(defaultValue = "") String fecha) {
        try {
            LocalDate fechaBusqueda = fecha.isEmpty() ? LocalDate.now() : LocalDate.parse(fecha);

            CalendarioDTO calendario = calendarioService.obtenerCalendarioDia(espacioId, fechaBusqueda);

            ApiResponseDTO<CalendarioDTO> response = ApiResponseDTO.<CalendarioDTO>builder()
                    .statusCode(200)
                    .message("Calendario del día obtenido exitosamente")
                    .data(calendario)
                    .success(true)
                    .build();

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponseDTO<CalendarioDTO> response = ApiResponseDTO.<CalendarioDTO>builder()
                    .statusCode(404)
                    .message(e.getMessage())
                    .success(false)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/semana/{espacioId}")
    public ResponseEntity<ApiResponseDTO<CalendarioDTO>> obtenerCalendarioSemana(
            @PathVariable Long espacioId,
            @RequestParam(defaultValue = "") String fechaInicio) {
        try {
            LocalDate fecha = fechaInicio.isEmpty() ? LocalDate.now() : LocalDate.parse(fechaInicio);

            CalendarioDTO calendario = calendarioService.obtenerCalendarioSemana(espacioId, fecha);

            ApiResponseDTO<CalendarioDTO> response = ApiResponseDTO.<CalendarioDTO>builder()
                    .statusCode(200)
                    .message("Calendario de la semana obtenido exitosamente")
                    .data(calendario)
                    .success(true)
                    .build();

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponseDTO<CalendarioDTO> response = ApiResponseDTO.<CalendarioDTO>builder()
                    .statusCode(404)
                    .message(e.getMessage())
                    .success(false)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
