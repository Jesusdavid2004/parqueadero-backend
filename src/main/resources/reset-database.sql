-- ============================================
-- LIMPIAR Y RELLENAR BASE DE DATOS
-- Ejecutar este script en pgAdmin sobre la BD parqueadero_db
-- ============================================

-- 1. ELIMINAR TODOS LOS DATOS (en orden correcto por foreign keys)
DELETE FROM facturas;
DELETE FROM pagos;
DELETE FROM reservas;
DELETE FROM tickets;
DELETE FROM usuarios;
DELETE FROM camiones;
DELETE FROM carros;
DELETE FROM motos;
DELETE FROM vehiculos;
DELETE FROM espacios_parqueo;
DELETE FROM zonas;
DELETE FROM sedes;
DELETE FROM tarifas;
DELETE FROM clientes;
DELETE FROM menu;

-- 2. REINICIAR SECUENCIAS
ALTER SEQUENCE facturas_id_seq RESTART WITH 1;
ALTER SEQUENCE pagos_id_seq RESTART WITH 1;
ALTER SEQUENCE reservas_id_seq RESTART WITH 1;
ALTER SEQUENCE tickets_id_seq RESTART WITH 1;
ALTER SEQUENCE usuarios_id_seq RESTART WITH 1;
ALTER SEQUENCE vehiculos_id_seq RESTART WITH 1;
ALTER SEQUENCE espacios_parqueo_id_seq RESTART WITH 1;
ALTER SEQUENCE zonas_id_seq RESTART WITH 1;
ALTER SEQUENCE sedes_id_seq RESTART WITH 1;
ALTER SEQUENCE tarifas_id_seq RESTART WITH 1;
ALTER SEQUENCE clientes_id_seq RESTART WITH 1;
ALTER SEQUENCE menu_id_seq RESTART WITH 1;

-- 3. INSERTAR DATOS DE PRUEBA

-- Clientes
INSERT INTO clientes (id, identificacion, nombre, telefono, correo, direccion, activo, fecha_creacion, fecha_actualizacion) VALUES
(1, '1234567890', 'Juan Pérez', '3001234567', 'juan@test.com', 'Calle 123 #45-67', true, NOW(), NOW()),
(2, '0987654321', 'María López', '3109876543', 'maria@test.com', 'Carrera 10 #20-30', true, NOW(), NOW()),
(3, '1122334455', 'Carlos Rodríguez', '3201122334', 'carlos@test.com', 'Avenida 5 #15-25', true, NOW(), NOW());

-- Vehículos
INSERT INTO vehiculos (id, placa, marca, modelo, color, tipo_vehiculo, cliente_id, activo, fecha_creacion, fecha_actualizacion) VALUES
(1, 'ABC123', 'Toyota', 'Corolla', 'Rojo', 'CARRO', 1, true, NOW(), NOW()),
(2, 'XYZ789', 'Yamaha', 'FZ', 'Negro', 'MOTO', 1, true, NOW(), NOW()),
(3, 'DEF456', 'Mazda', '3', 'Azul', 'CARRO', 2, true, NOW(), NOW()),
(4, 'GHI789', 'Honda', 'CBR', 'Rojo', 'MOTO', 2, true, NOW(), NOW()),
(5, 'JKL012', 'Chevrolet', 'Silverado', 'Blanco', 'CAMION', 3, true, NOW(), NOW());

-- Subtipos de vehículos
INSERT INTO carros (id, numero_puertas) VALUES (1, 4), (3, 4);
INSERT INTO motos (id, cilindraje, tiene_maletero) VALUES (2, 250, false), (4, 600, true);
INSERT INTO camiones (id, numero_ejes, capacidad_carga) VALUES (5, 2, 3500.0);

-- Sedes
INSERT INTO sedes (id, nombre, direccion, ciudad, activo, fecha_creacion, fecha_actualizacion) VALUES
(1, 'Sede Principal', 'Av. Siempre Viva 742', 'Bogotá', true, NOW(), NOW()),
(2, 'Sede Norte', 'Calle 100 #15-20', 'Bogotá', true, NOW(), NOW());

-- Zonas
INSERT INTO zonas (id, nombre, descripcion, tipo_zona, sede_id, activo, fecha_creacion, fecha_actualizacion) VALUES
(1, 'Zona Carros A', 'Zona exclusiva para carros - Piso 1', 'CARROS', 1, true, NOW(), NOW()),
(2, 'Zona Motos A', 'Zona exclusiva para motos - Piso 1', 'MOTOS', 1, true, NOW(), NOW()),
(3, 'Zona Mixta B', 'Zona mixta - Piso 2', 'MIXTA', 1, true, NOW(), NOW()),
(4, 'Zona Carros Norte', 'Zona carros sede norte', 'CARROS', 2, true, NOW(), NOW());

