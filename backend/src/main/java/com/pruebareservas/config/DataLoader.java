package com.pruebareservas.config;

import com.pruebareservas.entity.Usuario;
import com.pruebareservas.entity.Espacio;
import com.pruebareservas.entity.Reserva;
import com.pruebareservas.repository.UsuarioRepository;
import com.pruebareservas.repository.EspacioRepository;
import com.pruebareservas.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final EspacioRepository espacioRepository;
    private final ReservaRepository reservaRepository;

    @Override
    public void run(String... args) throws Exception {
        // Limpiar datos anteriores (opcional)
        // usuarioRepository.deleteAll();
        // espacioRepository.deleteAll();
        // reservaRepository.deleteAll();

        // Crear usuarios de muestra
        if (usuarioRepository.count() == 0) {
            Usuario usuario1 = Usuario.builder()
                    .email("juan.perez@example.com")
                    .nombre("Juan")
                    .apellido("Pérez")
                    .password("password123")
                    .autenticado(true)
                    .build();

            Usuario usuario2 = Usuario.builder()
                    .email("maria.garcia@example.com")
                    .nombre("María")
                    .apellido("García")
                    .password("password456")
                    .autenticado(true)
                    .build();

            Usuario usuario3 = Usuario.builder()
                    .email("carlos.lopez@example.com")
                    .nombre("Carlos")
                    .apellido("López")
                    .password("password789")
                    .autenticado(false)
                    .build();

            usuarioRepository.save(usuario1);
            usuarioRepository.save(usuario2);
            usuarioRepository.save(usuario3);

            System.out.println("✓ Usuarios de muestra creados");
        }

        // Crear espacios de muestra
        if (espacioRepository.count() == 0) {
            Espacio espacio1 = Espacio.builder()
                    .nombre("Sala de Juntas A")
                    .descripcion("Sala para reuniones pequeñas con capacidad para 8 personas")
                    .ubicacion("Piso 2 - Bloque A")
                    .capacidad(8)
                    .estado("DISPONIBLE")
                    .build();

            Espacio espacio2 = Espacio.builder()
                    .nombre("Sala de Juntas B")
                    .descripcion("Sala para reuniones medianas con capacidad para 15 personas")
                    .ubicacion("Piso 3 - Bloque B")
                    .capacidad(15)
                    .estado("DISPONIBLE")
                    .build();

            Espacio espacio3 = Espacio.builder()
                    .nombre("Auditorio Principal")
                    .descripcion("Espacio grande para eventos y presentaciones")
                    .ubicacion("Piso 1 - Entrada")
                    .capacidad(50)
                    .estado("DISPONIBLE")
                    .build();

            Espacio espacio4 = Espacio.builder()
                    .nombre("Cancha de Tenis")
                    .descripcion("Cancha de tenis cubierta")
                    .ubicacion("Zona deportiva")
                    .capacidad(4)
                    .estado("DISPONIBLE")
                    .build();

            espacioRepository.save(espacio1);
            espacioRepository.save(espacio2);
            espacioRepository.save(espacio3);
            espacioRepository.save(espacio4);

            System.out.println("✓ Espacios de muestra creados");
        }

        // Crear reservas de muestra
        if (reservaRepository.count() == 0) {
            Usuario usuario1 = usuarioRepository.findByEmail("juan.perez@example.com").orElse(null);
            Usuario usuario2 = usuarioRepository.findByEmail("maria.garcia@example.com").orElse(null);

            Espacio espacio1 = espacioRepository.findById(1L).orElse(null);
            Espacio espacio2 = espacioRepository.findById(2L).orElse(null);

            if (usuario1 != null && espacio1 != null) {
                // Reserva para hoy a las 10:00
                Reserva reserva1 = Reserva.builder()
                        .usuario(usuario1)
                        .espacio(espacio1)
                        .fechaInicio(LocalDateTime.now().withHour(10).withMinute(0).withSecond(0))
                        .fechaFin(LocalDateTime.now().withHour(11).withMinute(0).withSecond(0))
                        .estado("CONFIRMADA")
                        .build();

                // Reserva para hoy a las 14:00
                Reserva reserva2 = Reserva.builder()
                        .usuario(usuario1)
                        .espacio(espacio1)
                        .fechaInicio(LocalDateTime.now().withHour(14).withMinute(0).withSecond(0))
                        .fechaFin(LocalDateTime.now().withHour(15).withMinute(0).withSecond(0))
                        .estado("CONFIRMADA")
                        .build();

                reservaRepository.save(reserva1);
                reservaRepository.save(reserva2);
            }

            if (usuario2 != null && espacio2 != null) {
                // Reserva para mañana a las 09:00
                Reserva reserva3 = Reserva.builder()
                        .usuario(usuario2)
                        .espacio(espacio2)
                        .fechaInicio(LocalDateTime.now().plusDays(1).withHour(9).withMinute(0).withSecond(0))
                        .fechaFin(LocalDateTime.now().plusDays(1).withHour(12).withMinute(0).withSecond(0))
                        .estado("CONFIRMADA")
                        .build();

                reservaRepository.save(reserva3);
            }

            System.out.println("✓ Reservas de muestra creadas");
        }

        System.out.println("✓ Base de datos inicializada con datos de muestra");
    }
}
