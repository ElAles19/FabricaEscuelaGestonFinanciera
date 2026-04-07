# Guía Rápida: Sistema de Reservas - PruebaReservas

## 🚀 Inicio Rápido

### 1. Iniciar los Contenedores

```bash
cd E:\workspace\pruebas\ReservasCodeFactory
docker-compose up -d --build
```

### 2. Verificar que Todo Está Corriendo

```bash
docker ps --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"
```

**Esperado:**

```
NAMES                      STATUS              PORTS
prueba-reservas-frontend   Up                  0.0.0.0:3000->3000/tcp
prueba-reservas-backend    Up                  0.0.0.0:8080->8080/tcp
prueba-reservas-db         Up (healthy)        0.0.0.0:5432->5432/tcp
```

---

## 📊 Datos de Muestra Cargados

### Usuarios

1. **Juan Pérez** - juan.perez@example.com
2. **María García** - maria.garcia@example.com
3. **Carlos López** - carlos.lopez@example.com

### Espacios

1. **Sala de Juntas A** - Capacidad: 100 personas
2. **Sala de Reuniones B** - Capacidad: 30 personas
3. **Oficina C** - Capacidad: 10 personas

### Reservas Existentes

- Espacio 1: 2026-03-17 11:00-12:00
- Espacio 1: 2026-03-17 15:00-16:00
- Espacio 1: 2026-03-18 09:00-10:00
- Espacio 2: 2026-03-18 14:00-15:30
- Espacio 3: 2026-03-19 10:00-11:00

---

## 🔌 API REST Endpoints

### Usuarios

```bash
# Obtener todos los usuarios
curl -X GET http://localhost:8080/api/usuarios

# Obtener usuario específico
curl -X GET http://localhost:8080/api/usuarios/1

# Crear nuevo usuario
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Pedro",
    "email": "pedro@example.com",
    "telefono": "555-1234"
  }'
```

### Espacios

```bash
# Obtener todos los espacios
curl -X GET http://localhost:8080/api/espacios

# Obtener espacio específico
curl -X GET http://localhost:8080/api/espacios/1

# Crear nuevo espacio
curl -X POST http://localhost:8080/api/espacios \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Sala de Conferencias",
    "descripcion": "Gran sala para conferencias",
    "capacidad": 200,
    "ubicacion": "Piso 3"
  }'
```

### 📅 Calendario de Disponibilidad (HU-01) ⭐

```bash
# Obtener disponibilidad de un espacio para un día específico
curl -X GET "http://localhost:8080/api/calendario/dia/1?fecha=2026-03-17"

# Obtener disponibilidad por semana
curl -X GET "http://localhost:8080/api/calendario/semana/1?fechaInicio=2026-03-16"
```

**Respuesta del Calendario:**

```json
{
  "statusCode": 200,
  "message": "Calendario del día obtenido exitosamente",
  "data": {
    "espacioId": 1,
    "espacioNombre": "Sala de Juntas A",
    "fecha": "2026-03-17",
    "franjas": [
      {
        "horaInicio": "2026-03-17T10:00:00",
        "horaFin": "2026-03-17T11:00:00",
        "estado": "DISPONIBLE",
        "reservaId": null
      },
      {
        "horaInicio": "2026-03-17T11:00:00",
        "horaFin": "2026-03-17T12:00:00",
        "estado": "OCUPADO",
        "reservaId": 1
      }
    ],
    "totalDisponibles": 22,
    "totalOcupados": 2,
    "tieneDisponibilidad": true
  },
  "success": true
}
```

### Reservas

```bash
# Obtener reserva específica
curl -X GET http://localhost:8080/api/reservas/1

# Crear nueva reserva
curl -X POST http://localhost:8080/api/reservas \
  -H "Content-Type: application/json" \
  -d '{
    "usuarioId": 1,
    "espacioId": 2,
    "fechaInicio": "2026-03-17T14:00:00",
    "fechaFin": "2026-03-17T15:00:00"
  }'

# Cancelar reserva
curl -X DELETE http://localhost:8080/api/reservas/1

# Obtener mis reservas (usuario autenticado)
curl -X GET http://localhost:8080/api/reservas/mis-reservas
```

---

## 📂 Estructura de Archivos Importantes

```
ReservasCodeFactory/
├── backend/                              # Código Spring Boot
│   ├── src/main/java/com/pruebareservas/
│   │   ├── entity/                       # JPA Entities
│   │   │   ├── Usuario.java
│   │   │   ├── Espacio.java
│   │   │   └── Reserva.java
│   │   ├── dto/                          # Data Transfer Objects
│   │   │   ├── UsuarioDTO.java
│   │   │   ├── EspacioDTO.java
│   │   │   ├── ReservaDTO.java
│   │   │   ├── FranjasDTO.java
│   │   │   ├── CalendarioDTO.java
│   │   │   └── ApiResponseDTO.java
│   │   ├── repository/                   # Spring Data JPA
│   │   │   ├── UsuarioRepository.java
│   │   │   ├── EspacioRepository.java
│   │   │   └── ReservaRepository.java
│   │   ├── service/                      # Lógica de Negocio
│   │   │   ├── UsuarioService.java
│   │   │   ├── EspacioService.java
│   │   │   ├── ReservaService.java       # ⭐ Validación de conflictos
│   │   │   └── CalendarioService.java    # ⭐ HU-01 Generador de calendario
│   │   ├── controller/                   # REST Endpoints
│   │   │   ├── UsuarioController.java
│   │   │   ├── EspacioController.java
│   │   │   ├── ReservaController.java
│   │   │   └── CalendarioController.java # ⭐ HU-01 Endpoints
│   │   ├── config/
│   │   │   └── DataLoader.java           # Datos de muestra
│   │   └── PruebaReservasApplication.java
│   ├── src/test/java/com/pruebareservas/
│   │   ├── UsuarioServiceTest.java
│   │   ├── ReservaServiceTest.java       # ⭐ Tests HU-01
│   │   └── CalendarioServiceTest.java    # ⭐ Tests HU-01
│   ├── pom.xml                           # Dependencies Maven
│   └── Dockerfile.backend
│
├── FigmaMake/                            # Código React
│   ├── src/
│   │   ├── app/
│   │   │   ├── App.tsx
│   │   │   ├── routes.tsx
│   │   │   ├── pages/
│   │   │   │   ├── HomePage.tsx
│   │   │   │   ├── CalendarPage.tsx      # ⭐ UI para HU-01 (TODO)
│   │   │   │   └── LoginPage.tsx
│   │   │   └── components/
│   │   │       ├── WeeklyCalendar.tsx
│   │   │       └── SpaceSelector.tsx
│   │   └── main.tsx
│   ├── vite.config.ts
│   └── package.json
│
├── docs/
│   ├── serviciosBackend.md               # API Documentation
│   └── VALIDACION_HU01.md               # ⭐ Validation Report
│
├── docker-compose.yml
├── Dockerfile.frontend
├── Dockerfile.backend
└── .docker/
    ├── settings.xml                      # Maven settings
    └── nginx.conf                        # Nginx config
```

