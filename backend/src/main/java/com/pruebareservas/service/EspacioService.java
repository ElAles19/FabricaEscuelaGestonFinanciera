package com.pruebareservas.service;

import com.pruebareservas.entity.Espacio;
import com.pruebareservas.dto.EspacioDTO;
import com.pruebareservas.repository.EspacioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EspacioService {

    private final EspacioRepository espacioRepository;

    public EspacioDTO crearEspacio(String nombre, String descripcion, String ubicacion, Integer capacidad) {
        Espacio espacio = Espacio.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .ubicacion(ubicacion)
                .capacidad(capacidad)
                .estado("DISPONIBLE")
                .build();

        Espacio guardado = espacioRepository.save(espacio);
        return convertToDTO(guardado);
    }

    public EspacioDTO obtenerEspacio(Long id) {
        Espacio espacio = espacioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado: " + id));
        return convertToDTO(espacio);
    }

    public List<EspacioDTO> listarTodos() {
        return espacioRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<EspacioDTO> listarDisponibles() {
        return espacioRepository.findByEstado("DISPONIBLE").stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EspacioDTO actualizarEspacio(Long id, String nombre, String descripcion, String ubicacion, Integer capacidad,
            String estado) {
        Espacio espacio = espacioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado: " + id));

        if (nombre != null)
            espacio.setNombre(nombre);
        if (descripcion != null)
            espacio.setDescripcion(descripcion);
        if (ubicacion != null)
            espacio.setUbicacion(ubicacion);
        if (capacidad != null)
            espacio.setCapacidad(capacidad);
        if (estado != null)
            espacio.setEstado(estado);

        Espacio actualizado = espacioRepository.save(espacio);
        return convertToDTO(actualizado);
    }

    public void eliminarEspacio(Long id) {
        espacioRepository.deleteById(id);
    }

    private EspacioDTO convertToDTO(Espacio espacio) {
        return EspacioDTO.builder()
                .id(espacio.getId())
                .nombre(espacio.getNombre())
                .descripcion(espacio.getDescripcion())
                .ubicacion(espacio.getUbicacion())
                .capacidad(espacio.getCapacidad())
                .estado(espacio.getEstado())
                .fechaCreacion(espacio.getFechaCreacion())
                .build();
    }
}
