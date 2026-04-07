package com.pruebareservas.repository;

import com.pruebareservas.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByEspacioIdAndEstado(Long espacioId, String estado);

    List<Reserva> findByUsuarioIdAndEstado(Long usuarioId, String estado);

    @Query("SELECT r FROM Reserva r WHERE r.espacio.id = :espacioId " +
            "AND r.estado = 'CONFIRMADA' " +
            "AND r.fechaInicio >= :fechaInicio " +
            "AND r.fechaFin <= :fechaFin " +
            "ORDER BY r.fechaInicio")
    List<Reserva> findReservasByEspacioAndFecha(
            @Param("espacioId") Long espacioId,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Reserva r " +
            "WHERE r.espacio.id = :espacioId " +
            "AND r.estado = 'CONFIRMADA' " +
            "AND r.fechaInicio < :fechaFin " +
            "AND r.fechaFin > :fechaInicio")
    boolean existsConflicto(
            @Param("espacioId") Long espacioId,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);
}