---

## 🗄️ Base de Datos PostgreSQL

### Conectarse a PostgreSQL

```bash
docker-compose exec postgres psql -U prueba_user -d prueba_reservas_db
```

### SQL Útiles

```sql
-- Ver todos los usuarios
SELECT * FROM usuarios;

-- Ver todos los espacios
SELECT * FROM espacios;

-- Ver todas las reservas
SELECT r.id, u.nombre, e.nombre, r.fecha_inicio, r.fecha_fin
FROM reservas r
JOIN usuarios u ON r.usuario_id = u.id
JOIN espacios e ON r.espacio_id = e.id;

-- Ver reservas de un espacio en una fecha
SELECT * FROM reservas
WHERE espacio_id = 1 AND DATE(fecha_inicio) = '2026-03-17'
ORDER BY fecha_inicio;

-- Eliminar todo y crear nuevo
DELETE FROM reservas;
DELETE FROM espacios;
DELETE FROM usuarios;
```

---

## 🧪 Ejecutar Tests

### Opción 1: Compilar y ejecutar tests dentro del contenedor Docker

El sistema compila automáticamente en el Dockerfile, los tests se ejecutan durante el `docker build`.

### Opción 2: Compilar localmente (requiere Maven y Java 17 instalados)

```bash
cd backend
mvn clean test
```

### Tests Disponibles

- `UsuarioServiceTest` - 3 casos
- `ReservaServiceTest` - 4 casos (incluyendo HU-01)
- `CalendarioServiceTest` - 4 casos (incluyendo HU-01)

---

## 🐛 Solución de Problemas

### Contenedor MongoDB no inicia

```bash
# Limpiar y reintentar
docker-compose down -v
docker system prune -f
docker-compose up -d --build
```

### Puerto 8080 ya está en uso

```bash
# Encontrar qué proceso usa el puerto
netstat -ano | findstr :8080

# O modificar docker-compose.yml para usar otro puerto
# Cambiar ports: "8080:8080" a "8081:8080"
```

### Tests fallan por ClassNotFoundException

```bash
# Compilar de nuevo sin caché
docker-compose build backend --no-cache
docker-compose up -d --build
```

### Ver logs del backend

```bash
docker-compose logs backend --follow
```

### Ver logs de la base de datos

```bash
docker-compose logs postgres --follow
```

---

## 📝 Notas de Desarrollo

### Agregar Nueva Entidad

1. Crear `NewEntity.java` en `backend/src/main/java/com/pruebareservas/entity/`
2. Crear `NewEntityDTO.java` en `backend/src/main/java/com/pruebareservas/dto/`
3. Crear `NewEntityRepository.java` en `backend/src/main/java/com/pruebareservas/repository/`
4. Crear `NewEntityService.java` en `backend/src/main/java/com/pruebareservas/service/`
5. Crear `NewEntityController.java` en `backend/src/main/java/com/pruebareservas/controller/`
6. Recompilar: `docker-compose build backend --no-cache`

### Agregar Nuevo Endpoint

- Agregar método en controller con `@GetMapping`, `@PostMapping`, etc.
- Documentar en `docs/serviciosBackend.md`
- Recompilar y reiniciar

### Cambios en BD

- DataLoader.java maneja la inicialización
- `spring.jpa.hibernate.ddl-auto=create-drop` en application.yml recrea tablas en cada inicio
- Para persistencia: cambiar a `update`

---

## 🎯 HU-01: Visualizar Disponibilidad (Estado: ✅ Completada)

### Endpoint Principal

```
GET /api/calendario/dia/{espacioId}?fecha=YYYY-MM-DD
```

### Funcionalidades Implementadas

✅ Mostrar 24 horas del día divididas en franjas de 1 hora  
✅ Marcar slots como DISPONIBLE u OCUPADO  
✅ Mostrar ID de reserva para slots ocupados  
✅ Calcular total de horas disponibles vs. ocupadas  
✅ Detectar conflictos de reserva automáticamente  
✅ Validar que no haya doble booking

### Falta Implementar

⏳ Autenticación JWT/Sessions en controladores  
⏳ Frontend para visualizar calendario  
⏳ Permitir usuario hacer nueva reserva desde UI

---

## 📞 Información de Contacto / Soporte

Para reportar problemas o sugerencias, contactar al equipo de desarrollo.

**Última Actualización:** 2026-03-17