-- Espacios de parqueo
INSERT INTO espacios_parqueo (id, codigo, ubicacion, estado, tipo_vehiculo_permitido, zona_id, activo, fecha_creacion, fecha_actualizacion) VALUES
(1, 'A-01', 'Piso 1 - Sección A', 'DISPONIBLE', 'CARRO', 1, true, NOW(), NOW()),
(2, 'A-02', 'Piso 1 - Sección A', 'DISPONIBLE', 'CARRO', 1, true, NOW(), NOW()),
(3, 'A-03', 'Piso 1 - Sección A', 'DISPONIBLE', 'CARRO', 1, true, NOW(), NOW()),
(4, 'B-01', 'Piso 1 - Sección B', 'DISPONIBLE', 'MOTO', 2, true, NOW(), NOW()),
(5, 'B-02', 'Piso 1 - Sección B', 'DISPONIBLE', 'MOTO', 2, true, NOW(), NOW()),
(6, 'B-03', 'Piso 1 - Sección B', 'DISPONIBLE', 'MOTO', 2, true, NOW(), NOW()),
(7, 'C-01', 'Piso 2 - Sección C', 'DISPONIBLE', 'CARRO', 3, true, NOW(), NOW()),
(8, 'C-02', 'Piso 2 - Sección C', 'DISPONIBLE', 'MOTO', 3, true, NOW(), NOW()),
(9, 'D-01', 'Sede Norte - Sección D', 'DISPONIBLE', 'CARRO', 4, true, NOW(), NOW()),
(10, 'D-02', 'Sede Norte - Sección D', 'DISPONIBLE', 'CARRO', 4, true, NOW(), NOW());

-- Tarifas
INSERT INTO tarifas (id, tipo_vehiculo, valor_hora, valor_dia, valor_fraccion, activo, fecha_creacion, fecha_actualizacion) VALUES
(1, 'CARRO', 5000.0, 40000.0, 1500.0, true, NOW(), NOW()),
(2, 'MOTO', 3000.0, 25000.0, 1000.0, true, NOW(), NOW()),
(3, 'CAMION', 8000.0, 60000.0, 2500.0, true, NOW(), NOW());

-- Tickets
INSERT INTO tickets (id, codigo_ticket, hora_entrada, hora_salida, estado, total, vehiculo_id, espacio_id, activo, fecha_creacion, fecha_actualizacion) VALUES
(1, 'TK-001', NOW() - INTERVAL '2 hours', NOW(), 'CERRADO', 10000.0, 1, 1, true, NOW(), NOW()),
(2, 'TK-002', NOW() - INTERVAL '1 hour', NULL, 'ABIERTO', 0.0, 2, 4, true, NOW(), NOW()),
(3, 'TK-003', NOW() - INTERVAL '3 hours', NOW() - INTERVAL '1 hour', 'CERRADO', 10000.0, 3, 2, true, NOW(), NOW()),
(4, 'TK-004', NOW() - INTERVAL '30 minutes', NULL, 'ABIERTO', 0.0, 4, 5, true, NOW(), NOW());

-- Actualizar espacios ocupados
UPDATE espacios_parqueo SET estado = 'OCUPADO' WHERE id IN (4, 5);

-- Pagos
INSERT INTO pagos (id, monto, metodo_pago, estado, referencia, fecha, ticket_id, activo, fecha_creacion, fecha_actualizacion) VALUES
(1, 10000.0, 'EFECTIVO', 'APROBADO', 'PAY-001', NOW(), 1, true, NOW(), NOW()),
(2, 10000.0, 'TARJETA', 'APROBADO', 'PAY-002', NOW() - INTERVAL '1 hour', 3, true, NOW(), NOW());

-- Facturas
INSERT INTO facturas (id, numero_factura, subtotal, impuesto, total, pago_id, activo, fecha_creacion, fecha_actualizacion) VALUES
(1, 'FAC-000001', 8403.36, 1596.64, 10000.0, 1, true, NOW(), NOW()),
(2, 'FAC-000002', 8403.36, 1596.64, 10000.0, 2, true, NOW() - INTERVAL '1 hour', NOW());

-- Menús
INSERT INTO menu (id, nombre, ruta, activo, padre_id) VALUES
(1, 'Dashboard', '/admin/dashboard', true, NULL),
(2, 'Clientes', '/admin/clientes', true, NULL),
(3, 'Vehículos', '/admin/vehiculos', true, NULL),
(4, 'Sedes', '/admin/sedes', true, NULL),
(5, 'Zonas', '/admin/zonas', true, NULL),
(6, 'Espacios', '/admin/espacios', true, NULL),
(7, 'Tarifas', '/admin/tarifas', true, NULL),
(8, 'Tickets', '/admin/tickets', true, NULL),
(9, 'Pagos', '/admin/pagos', true, NULL),
(10, 'Facturas', '/admin/facturas', true, NULL),
(11, 'Empleados', '/admin/empleados', true, NULL),
(12, 'Menús', '/admin/menus', true, NULL),
(13, 'Gestión de Sedes', NULL, true, 4),
(14, 'Listar Sedes', '/admin/sedes/listar', true, 4),
(15, 'Crear Sede', '/admin/sedes/crear', true, 4),
(16, 'Gestión de Zonas', NULL, true, 5),
(17, 'Listar Zonas', '/admin/zonas/listar', true, 5),
(18, 'Crear Zona', '/admin/zonas/crear', true, 5);

-- 4. ACTUALIZAR SECUENCIAS
SELECT setval('clientes_id_seq', (SELECT MAX(id) FROM clientes));
SELECT setval('vehiculos_id_seq', (SELECT MAX(id) FROM vehiculos));
SELECT setval('sedes_id_seq', (SELECT MAX(id) FROM sedes));
SELECT setval('zonas_id_seq', (SELECT MAX(id) FROM zonas));
SELECT setval('espacios_parqueo_id_seq', (SELECT MAX(id) FROM espacios_parqueo));
SELECT setval('tarifas_id_seq', (SELECT MAX(id) FROM tarifas));
SELECT setval('tickets_id_seq', (SELECT MAX(id) FROM tickets));
SELECT setval('pagos_id_seq', (SELECT MAX(id) FROM pagos));
SELECT setval('facturas_id_seq', (SELECT MAX(id) FROM facturas));
SELECT setval('menu_id_seq', (SELECT MAX(id) FROM menu));
