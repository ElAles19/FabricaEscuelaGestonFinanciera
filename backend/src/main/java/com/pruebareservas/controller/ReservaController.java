package com.pruebareservas.controller;

import com.pruebareservas.dto.ApiResponseDTO;
import com.pruebareservas.dto.ReservaDTO;
import com.pruebareservas.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<ReservaDTO>> crearReserva(@RequestBody Map<String, Object> request) {
        try {
            Long usuarioId = ((Number) request.get("usuarioId")).longValue();
            Long espacioId = ((Number) request.get("espacioId")).longValue();
            LocalDateTime fechaInicio = LocalDateTime.parse((String) request.get("fechaInicio"));
            LocalDateTime fechaFin = LocalDateTime.parse((String) request.get("fechaFin"));

            ReservaDTO reserva = reservaService.crearReserva(usuarioId, espacioId, fechaInicio, fechaFin);

            ApiResponseDTO<ReservaDTO> response = ApiResponseDTO.<ReservaDTO>builder()
                    .statusCode(201)
                    .message("Reserva creada exitosamente")
                    .data(reserva)
                    .success(true)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            ApiResponseDTO<ReservaDTO> response = ApiResponseDTO.<ReservaDTO>builder()
                    .statusCode(400)
                    .message(e.getMessage())
                    .success(false)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ReservaDTO>> obtenerReserva(@PathVariable Long id) {
        try {
            ReservaDTO reserva = reservaService.obtenerReserva(id);

            ApiResponseDTO<ReservaDTO> response = ApiResponseDTO.<ReservaDTO>builder()
                    .statusCode(200)
                    .message("Reserva obtenida exitosamente")
                    .data(reserva)
                    .success(true)
                    .build();

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponseDTO<ReservaDTO> response = ApiResponseDTO.<ReservaDTO>builder()
                    .statusCode(404)
                    .message(e.getMessage())
                    .success(false)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/espacio/{espacioId}")
    public ResponseEntity<ApiResponseDTO<List<ReservaDTO>>> listarPorEspacio(@PathVariable Long espacioId) {
        List<ReservaDTO> reservas = reservaService.listarPorEspacio(espacioId);

        ApiResponseDTO<List<ReservaDTO>> response = ApiResponseDTO.<List<ReservaDTO>>builder()
                .statusCode(200)
                .message("Reservas obtenidas exitosamente")
                .data(reservas)
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<ApiResponseDTO<List<ReservaDTO>>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<ReservaDTO> reservas = reservaService.listarPorUsuario(usuarioId);

        ApiResponseDTO<List<ReservaDTO>> response = ApiResponseDTO.<List<ReservaDTO>>builder()
                .statusCode(200)
                .message("Reservas obtenidas exitosamente")
                .data(reservas)
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<ApiResponseDTO<ReservaDTO>> cancelarReserva(@PathVariable Long id) {
        try {
            ReservaDTO reserva = reservaService.cancelarReserva(id);

            ApiResponseDTO<ReservaDTO> response = ApiResponseDTO.<ReservaDTO>builder()
                    .statusCode(200)
                    .message("Reserva cancelada exitosamente")
                    .data(reserva)
                    .success(true)
                    .build();

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponseDTO<ReservaDTO> response = ApiResponseDTO.<ReservaDTO>builder()
                    .statusCode(404)
                    .message(e.getMessage())
                    .success(false)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
