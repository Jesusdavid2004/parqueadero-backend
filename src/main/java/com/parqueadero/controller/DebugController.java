package com.parqueadero.controller;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.parqueadero.enums.RolUsuario;
import com.parqueadero.model.Cliente;
import com.parqueadero.model.Menu;
import com.parqueadero.model.Usuario;
import com.parqueadero.repository.ClienteRepository;
import com.parqueadero.repository.MenuRepository;
import com.parqueadero.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final MenuRepository menuRepository;
    private final JdbcTemplate jdbcTemplate;

    public DebugController(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository, 
                           ClienteRepository clienteRepository, MenuRepository menuRepository,
                           JdbcTemplate jdbcTemplate) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.menuRepository = menuRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/seed-database")
    public String seedDatabase() {
        StringBuilder result = new StringBuilder();
        result.append("=== SEMBRANDO BASE DE DATOS ===\n\n");

        try {
            // Limpiar tablas en orden correcto
            jdbcTemplate.execute("DELETE FROM facturas");
            jdbcTemplate.execute("DELETE FROM pagos");
            jdbcTemplate.execute("DELETE FROM reservas");
            jdbcTemplate.execute("DELETE FROM tickets");
            jdbcTemplate.execute("DELETE FROM usuarios");
            jdbcTemplate.execute("DELETE FROM camiones");
            jdbcTemplate.execute("DELETE FROM carros");
            jdbcTemplate.execute("DELETE FROM motos");
            jdbcTemplate.execute("DELETE FROM vehiculos");
            jdbcTemplate.execute("DELETE FROM espacios_parqueo");
            jdbcTemplate.execute("DELETE FROM zonas");
            jdbcTemplate.execute("DELETE FROM sedes");
            jdbcTemplate.execute("DELETE FROM tarifas");
            jdbcTemplate.execute("DELETE FROM clientes");
            jdbcTemplate.execute("DELETE FROM menu");
            result.append("✓ Tablas limpiadas\n");

            // Reiniciar secuencias
            jdbcTemplate.execute("ALTER SEQUENCE facturas_id_seq RESTART WITH 1");
            jdbcTemplate.execute("ALTER SEQUENCE pagos_id_seq RESTART WITH 1");
            jdbcTemplate.execute("ALTER SEQUENCE reservas_id_seq RESTART WITH 1");
            jdbcTemplate.execute("ALTER SEQUENCE tickets_id_seq RESTART WITH 1");
            jdbcTemplate.execute("ALTER SEQUENCE usuarios_id_seq RESTART WITH 1");
            jdbcTemplate.execute("ALTER SEQUENCE vehiculos_id_seq RESTART WITH 1");
            jdbcTemplate.execute("ALTER SEQUENCE espacios_parqueo_id_seq RESTART WITH 1");
            jdbcTemplate.execute("ALTER SEQUENCE zonas_id_seq RESTART WITH 1");
            jdbcTemplate.execute("ALTER SEQUENCE sedes_id_seq RESTART WITH 1");
            jdbcTemplate.execute("ALTER SEQUENCE tarifas_id_seq RESTART WITH 1");
            jdbcTemplate.execute("ALTER SEQUENCE clientes_id_seq RESTART WITH 1");
            jdbcTemplate.execute("ALTER SEQUENCE menu_id_seq RESTART WITH 1");
            result.append("✓ Secuencias reiniciadas\n");

            // Insertar clientes
            jdbcTemplate.execute("INSERT INTO clientes (id, identificacion, nombre, telefono, correo, direccion, activo, fecha_creacion, fecha_actualizacion) VALUES (1, '1234567890', 'Juan Pérez', '3001234567', 'juan@test.com', 'Calle 123 #45-67', true, NOW(), NOW())");
            jdbcTemplate.execute("INSERT INTO clientes (id, identificacion, nombre, telefono, correo, direccion, activo, fecha_creacion, fecha_actualizacion) VALUES (2, '0987654321', 'María López', '3109876543', 'maria@test.com', 'Carrera 10 #20-30', true, NOW(), NOW())");
            jdbcTemplate.execute("INSERT INTO clientes (id, identificacion, nombre, telefono, correo, direccion, activo, fecha_creacion, fecha_actualizacion) VALUES (3, '1122334455', 'Carlos Rodríguez', '3201122334', 'carlos@test.com', 'Avenida 5 #15-25', true, NOW(), NOW())");
            result.append("✓ 3 clientes creados\n");

            // Insertar vehículos
            jdbcTemplate.execute("INSERT INTO vehiculos (id, placa, marca, modelo, color, tipo_vehiculo, cliente_id, activo, fecha_creacion, fecha_actualizacion) VALUES (1, 'ABC123', 'Toyota', 'Corolla', 'Rojo', 'CARRO', 1, true, NOW(), NOW())");
            jdbcTemplate.execute("INSERT INTO vehiculos (id, placa, marca, modelo, color, tipo_vehiculo, cliente_id, activo, fecha_creacion, fecha_actualizacion) VALUES (2, 'XYZ789', 'Yamaha', 'FZ', 'Negro', 'MOTO', 1, true, NOW(), NOW())");
            jdbcTemplate.execute("INSERT INTO vehiculos (id, placa, marca, modelo, color, tipo_vehiculo, cliente_id, activo, fecha_creacion, fecha_actualizacion) VALUES (3, 'DEF456', 'Mazda', '3', 'Azul', 'CARRO', 2, true, NOW(), NOW())");
            jdbcTemplate.execute("INSERT INTO vehiculos (id, placa, marca, modelo, color, tipo_vehiculo, cliente_id, activo, fecha_creacion, fecha_actualizacion) VALUES (4, 'GHI789', 'Honda', 'CBR', 'Rojo', 'MOTO', 2, true, NOW(), NOW())");
            jdbcTemplate.execute("INSERT INTO vehiculos (id, placa, marca, modelo, color, tipo_vehiculo, cliente_id, activo, fecha_creacion, fecha_actualizacion) VALUES (5, 'JKL012', 'Chevrolet', 'Silverado', 'Blanco', 'CAMION', 3, true, NOW(), NOW())");
            
            // Subtipos
            jdbcTemplate.execute("INSERT INTO carros (id, numero_puertas) VALUES (1, 4)");
            jdbcTemplate.execute("INSERT INTO carros (id, numero_puertas) VALUES (3, 4)");
            jdbcTemplate.execute("INSERT INTO motos (id, cilindraje, tiene_maletero) VALUES (2, 250, false)");
            jdbcTemplate.execute("INSERT INTO motos (id, cilindraje, tiene_maletero) VALUES (4, 600, true)");
            jdbcTemplate.execute("INSERT INTO camiones (id, numero_ejes, capacidad_carga) VALUES (5, 2, 3500.0)");
            result.append("✓ 5 vehículos creados\n");

            // Insertar sedes
            jdbcTemplate.execute("INSERT INTO sedes (id, nombre, direccion, ciudad, activo, fecha_creacion, fecha_actualizacion) VALUES (1, 'Sede Principal', 'Av. Siempre Viva 742', 'Bogotá', true, NOW(), NOW())");
            jdbcTemplate.execute("INSERT INTO sedes (id, nombre, direccion, ciudad, activo, fecha_creacion, fecha_actualizacion) VALUES (2, 'Sede Norte', 'Calle 100 #15-20', 'Bogotá', true, NOW(), NOW())");
            result.append("✓ 2 sedes creadas\n");

            // Insertar zonas
            jdbcTemplate.execute("INSERT INTO zonas (id, nombre, descripcion, tipo_zona, sede_id, activo, fecha_creacion, fecha_actualizacion) VALUES (1, 'Zona Carros A', 'Zona exclusiva para carros - Piso 1', 'CARROS', 1, true, NOW(), NOW())");
            jdbcTemplate.execute("INSERT INTO zonas (id, nombre, descripcion, tipo_zona, sede_id, activo, fecha_creacion, fecha_actualizacion) VALUES (2, 'Zona Motos A', 'Zona exclusiva para motos - Piso 1', 'MOTOS', 1, true, NOW(), NOW())");
            jdbcTemplate.execute("INSERT INTO zonas (id, nombre, descripcion, tipo_zona, sede_id, activo, fecha_creacion, fecha_actualizacion) VALUES (3, 'Zona Mixta B', 'Zona mixta - Piso 2', 'MIXTA', 1, true, NOW(), NOW())");
            jdbcTemplate.execute("INSERT INTO zonas (id, nombre, descripcion, tipo_zona, sede_id, activo, fecha_creacion, fecha_actualizacion) VALUES (4, 'Zona Carros Norte', 'Zona carros sede norte', 'CARROS', 2, true, NOW(), NOW())");
            result.append("✓ 4 zonas creadas\n");

            // Insertar espacios
            jdbcTemplate.execute("INSERT INTO espacios_parqueo (id, codigo, ubicacion, estado, tipo_vehiculo_permitido, zona_id, activo, fecha_creacion, fecha_actualizacion) VALUES (1, 'A-01', 'Piso 1 - Sección A', 'DISPONIBLE', 'CARRO', 1, true, NOW(), NOW())");
            jdbcTemplate.execute("INSERT INTO espacios_parqueo (id, codigo, ubicacion, estado, tipo_vehiculo_permitido, zona_id, activo, fecha_creacion, fecha_actualizacion) VALUES (2, 'A-02', 'Piso 1 - Sección A', 'DISPONIBLE', 'CARRO', 1, true, NOW(), NOW())");
            jdbcTemplate.execute("INSERT INTO espacios_parqueo (id, codigo, ubicacion, estado, tipo_vehiculo_permitido, zona_id, activo, fecha_creacion, fecha_actualizacion) VALUES (3, 'A-03', 'Piso 1 - Sección A', 'DISPONIBLE', 'CARRO', 1, true, NOW(), NOW())");
            jdbcTemplate.execute("INSERT INTO espacios_parqueo (id, codigo, ubicacion, estado, tipo_vehiculo_permitido, zona_id, activo, fecha_creacion, fecha_actualizacion) VALUES (4, 'B-01', 'Piso 1 - Sección B', 'DISPONIBLE', 'MOTO', 2, true, NOW(), NOW())");
            jdbcTemplate.execute("INSERT INTO espacios_parqueo (id, codigo, ubicacion, estado, tipo_vehiculo_permitido, zona_id, activo, fecha_creacion, fecha_actualizacion) VALUES (5, 'B-02', 'Piso 1 - Sección B', 'DISPONIBLE', 'MOTO', 2, true, NOW(), NOW())");
            jdbcTemplate.execute("INSERT INTO espacios_parqueo (id, codigo, ubicacion, estado, tipo_vehiculo_permitido, zona_id, activo, fecha_creacion, fecha_actualizacion) VALUES (6, 'B-03', 'Piso 1 - Sección B', 'DISPONIBLE', 'MOTO', 2, true, NOW(), NOW())");
            jdbcTemplate.execute("INSERT INTO espacios_parqueo (id, codigo, ubicacion, estado, tipo_vehiculo_permitido, zona_id, activo, fecha_creacion, fecha_actualizacion) VALUES (7, 'C-01', 'Piso 2 - Sección C', 'DISPONIBLE', 'CARRO', 3, true, NOW(), NOW())");
            jdbcTemplate.execute("INSERT INTO espacios_parqueo (id, codigo, ubicacion, estado, tipo_vehiculo_permitido, zona_id, activo, fecha_creacion, fecha_actualizacion) VALUES (8, 'C-02', 'Piso 2 - Sección C', 'DISPONIBLE', 'MOTO', 3, true, NOW(), NOW())");
            jdbcTemplate.execute("INSERT INTO espacios_parqueo (id, codigo, ubicacion, estado, tipo_vehiculo_permitido, zona_id, activo, fecha_creacion, fecha_actualizacion) VALUES (9, 'D-01', 'Sede Norte - Sección D', 'DISPONIBLE', 'CARRO', 4, true, NOW(), NOW())");
            jdbcTemplate.execute("INSERT INTO espacios_parqueo (id, codigo, ubicacion, estado, tipo_vehiculo_permitido, zona_id, activo, fecha_creacion, fecha_actualizacion) VALUES (10, 'D-02', 'Sede Norte - Sección D', 'DISPONIBLE', 'CARRO', 4, true, NOW(), NOW())");
            result.append("✓ 10 espacios de parqueo creados\n");

            // Insertar tarifas
            jdbcTemplate.execute("INSERT INTO tarifas (id, tipo_vehiculo, valor_hora, valor_dia, valor_fraccion, activo, fecha_creacion, fecha_actualizacion) VALUES (1, 'CARRO', 5000.0, 40000.0, 1500.0, true, NOW(), NOW())");
            jdbcTemplate.execute("INSERT INTO tarifas (id, tipo_vehiculo, valor_hora, valor_dia, valor_fraccion, activo, fecha_creacion, fecha_actualizacion) VALUES (2, 'MOTO', 3000.0, 25000.0, 1000.0, true, NOW(), NOW())");
            jdbcTemplate.execute("INSERT INTO tarifas (id, tipo_vehiculo, valor_hora, valor_dia, valor_fraccion, activo, fecha_creacion, fecha_actualizacion) VALUES (3, 'CAMION', 8000.0, 60000.0, 2500.0, true, NOW(), NOW())");
            result.append("✓ 3 tarifas creadas\n");

            // Insertar tickets
            jdbcTemplate.execute("INSERT INTO tickets (id, codigo_ticket, hora_entrada, hora_salida, estado, total, vehiculo_id, espacio_id, activo, fecha_creacion, fecha_actualizacion) VALUES (1, 'TK-001', NOW() - INTERVAL '2 hours', NOW(), 'CERRADO', 10000.0, 1, 1, true, NOW(), NOW())");
            jdbcTemplate.execute("INSERT INTO tickets (id, codigo_ticket, hora_entrada, hora_salida, estado, total, vehiculo_id, espacio_id, activo, fecha_creacion, fecha_actualizacion) VALUES (2, 'TK-002', NOW() - INTERVAL '1 hour', NULL, 'ABIERTO', 0.0, 2, 4, true, NOW(), NOW())");
            jdbcTemplate.execute("INSERT INTO tickets (id, codigo_ticket, hora_entrada, hora_salida, estado, total, vehiculo_id, espacio_id, activo, fecha_creacion, fecha_actualizacion) VALUES (3, 'TK-003', NOW() - INTERVAL '3 hours', NOW() - INTERVAL '1 hour', 'CERRADO', 10000.0, 3, 2, true, NOW(), NOW())");
            jdbcTemplate.execute("INSERT INTO tickets (id, codigo_ticket, hora_entrada, hora_salida, estado, total, vehiculo_id, espacio_id, activo, fecha_creacion, fecha_actualizacion) VALUES (4, 'TK-004', NOW() - INTERVAL '30 minutes', NULL, 'ABIERTO', 0.0, 4, 5, true, NOW(), NOW())");
            jdbcTemplate.execute("UPDATE espacios_parqueo SET estado = 'OCUPADO' WHERE id IN (4, 5)");
            result.append("✓ 4 tickets creados\n");

            // Insertar pagos
            jdbcTemplate.execute("INSERT INTO pagos (id, monto, metodo_pago, estado, referencia, fecha, ticket_id, activo, fecha_creacion, fecha_actualizacion) VALUES (1, 10000.0, 'EFECTIVO', 'APROBADO', 'PAY-001', NOW(), 1, true, NOW(), NOW())");
            jdbcTemplate.execute("INSERT INTO pagos (id, monto, metodo_pago, estado, referencia, fecha, ticket_id, activo, fecha_creacion, fecha_actualizacion) VALUES (2, 10000.0, 'TARJETA', 'APROBADO', 'PAY-002', NOW() - INTERVAL '1 hour', 3, true, NOW(), NOW())");
            result.append("✓ 2 pagos creados\n");

            // Insertar facturas
            jdbcTemplate.execute("INSERT INTO facturas (id, numero_factura, subtotal, impuesto, total, pago_id, activo, fecha_creacion, fecha_actualizacion) VALUES (1, 'FAC-000001', 8403.36, 1596.64, 10000.0, 1, true, NOW(), NOW())");
            jdbcTemplate.execute("INSERT INTO facturas (id, numero_factura, subtotal, impuesto, total, pago_id, activo, fecha_creacion, fecha_actualizacion) VALUES (2, 'FAC-000002', 8403.36, 1596.64, 10000.0, 2, true, NOW() - INTERVAL '1 hour', NOW())");
            result.append("✓ 2 facturas creadas\n");

            // Insertar menús
            jdbcTemplate.execute("INSERT INTO menu (id, nombre, ruta, activo, padre_id) VALUES (1, 'Dashboard', '/admin', true, NULL)");
            jdbcTemplate.execute("INSERT INTO menu (id, nombre, ruta, activo, padre_id) VALUES (2, 'Clientes', '/admin/clientes', true, NULL)");
            jdbcTemplate.execute("INSERT INTO menu (id, nombre, ruta, activo, padre_id) VALUES (3, 'Vehículos', '/admin/vehiculos', true, NULL)");
            jdbcTemplate.execute("INSERT INTO menu (id, nombre, ruta, activo, padre_id) VALUES (4, 'Sedes', '/admin/sedes', true, NULL)");
            jdbcTemplate.execute("INSERT INTO menu (id, nombre, ruta, activo, padre_id) VALUES (5, 'Zonas', '/admin/zonas', true, NULL)");
            jdbcTemplate.execute("INSERT INTO menu (id, nombre, ruta, activo, padre_id) VALUES (6, 'Espacios', '/admin/espacios', true, NULL)");
            jdbcTemplate.execute("INSERT INTO menu (id, nombre, ruta, activo, padre_id) VALUES (7, 'Tarifas', '/admin/tarifas', true, NULL)");
            jdbcTemplate.execute("INSERT INTO menu (id, nombre, ruta, activo, padre_id) VALUES (8, 'Tickets', '/admin/tickets', true, NULL)");
            jdbcTemplate.execute("INSERT INTO menu (id, nombre, ruta, activo, padre_id) VALUES (9, 'Pagos', '/admin/pagos-facturas', true, NULL)");
            jdbcTemplate.execute("INSERT INTO menu (id, nombre, ruta, activo, padre_id) VALUES (10, 'Facturas', '/admin/pagos-facturas', true, NULL)");
            jdbcTemplate.execute("INSERT INTO menu (id, nombre, ruta, activo, padre_id) VALUES (11, 'Empleados', '/admin/empleados', true, NULL)");
            jdbcTemplate.execute("INSERT INTO menu (id, nombre, ruta, activo, padre_id) VALUES (12, 'Menús', '/admin/menu', true, NULL)");
            jdbcTemplate.execute("INSERT INTO menu (id, nombre, ruta, activo, padre_id) VALUES (13, 'Gestión de Sedes', NULL, true, 4)");
            jdbcTemplate.execute("INSERT INTO menu (id, nombre, ruta, activo, padre_id) VALUES (14, 'Listar Sedes', '/admin/sedes', true, 4)");
            jdbcTemplate.execute("INSERT INTO menu (id, nombre, ruta, activo, padre_id) VALUES (15, 'Crear Sede', '/admin/sedes', true, 4)");
            jdbcTemplate.execute("INSERT INTO menu (id, nombre, ruta, activo, padre_id) VALUES (16, 'Gestión de Zonas', NULL, true, 5)");
            jdbcTemplate.execute("INSERT INTO menu (id, nombre, ruta, activo, padre_id) VALUES (17, 'Listar Zonas', '/admin/zonas', true, 5)");
            jdbcTemplate.execute("INSERT INTO menu (id, nombre, ruta, activo, padre_id) VALUES (18, 'Crear Zona', '/admin/zonas', true, 5)");
            result.append("✓ 18 menús creados\n");

            // Actualizar secuencias
            jdbcTemplate.execute("SELECT setval('clientes_id_seq', (SELECT MAX(id) FROM clientes))");
            jdbcTemplate.execute("SELECT setval('vehiculos_id_seq', (SELECT MAX(id) FROM vehiculos))");
            jdbcTemplate.execute("SELECT setval('sedes_id_seq', (SELECT MAX(id) FROM sedes))");
            jdbcTemplate.execute("SELECT setval('zonas_id_seq', (SELECT MAX(id) FROM zonas))");
            jdbcTemplate.execute("SELECT setval('espacios_parqueo_id_seq', (SELECT MAX(id) FROM espacios_parqueo))");
            jdbcTemplate.execute("SELECT setval('tarifas_id_seq', (SELECT MAX(id) FROM tarifas))");
            jdbcTemplate.execute("SELECT setval('tickets_id_seq', (SELECT MAX(id) FROM tickets))");
            jdbcTemplate.execute("SELECT setval('pagos_id_seq', (SELECT MAX(id) FROM pagos))");
            jdbcTemplate.execute("SELECT setval('facturas_id_seq', (SELECT MAX(id) FROM facturas))");
            jdbcTemplate.execute("SELECT setval('menu_id_seq', (SELECT MAX(id) FROM menu))");
            result.append("✓ Secuencias actualizadas\n");

            result.append("\n=== BASE DE DATOS SEMBRADA EXITOSAMENTE ===\n");

        } catch (Exception e) {
            result.append("\n✗ ERROR: ").append(e.getMessage()).append("\n");
        }

        return result.toString();
    }

    @PostMapping("/update-password")
    public String updatePassword(@RequestParam String username, @RequestParam String newPassword) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
        
        usuario.setPassword(passwordEncoder.encode(newPassword));
        usuarioRepository.save(usuario);
        
        return "Contraseña actualizada para: " + username;
    }

    @PostMapping("/generate-hash")
    public String generateHash(@RequestParam String password) {
        return passwordEncoder.encode(password);
    }

    @GetMapping("/list-users")
    public String listUsers() {
        StringBuilder sb = new StringBuilder();
        usuarioRepository.findAll().forEach(u -> {
            sb.append("ID: ").append(u.getId())
              .append(" | Username: ").append(u.getUsername())
              .append(" | Email: ").append(u.getEmail())
              .append(" | Rol: ").append(u.getRol())
              .append(" | ClienteId: ").append(u.getCliente() != null ? u.getCliente().getId() : "null")
              .append("\n");
        });
        return sb.toString();
    }

    @PostMapping("/create-test-users")
    public String createTestUsers() {
        StringBuilder result = new StringBuilder();

        // Crear usuario ADMIN
        if (!usuarioRepository.existsByUsername("admin")) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setEmail("admin@test.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRol(RolUsuario.ADMIN);
            admin.setCliente(null);
            usuarioRepository.save(admin);
            result.append("Usuario ADMIN creado: admin / admin123\n");
        } else {
            result.append("Usuario admin ya existe\n");
        }

        // Crear usuario CLIENTE asociado al cliente_id=1
        if (!usuarioRepository.existsByUsername("cliente1")) {
            Cliente cliente = clienteRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("Cliente con id=1 no encontrado. Ejecuta primero reset-database.sql"));
            
            Usuario clienteUser = new Usuario();
            clienteUser.setUsername("cliente1");
            clienteUser.setEmail("cliente1@test.com");
            clienteUser.setPassword(passwordEncoder.encode("1234"));
            clienteUser.setRol(RolUsuario.CLIENTE);
            clienteUser.setCliente(cliente);
            usuarioRepository.save(clienteUser);
            result.append("Usuario CLIENTE creado: cliente1 / 1234 (asociado a cliente_id=1)\n");
        } else {
            result.append("Usuario cliente1 ya existe\n");
        }

        // Crear usuario CLIENTE asociado al cliente_id=2
        if (!usuarioRepository.existsByUsername("cliente2")) {
            Cliente cliente2 = clienteRepository.findById(2L)
                    .orElseThrow(() -> new RuntimeException("Cliente con id=2 no encontrado. Ejecuta primero reset-database.sql"));
            
            Usuario clienteUser2 = new Usuario();
            clienteUser2.setUsername("cliente2");
            clienteUser2.setEmail("cliente2@test.com");
            clienteUser2.setPassword(passwordEncoder.encode("1234"));
            clienteUser2.setRol(RolUsuario.CLIENTE);
            clienteUser2.setCliente(cliente2);
            usuarioRepository.save(clienteUser2);
            result.append("Usuario CLIENTE creado: cliente2 / 1234 (asociado a cliente_id=2)\n");
        } else {
            result.append("Usuario cliente2 ya existe\n");
        }

        return result.toString();
    }

    @PostMapping("/test-login")
    public String testLogin(@RequestParam String username, @RequestParam String password) {
        StringBuilder result = new StringBuilder();
        
        try {
            Usuario usuario = usuarioRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
            
            result.append("Usuario encontrado:\n");
            result.append("  ID: ").append(usuario.getId()).append("\n");
            result.append("  Username: ").append(usuario.getUsername()).append("\n");
            result.append("  Email: ").append(usuario.getEmail()).append("\n");
            result.append("  Rol: ").append(usuario.getRol()).append("\n");
            result.append("  ClienteId: ").append(usuario.getCliente() != null ? usuario.getCliente().getId() : "null").append("\n");
            result.append("  Password en BD: ").append(usuario.getPassword()).append("\n");
            
            boolean matches = passwordEncoder.matches(password, usuario.getPassword());
            result.append("\n¿La contraseña coincide?: ").append(matches).append("\n");
            
            if (!matches) {
                String newHash = passwordEncoder.encode(password);
                result.append("Hash que se generaría para '").append(password).append("': ").append(newHash).append("\n");
            }
            
        } catch (Exception e) {
            result.append("Error: ").append(e.getMessage()).append("\n");
        }
        
        return result.toString();
    }

    @GetMapping("/ver-menus")
    public String verMenus() {
        StringBuilder result = new StringBuilder();
        result.append("=== TODOS LOS MENÚS ===\n\n");
        
        List<Menu> todos = menuRepository.findAll();
        for (Menu menu : todos) {
            result.append("ID=").append(menu.getId())
                  .append(" | Nombre=").append(menu.getNombre())
                  .append(" | Ruta=").append(menu.getRuta())
                  .append(" | Activo=").append(menu.getActivo())
                  .append(" | PadreID=").append(menu.getPadre() != null ? menu.getPadre().getId() : "null")
                  .append("\n");
        }
        
        result.append("\n=== MENÚS RAÍZ (padre_id IS NULL) ===\n\n");
        List<Menu> raiz = menuRepository.findByPadreIsNull();
        for (Menu menu : raiz) {
            result.append("ID=").append(menu.getId())
                  .append(" | Nombre=").append(menu.getNombre())
                  .append(" | Ruta=").append(menu.getRuta())
                  .append("\n");
        }
        
        return result.toString();
    }

    @PostMapping("/fix-menu-rutas")
    public String fixMenuRutas() {
        StringBuilder result = new StringBuilder();
        List<Menu> todos = menuRepository.findAll();

        for (Menu menu : todos) {
            String nombre = menu.getNombre();
            String rutaAnterior = menu.getRuta();
            boolean cambiado = false;

            if ("Dashboard".equals(nombre)) {
                menu.setRuta("/admin");
                cambiado = true;
            } else if ("Pagos".equals(nombre)) {
                menu.setRuta("/admin/pagos-facturas");
                cambiado = true;
            } else if ("Facturas".equals(nombre)) {
                menu.setRuta("/admin/pagos-facturas");
                cambiado = true;
            } else if ("Menús".equals(nombre)) {
                menu.setRuta("/admin/menu");
                cambiado = true;
            } else if ("/admin/sedes/listar".equals(menu.getRuta())) {
                menu.setRuta("/admin/sedes");
                cambiado = true;
            } else if ("/admin/sedes/crear".equals(menu.getRuta())) {
                menu.setRuta("/admin/sedes");
                cambiado = true;
            } else if ("/admin/zonas/listar".equals(menu.getRuta())) {
                menu.setRuta("/admin/zonas");
                cambiado = true;
            } else if ("/admin/zonas/crear".equals(menu.getRuta())) {
                menu.setRuta("/admin/zonas");
                cambiado = true;
            }

            if (cambiado) {
                menuRepository.save(menu);
                result.append("ID=").append(menu.getId())
                      .append(" | ").append(nombre)
                      .append(" | ").append(rutaAnterior)
                      .append(" -> ").append(menu.getRuta()).append("\n");
            }
        }

        if (result.length() == 0) {
            result.append("No se encontraron rutas para corregir.\n");
        }

        result.append("\n--- Menús actuales ---\n");
        for (Menu menu : todos) {
            result.append("ID=").append(menu.getId())
                  .append(" | ").append(menu.getNombre())
                  .append(" | ruta=").append(menu.getRuta())
                  .append(" | padre=").append(menu.getPadre() != null ? menu.getPadre().getId() : "null")
                  .append("\n");
        }

        return result.toString();
    }

    @PostMapping("/fix-menu-raiz")
    public String fixMenuRaiz() {
        StringBuilder result = new StringBuilder();

        // Verificar si ya existe el menú raíz "Admin"
        List<Menu> existentes = menuRepository.findAll().stream()
                .filter(m -> "/admin".equals(m.getRuta()) && m.getPadre() == null)
                .toList();

        Menu menuAdmin;
        if (existentes.isEmpty()) {
            menuAdmin = new Menu("Admin", "/admin", true);
            menuAdmin = menuRepository.save(menuAdmin);
            result.append("Menú raíz 'Admin' creado con id=").append(menuAdmin.getId()).append("\n");
        } else {
            menuAdmin = existentes.get(0);
            result.append("Menú raíz 'Admin' ya existe con id=").append(menuAdmin.getId()).append("\n");
        }

        // Hacer que todos los menús raíz actuales sean hijos de "Admin"
        List<Menu> menusRaiz = menuRepository.findByPadreIsNull();
        int actualizados = 0;
        for (Menu menu : menusRaiz) {
            if (!menu.getId().equals(menuAdmin.getId())) {
                menu.setPadre(menuAdmin);
                menuRepository.save(menu);
                actualizados++;
            }
        }
        result.append("Menús movidos como hijos de Admin: ").append(actualizados).append("\n");

        return result.toString();
    }
}
