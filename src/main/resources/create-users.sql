-- ============================================
-- CREAR USUARIOS DE PRUEBA
-- Ejecutar este script en pgAdmin sobre la BD parqueadero_db
-- ============================================

-- Usuario ADMIN (password: admin123)
INSERT INTO usuarios (username, email, password, rol, cliente_id, activo, fecha_creacion, fecha_actualizacion)
SELECT 'admin', 'admin@test.com', '$2a$10$rOXBjzj8y1nXqJp1w.qKqOdYmHnvGd6YwGvGvHqZ9vQX0zJQJvOeS', 'ADMIN', NULL, true, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE username = 'admin');

-- Usuario CLIENTE1 asociado al cliente_id=1 (password: 1234)
INSERT INTO usuarios (username, email, password, rol, cliente_id, activo, fecha_creacion, fecha_actualizacion)
SELECT 'cliente1', 'cliente1@test.com', '$2a$10$dXJ3SW6G7P50lGmMQgel7u7.YQvN6rfyNf8vZ5yGjKqTs2pYxYvOm', 'CLIENTE', 1, true, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE username = 'cliente1');

-- Usuario CLIENTE2 asociado al cliente_id=2 (password: 1234)
INSERT INTO usuarios (username, email, password, rol, cliente_id, activo, fecha_creacion, fecha_actualizacion)
SELECT 'cliente2', 'cliente2@test.com', '$2a$10$dXJ3SW6G7P50lGmMQgel7u7.YQvN6rfyNf8vZ5yGjKqTs2pYxYvOm', 'CLIENTE', 2, true, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE username = 'cliente2');

-- Actualizar secuencia
SELECT setval('usuarios_id_seq', COALESCE((SELECT MAX(id) FROM usuarios), 1));
