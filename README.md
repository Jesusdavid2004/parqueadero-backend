# Sistema de Parqueadero

Sistema de gestion de parqueadero con arquitectura cliente-servidor. Backend en Spring Boot 4.0.6 + Java 17, frontend en Angular 21, base de datos PostgreSQL, desplegado en Railway.

---

## Tecnologias

| Capa | Tecnologia |
|------|-----------|
| Backend | Spring Boot 4.0.6, Java 17, Spring Security, Spring Data JPA |
| Frontend | Angular 21, TypeScript, SSR |
| Base de datos | PostgreSQL 18.4 |
| Auth | HTTP Basic + BCrypt |
| Build | Maven, Docker (multi-stage) |
| Despliegue | Railway Cloud |

---

## Estructura del Proyecto

```
parqueadero/
├── src/main/java/com/parqueadero/
│   ├── controller/        # REST API (15 controladores)
│   ├── service/           # Logica de negocio (interfaces + impl)
│   ├── repository/        # Interfaces JPA (JpaRepository)
│   ├── model/             # Entidades JPA
│   ├── dto/               # Data Transfer Objects
│   ├── enums/             # Enumeraciones
│   ├── security/          # Configuracion Spring Security
│   └── exception/         # Manejo de errores
├── src/main/resources/
│   ├── application.properties
│   └── *.sql              # Scripts de datos
├── diagramas/             # Diagramas UML (PlantUML)
├── Dockerfile
├── railway.json
└── pom.xml
```

---

## Diagramas

