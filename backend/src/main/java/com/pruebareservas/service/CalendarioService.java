package com.pruebareservas.service;

import com.pruebareservas.dto.CalendarioDTO;
import com.pruebareservas.dto.FranjasDTO;
import com.pruebareservas.entity.Reserva;
import com.pruebareservas.repository.ReservaRepository;
import com.pruebareservas.repository.EspacioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarioService {

    private final ReservaRepository reservaRepository;
    private final EspacioRepository espacioRepository;

    public CalendarioDTO obtenerCalendarioDia(Long espacioId, LocalDate fecha) {
        return obtenerCalendario(espacioId, fecha, fecha);
    }

    public CalendarioDTO obtenerCalendarioSemana(Long espacioId, LocalDate fechaInicio) {
        LocalDate fechaFin = fechaInicio.plusDays(6);
        return obtenerCalendario(espacioId, fechaInicio, fechaFin);
    }

    private CalendarioDTO obtenerCalendario(Long espacioId, LocalDate fechaInicio, LocalDate fechaFin) {
        // Verificar que el espacio existe
        var espacio = espacioRepository.findById(espacioId)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado: " + espacioId));

        // Obtener reservas confirmadas del espacio en el rango de fechas
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);

        List<Reserva> reservas = reservaRepository.findReservasByEspacioAndFecha(espacioId, inicio, fin);

        // Generar franjas horarias (cada franja de 1 hora)
        List<FranjasDTO> franjas = generarFranjas(inicio, fin, reservas);

        int totalDisponibles = (int) franjas.stream().filter(f -> "DISPONIBLE".equals(f.getEstado())).count();
        int totalOcupados = (int) franjas.stream().filter(f -> "OCUPADO".equals(f.getEstado())).count();

        return CalendarioDTO.builder()
                .espacioId(espacioId)
                .espacioNombre(espacio.getNombre())
                .fecha(fechaInicio)
                .franjas(franjas)
                .totalDisponibles(totalDisponibles)
                .totalOcupados(totalOcupados)
                .tieneDisponibilidad(totalDisponibles > 0)
                .build();
    }

    private List<FranjasDTO> generarFranjas(LocalDateTime inicio, LocalDateTime fin, List<Reserva> reservas) {
        List<FranjasDTO> franjas = new ArrayList<>();
        LocalDateTime actual = inicio;

        while (actual.isBefore(fin)) {
            final LocalDateTime horaActual = actual;
            final LocalDateTime proximaHora = actual.plusHours(1);

            // Buscar si hay una reserva que cubra esta franja
            Reserva reservaOcupante = reservas.stream()
                    .filter(r -> !r.getFechaInicio().isAfter(horaActual) && r.getFechaFin().isAfter(horaActual))
                    .findFirst()
                    .orElse(null);

            if (reservaOcupante != null) {
                franjas.add(FranjasDTO.builder()
                        .horaInicio(horaActual)
                        .horaFin(proximaHora)
                        .estado("OCUPADO")
                        .reservaId(reservaOcupante.getId())
                        .build());
            } else {
                franjas.add(FranjasDTO.builder()
                        .horaInicio(horaActual)
                        .horaFin(proximaHora)
                        .estado("DISPONIBLE")
                        .reservaId(null)
                        .build());
            }

            actual = proximaHora;
        }

        return franjas;
    }
}
