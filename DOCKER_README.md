# 📋 Prueba Reservas - Docker Setup

Sistema completo con Frontend React, Backend Spring Boot y PostgreSQL.

## 🏗️ Estructura del Proyecto

```
PruebaReservas/
├── FigmaMake/              # Frontend React + Vite + Tailwind
├── backend/                # Backend Spring Boot
├── .docker/                # Configuraciones Docker
├── Dockerfile.frontend     # Build del frontend
├── Dockerfile.backend      # Build del backend
├── docker-compose.yml      # Orquestación de contenedores
└── .dockerignore          # Exclusiones para Docker
```

## 🚀 Requisitos Previos

- Docker Desktop instalado y ejecutándose
- Docker Compose (incluido en Docker Desktop)

## 🏃 Instrucciones de Ejecución

### 1. Navega a la carpeta del proyecto

```bash
cd e:\workspace\pruebas\ReservasCodeFactory
```

### 2. Levanta los contenedores

```bash
docker-compose up --build
```

**Primera vez:** Tardará varios minutos descargando imágenes y compilando.

### 3. Espera a que todos estén listos

Verás mensajes similares a:

```
postgres  | database system is ready to accept connections
backend   | Tomcat started on port(s): 8080 (http) with context path ''
frontend  | 🐳 Nginx escuchando en puerto 3000
```

## 🌐 URLs de Acceso

| Servicio        | URL                       | Puerto |
| --------------- | ------------------------- | ------ |
| **Frontend**    | http://localhost:3000     | 3000   |
| **API Backend** | http://localhost:8080/api | 8080   |
| **PostgreSQL**  | localhost:5432            | 5432   |

## 📱 Endpoints Disponibles

### Frontend

- `http://localhost:3000` - Aplicación React con página "Hola Mundo"

### Backend API

- `GET http://localhost:8080/api/hello` - Retorna mensaje de saludo
- `GET http://localhost:8080/api/status` - Estado del sistema

**Ejemplo de respuesta:**

```json
{
  "message": "¡Hola Mundo desde Spring Boot!",
  "timestamp": "2024-03-16T10:30:45.123456"
}
```

## 🗄️ Base de Datos

**PostgreSQL Details:**

- Host: `localhost` (desde fuera) o `postgres` (desde contenedores)
- Puerto: 5432
- Database: `prueba_reservas_db`
- Usuario: `prueba_user`
- Contraseña: `prueba_password`

## 📝 Comandos Útiles

### Ver logs en tiempo real

```bash
docker-compose logs -f
```

### Ver logs de un servicio específico

```bash
docker-compose logs -f backend
docker-compose logs -f frontend
docker-compose logs -f postgres
```

### Detener los contenedores

```bash
docker-compose down
```

### Detener y borrar volúmenes (limpia la DB)

```bash
docker-compose down -v
```

### Reconstruir imágenes sin caché

```bash
docker-compose build --no-cache
```

### Reiniciar un servicio específico

```bash
docker-compose restart backend
```

## 🐛 Solución de Problemas

### El frontend no carga

1. Espera 30 segundos después de ejecutar `docker-compose up`
2. Recarga la página (Ctrl+F5)
3. Verifica los logs: `docker-compose logs frontend`

### El backend no responde

1. Verifica: `docker-compose logs backend`
2. Asegúrate que PostgreSQL está listo: `docker-compose logs postgres`
3. Reinicia: `docker-compose restart backend`

### Puerto en uso

```bash
# Windows - buscar proceso en puerto 3000
netstat -ano | findstr :3000

# Cambiar puertos en docker-compose.yml
```

### Acceder a la base de datos

```bash
docker-compose exec postgres psql -U prueba_user -d prueba_reservas_db
```

## 🔧 Personalización

### Cambiar puertos

Edita `docker-compose.yml`:

```yaml
ports:
  - "3000:3000" # Cambiar primer número para puerto local
  - "8080:8080"
  - "5432:5432"
```

### Variables de entorno

Crea un archivo `.env` en la raíz:

```env
POSTGRES_USER=mi_usuario
POSTGRES_PASSWORD=mi_contraseña
POSTGRES_DB=mi_base_datos
```

## 📚 Próximos Pasos

1. **Frontend:** Modifica `FigmaMake/src/pages/HomePage.tsx`
2. **Backend:** Agrega controladores en `backend/src/main/java/com/pruebareservas/controller/`
3. **BD:** Define entidades en `backend/src/main/java/com/pruebareservas/entity/`

## 🎯 Verificación Rápida

```bash
# Test del frontend
curl http://localhost:3000

# Test del backend
curl http://localhost:8080/api/hello

# Test de la BD
curl http://localhost:8080/api/status
```

---

**¡Sistema listo para comenzar a desarrollar!** 🚀