Los diagramas estan en formato PlantUML (`.puml`) dentro de la carpeta `diagramas/`. Se pueden visualizar con:
- [PlantUML Online Server](https://www.plantuml.com/plantuml/uml/)
- Extension de VS Code: PlantUML
- IntelliJ IDEA: PlantUML Integration

| # | Diagrama | Archivo | Descripcion |
|---|----------|---------|-------------|
| 1 | Contexto | `diagramas/01-contexto.puml` | Vision general del sistema, actores y sistemas externos |
| 2 | Despliegue | `diagramas/02-despliegue.puml` | Infraestructura en Railway: contenedores, puertos y conexiones |
| 3 | Conceptual | `diagramas/03-conceptual.puml` | Modelo de dominio completo: entidades, relaciones, herencia y enums |
| 4 | Desarrollo | `diagramas/04-desarrollo.puml` | Arquitectura por capas: frontend y backend con sus componentes |
| 5 | Funcional | `diagramas/05-funcional.puml` | Casos de uso organizados por actor (Admin/Cliente) |

---

## API Endpoints

### Autenticacion
| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| POST | `/api/auth/login` | Iniciar sesion |
| POST | `/api/auth/register` | Registrar usuario |
| GET | `/api/auth/me` | Perfil del usuario autenticado |

### Clientes
| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/clientes` | Listar clientes |
| GET | `/api/clientes/{id}` | Buscar por ID |
| POST | `/api/clientes` | Crear cliente |
| PUT | `/api/clientes/{id}` | Actualizar cliente |
| DELETE | `/api/clientes/{id}` | Eliminar cliente |

### Vehiculos
| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/vehiculos` | Listar vehiculos |
| GET | `/api/vehiculos/{id}` | Buscar por ID |
| POST | `/api/vehiculos` | Crear vehiculo |
| PUT | `/api/vehiculos/{id}` | Actualizar vehiculo |
| DELETE | `/api/vehiculos/{id}` | Eliminar vehiculo |

### Tickets
| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/tickets` | Listar tickets |
| GET | `/api/tickets/{id}` | Buscar por ID |
| POST | `/api/tickets` | Crear ticket |
| PUT | `/api/tickets/{id}` | Actualizar ticket |
| DELETE | `/api/tickets/{id}` | Eliminar ticket |

### Pagos
| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/pagos` | Listar pagos |
| GET | `/api/pagos/{id}` | Buscar por ID |
| POST | `/api/pagos` | Crear pago |
| DELETE | `/api/pagos/{id}` | Eliminar pago |

### Facturas
| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/facturas` | Listar facturas |
| GET | `/api/facturas/{id}` | Buscar por ID |
| POST | `/api/facturas` | Crear factura |
| DELETE | `/api/facturas/{id}` | Eliminar factura |

### Reservas
| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/reservas` | Listar reservas |
| GET | `/api/reservas/{id}` | Buscar por ID |
| POST | `/api/reservas` | Crear reserva |
| PUT | `/api/reservas/{id}` | Actualizar reserva |
| DELETE | `/api/reservas/{id}` | Eliminar reserva |

### Espacios de Parqueo
| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/espacios` | Listar espacios |
| GET | `/api/espacios/disponibles` | Espacios disponibles |
| GET | `/api/espacios/{id}` | Buscar por ID |
| POST | `/api/espacios` | Crear espacio |
| PUT | `/api/espacios/{id}` | Actualizar espacio |
| DELETE | `/api/espacios/{id}` | Eliminar espacio |

### Sedes
| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/sedes` | Listar sedes |
| GET | `/api/sedes/{id}` | Buscar por ID |
| POST | `/api/sedes` | Crear sede |
| PUT | `/api/sedes/{id}` | Actualizar sede |
| DELETE | `/api/sedes/{id}` | Eliminar sede |

### Zonas
| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/zonas` | Listar zonas |
| GET | `/api/zonas/{id}` | Buscar por ID |
| POST | `/api/zonas` | Crear zona |
| PUT | `/api/zonas/{id}` | Actualizar zona |
| DELETE | `/api/zonas/{id}` | Eliminar zona |

### Tarifas
| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/tarifas` | Listar tarifas |
| GET | `/api/tarifas/{id}` | Buscar por ID |
| POST | `/api/tarifas` | Crear tarifa |
| PUT | `/api/tarifas/{id}` | Actualizar tarifa |
| DELETE | `/api/tarifas/{id}` | Eliminar tarifa |

### Empleados
| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/empleados` | Listar empleados |
| GET | `/api/empleados/{id}` | Buscar por ID |
| POST | `/api/empleados` | Crear empleado |
| PUT | `/api/empleados/{id}` | Actualizar empleado |
| DELETE | `/api/empleados/{id}` | Eliminar empleado |

### Menu
| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/menus` | Listar items (plano) |
| GET | `/api/menus/arbol` | Arbol jerarquico |
| GET | `/api/menus/{id}` | Buscar por ID |
| POST | `/api/menus` | Crear item |
| PUT | `/api/menus/{id}` | Actualizar item |
| DELETE | `/api/menus/{id}` | Eliminar item |

### Zona Cliente (consultas)
| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/cliente/dashboard` | Dashboard del cliente |
| GET | `/api/cliente/vehiculos` | Mis vehiculos |
| GET | `/api/cliente/tickets` | Mis tickets |

---

## Modelo de Dominio

### Entidades principales

- **Persona** (abstracta) -> Cliente, Empleado
- **Vehiculo** (abstracta) -> Carro, Moto, Camion
- **Sede** -> contiene **Zonas** -> contiene **EspaciosParqueo**
- **Ticket** -> vincula Vehiculo + EspacioParqueo
- **Pago** -> liquida un Ticket
- **Factura** -> generada por un Pago
- **Reserva** -> Cliente + Vehiculo + EspacioParqueo
- **Usuario** -> credenciales de acceso (rol ADMIN/CLIENTE)
- **Menu** -> arbol de navegacion jerarquico

### Enums

| Enum | Valores |
|------|---------|
| RolUsuario | ADMIN, CLIENTE |
| TipoVehiculo | CARRO, MOTO, CAMION |
| TipoZona | CARROS, MOTOS, CAMIONES, MIXTA |
| EstadoEspacio | DISPONIBLE, OCUPADO, RESERVADO, INACTIVO |
| EstadoTicket | ABIERTO, CERRADO, FACTURADO |
| EstadoPago | PENDIENTE, APROBADO, RECHAZADO, REEMBOLSADO |
| MetodoPago | EFECTIVO, TARJETA, TRANSFERENCIA, QR |
| EstadoReserva | PENDIENTE, CONFIRMADA, CANCELADA, VENCIDA |

---

## Roles y Permisos

| Rol | Acceso |
|-----|--------|
| **ADMIN** | Gestion completa: CRUD de clientes, empleados, vehiculos, espacios, sedes, zonas, tarifas, tickets, pagos, facturas, reservas, menu |
| **CLIENTE** | Solo consultas: dashboard, mis vehiculos, mis tickets |

---

## Configuracion Local

### Requisitos
- Java 17+
- Maven 3.9+
- PostgreSQL 15+

### Variables de entorno

```properties
PGHOST=localhost
PGPORT=5432
PGDATABASE=parqueadero
PGUSER=postgres
PGPASSWORD=tu_password
PORT=8080
```

### Ejecutar

```bash
cd parqueadero
./mvnw spring-boot:run
```

### Construir JAR

```bash
./mvnw clean package -DskipTests
java -jar target/parqueadero-0.0.1-SNAPSHOT.jar
```

---

## Docker

```bash
docker build -t parqueadero .
docker run -p 8080:8080 \
  -e PGHOST=db \
  -e PGPORT=5432 \
  -e PGDATABASE=parqueadero \
  -e PGUSER=postgres \
  -e PGPASSWORD=secret \
  parqueadero
```

---

## Despliegue en Railway

El proyecto esta configurado para Railway con `railway.json`:
- Builder: Dockerfile
- Healthcheck: `/api/debug/ver-menus`
- Timeout: 120s

La base de datos PostgreSQL es administrada por Railway (conexion interna via `postgres.railway.internal:5432`).
