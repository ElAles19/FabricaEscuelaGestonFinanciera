package com.pruebareservas.controller;

import com.pruebareservas.dto.ApiResponseDTO;
import com.pruebareservas.dto.EspacioDTO;
import com.pruebareservas.service.EspacioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/espacios")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EspacioController {

    private final EspacioService espacioService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<EspacioDTO>> crearEspacio(@RequestBody Map<String, Object> request) {
        try {
            EspacioDTO espacio = espacioService.crearEspacio(
                    (String) request.get("nombre"),
                    (String) request.get("descripcion"),
                    (String) request.get("ubicacion"),
                    ((Number) request.get("capacidad")).intValue());

            ApiResponseDTO<EspacioDTO> response = ApiResponseDTO.<EspacioDTO>builder()
                    .statusCode(201)
                    .message("Espacio creado exitosamente")
                    .data(espacio)
                    .success(true)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponseDTO<EspacioDTO> response = ApiResponseDTO.<EspacioDTO>builder()
                    .statusCode(400)
                    .message(e.getMessage())
                    .success(false)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<EspacioDTO>> obtenerEspacio(@PathVariable Long id) {
        try {
            EspacioDTO espacio = espacioService.obtenerEspacio(id);

            ApiResponseDTO<EspacioDTO> response = ApiResponseDTO.<EspacioDTO>builder()
                    .statusCode(200)
                    .message("Espacio obtenido exitosamente")
                    .data(espacio)
                    .success(true)
                    .build();

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponseDTO<EspacioDTO> response = ApiResponseDTO.<EspacioDTO>builder()
                    .statusCode(404)
                    .message(e.getMessage())
                    .success(false)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<EspacioDTO>>> listarEspacios() {
        List<EspacioDTO> espacios = espacioService.listarTodos();

        ApiResponseDTO<List<EspacioDTO>> response = ApiResponseDTO.<List<EspacioDTO>>builder()
                .statusCode(200)
                .message("Espacios obtenidos exitosamente")
                .data(espacios)
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<ApiResponseDTO<List<EspacioDTO>>> listarDisponibles() {
        List<EspacioDTO> espacios = espacioService.listarDisponibles();

        ApiResponseDTO<List<EspacioDTO>> response = ApiResponseDTO.<List<EspacioDTO>>builder()
                .statusCode(200)
                .message("Espacios disponibles obtenidos exitosamente")
                .data(espacios)
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }
}
