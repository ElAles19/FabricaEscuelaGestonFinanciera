package com.pruebareservas.controller;

import com.pruebareservas.dto.ApiResponseDTO;
import com.pruebareservas.dto.UsuarioDTO;
import com.pruebareservas.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<ApiResponseDTO<UsuarioDTO>> registrarUsuario(@RequestBody Map<String, String> request) {
        try {
            UsuarioDTO usuario = usuarioService.crearUsuario(
                    request.get("email"),
                    request.get("nombre"),
                    request.get("apellido"),
                    request.get("password"));

            ApiResponseDTO<UsuarioDTO> response = ApiResponseDTO.<UsuarioDTO>builder()
                    .statusCode(201)
                    .message("Usuario registrado exitosamente")
                    .data(usuario)
                    .success(true)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            ApiResponseDTO<UsuarioDTO> response = ApiResponseDTO.<UsuarioDTO>builder()
                    .statusCode(400)
                    .message(e.getMessage())
                    .success(false)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<UsuarioDTO>> login(@RequestBody Map<String, String> request) {
        try {
            UsuarioDTO usuario = usuarioService.autenticar(
                    request.get("email"),
                    request.get("password"));

            ApiResponseDTO<UsuarioDTO> response = ApiResponseDTO.<UsuarioDTO>builder()
                    .statusCode(200)
                    .message("Login exitoso")
                    .data(usuario)
                    .success(true)
                    .build();

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponseDTO<UsuarioDTO> response = ApiResponseDTO.<UsuarioDTO>builder()
                    .statusCode(401)
                    .message(e.getMessage())
                    .success(false)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}