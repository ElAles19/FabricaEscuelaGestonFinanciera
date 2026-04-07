package com.pruebareservas.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HelloController {

    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of(
                "message", "¡Hola Mundo desde Spring Boot!",
                "timestamp", java.time.LocalDateTime.now().toString());
    }

    @GetMapping("/status")
    public Map<String, Object> status() {
        return Map.of(
                "status", "OK",
                "application", "Prueba Reservas",
                "version", "1.0.0",
                "timestamp", java.time.LocalDateTime.now().toString());
    }

}
