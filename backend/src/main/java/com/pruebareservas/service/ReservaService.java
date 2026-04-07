package com.pruebareservas.service;

import com.pruebareservas.entity.Reserva;
import com.pruebareservas.entity.Usuario;
import com.pruebareservas.entity.Espacio;
import com.pruebareservas.dto.ReservaDTO;
import com.pruebareservas.repository.ReservaRepository;
import com.pruebareservas.repository.UsuarioRepository;
import com.pruebareservas.repository.EspacioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EspacioRepository espacioRepository;

    public ReservaDTO crearReserva(Long usuarioId, Long espacioId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + usuarioId));

        Espacio espacio = espacioRepository.findById(espacioId)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado: " + espacioId));

        if (!usuario.getAutenticado()) {
            throw new RuntimeException("Usuario no autenticado. Debe iniciar sesión para hacer reservas");
        }

        if (reservaRepository.existsConflicto(espacioId, fechaInicio, fechaFin)) {
            throw new RuntimeException("El horario seleccionado ya está reservado");
        }

        Reserva reserva = Reserva.builder()
                .usuario(usuario)
                .espacio(espacio)
                .fechaInicio(fechaInicio)
                .fechaFin(fechaFin)
                .estado("CONFIRMADA")
                .build();

        Reserva guardada = reservaRepository.save(reserva);
        return convertToDTO(guardada);
    }

    public ReservaDTO obtenerReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada: " + id));
        return convertToDTO(reserva);
    }

    public List<ReservaDTO> listarPorEspacio(Long espacioId) {
        return reservaRepository.findByEspacioIdAndEstado(espacioId, "CONFIRMADA").stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ReservaDTO> listarPorUsuario(Long usuarioId) {
        return reservaRepository.findByUsuarioIdAndEstado(usuarioId, "CONFIRMADA").stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ReservaDTO cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada: " + id));

        reserva.setEstado("CANCELADA");
        Reserva cancelada = reservaRepository.save(reserva);
        return convertToDTO(cancelada);
    }

    private ReservaDTO convertToDTO(Reserva reserva) {
        return ReservaDTO.builder()
                .id(reserva.getId())
                .usuarioId(reserva.getUsuario().getId())
                .usuarioNombre(reserva.getUsuario().getNombre() + " " + reserva.getUsuario().getApellido())
                .espacioId(reserva.getEspacio().getId())
                .espacioNombre(reserva.getEspacio().getNombre())
                .fechaInicio(reserva.getFechaInicio())
                .fechaFin(reserva.getFechaFin())
                .estado(reserva.getEstado())
                .fechaCreacion(reserva.getFechaCreacion())
                .build();
    }
}
