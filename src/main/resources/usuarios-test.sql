-- ============================================
-- USUARIO DE PRUEBA PARA LOGIN
-- Ejecutar este script en pgAdmin sobre la BD parqueadero_db
-- ============================================

-- Verificar si ya existe un usuario con cliente_id=1
DO $$
BEGIN
    -- Si no existe usuario con cliente_id=1, crear uno nuevo
    IF NOT EXISTS (SELECT 1 FROM usuarios WHERE cliente_id = 1) THEN
        INSERT INTO usuarios (username, email, password, rol, cliente_id, activo, fecha_creacion, fecha_actualizacion)
        VALUES ('cliente1', 'cliente1@test.com', '$2a$10$dXJ3SW6G7P50lGmMQgel7u7.YQvN6rfyNf8vZ5yGjKqTs2pYxYvOm', 'CLIENTE', 1, true, NOW(), NOW());
    ELSE
        -- Si ya existe, actualizar la contraseña
        UPDATE usuarios 
        SET password = '$2a$10$dXJ3SW6G7P50lGmMQgel7u7.YQvN6rfyNf8vZ5yGjKqTs2pYxYvOm',
            username = 'cliente1',
            email = 'cliente1@test.com',
            rol = 'CLIENTE'
        WHERE cliente_id = 1;
    END IF;
END $$;

-- Crear usuario ADMIN (sin cliente asociado)
-- Username: admin
-- Password: admin123
INSERT INTO usuarios (username, email, password, rol, cliente_id, activo, fecha_creacion, fecha_actualizacion)
VALUES ('admin', 'admin@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN', NULL, true, NOW(), NOW())
ON CONFLICT (username) DO NOTHING;

-- Actualizar secuencia
SELECT setval('usuarios_id_seq', COALESCE((SELECT MAX(id) FROM usuarios), 1));
