-- ============================================
-- DATOS DE PRUEBA PARA PARQUEADERO
-- Ejecutar este script en pgAdmin sobre la BD parqueadero_db
-- ============================================

-- 1. Crear cliente (si no existe)
INSERT INTO clientes (id, identificacion, nombre, telefono, correo, direccion, activo, fecha_creacion, fecha_actualizacion)
VALUES (1, '1234567890', 'Juan Pérez', '3001234567', 'juan@test.com', 'Calle 123 #45-67', true, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- 2. Crear vehículos asociados al cliente_id=1
INSERT INTO vehiculos (id, placa, marca, modelo, color, tipo_vehiculo, cliente_id, activo, fecha_creacion, fecha_actualizacion)
VALUES (1, 'ABC123', 'Toyota', 'Corolla', 'Rojo', 'CARRO', 1, true, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO carros (id, numero_puertas)
VALUES (1, 4)
ON CONFLICT (id) DO NOTHING;

INSERT INTO vehiculos (id, placa, marca, modelo, color, tipo_vehiculo, cliente_id, activo, fecha_creacion, fecha_actualizacion)
VALUES (2, 'XYZ789', 'Yamaha', 'FZ', 'Negro', 'MOTO', 1, true, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO motos (id, cilindraje, tiene_maletero)
VALUES (2, 250, false)
ON CONFLICT (id) DO NOTHING;

-- 3. Crear sede
INSERT INTO sedes (id, nombre, direccion, ciudad, activo, fecha_creacion, fecha_actualizacion)
VALUES (1, 'Sede Principal', 'Av. Siempre Viva 742', 'Bogotá', true, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- 4. Crear zonas
INSERT INTO zonas (id, nombre, descripcion, tipo_zona, sede_id, activo, fecha_creacion, fecha_actualizacion)
VALUES (1, 'Zona Carros', 'Zona exclusiva para carros', 'CARROS', 1, true, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO zonas (id, nombre, descripcion, tipo_zona, sede_id, activo, fecha_creacion, fecha_actualizacion)
VALUES (2, 'Zona Motos', 'Zona exclusiva para motos', 'MOTOS', 1, true, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- 5. Crear espacios de parqueo
INSERT INTO espacios_parqueo (id, codigo, ubicacion, estado, tipo_vehiculo_permitido, zona_id, activo, fecha_creacion, fecha_actualizacion)
VALUES (1, 'A-01', 'Piso 1', 'DISPONIBLE', 'CARRO', 1, true, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO espacios_parqueo (id, codigo, ubicacion, estado, tipo_vehiculo_permitido, zona_id, activo, fecha_creacion, fecha_actualizacion)
VALUES (2, 'A-02', 'Piso 1', 'DISPONIBLE', 'CARRO', 1, true, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO espacios_parqueo (id, codigo, ubicacion, estado, tipo_vehiculo_permitido, zona_id, activo, fecha_creacion, fecha_actualizacion)
VALUES (3, 'B-01', 'Piso 2', 'DISPONIBLE', 'MOTO', 2, true, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO espacios_parqueo (id, codigo, ubicacion, estado, tipo_vehiculo_permitido, zona_id, activo, fecha_creacion, fecha_actualizacion)
VALUES (4, 'B-02', 'Piso 2', 'DISPONIBLE', 'MOTO', 2, true, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- 6. Crear tarifas
INSERT INTO tarifas (id, tipo_vehiculo, valor_hora, valor_dia, valor_fraccion, activo, fecha_creacion, fecha_actualizacion)
VALUES (1, 'CARRO', 5000.0, 40000.0, 1500.0, true, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO tarifas (id, tipo_vehiculo, valor_hora, valor_dia, valor_fraccion, activo, fecha_creacion, fecha_actualizacion)
VALUES (2, 'MOTO', 3000.0, 25000.0, 1000.0, true, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO tarifas (id, tipo_vehiculo, valor_hora, valor_dia, valor_fraccion, activo, fecha_creacion, fecha_actualizacion)
VALUES (3, 'CAMION', 8000.0, 60000.0, 2500.0, true, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- 7. Crear tickets de ejemplo
INSERT INTO tickets (id, codigo_ticket, hora_entrada, hora_salida, estado, total, vehiculo_id, espacio_id, activo, fecha_creacion, fecha_actualizacion)
VALUES (1, 'TK-001', NOW() - INTERVAL '2 hours', NOW(), 'CERRADO', 10000.0, 1, 1, true, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO tickets (id, codigo_ticket, hora_entrada, hora_salida, estado, total, vehiculo_id, espacio_id, activo, fecha_creacion, fecha_actualizacion)
VALUES (2, 'TK-002', NOW() - INTERVAL '1 hour', NULL, 'ABIERTO', 0.0, 2, 3, true, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- 8. Actualizar secuencias (importante para que los IDs autoincrementales funcionen)
SELECT setval('clientes_id_seq', COALESCE((SELECT MAX(id) FROM clientes), 1));
SELECT setval('vehiculos_id_seq', COALESCE((SELECT MAX(id) FROM vehiculos), 1));
SELECT setval('sedes_id_seq', COALESCE((SELECT MAX(id) FROM sedes), 1));
SELECT setval('zonas_id_seq', COALESCE((SELECT MAX(id) FROM zonas), 1));
SELECT setval('espacios_parqueo_id_seq', COALESCE((SELECT MAX(id) FROM espacios_parqueo), 1));
SELECT setval('tarifas_id_seq', COALESCE((SELECT MAX(id) FROM tarifas), 1));
SELECT setval('tickets_id_seq', COALESCE((SELECT MAX(id) FROM tickets), 1));
