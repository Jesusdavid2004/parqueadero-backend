-- ============================================
-- CREAR MENÚ RAÍZ /admin
-- El frontend busca un nodo con ruta "/admin" para renderizar sus hijos
-- Ejecutar este script en pgAdmin sobre la BD parqueadero_db
-- ============================================

-- 1. Crear menú raíz "Admin" con ruta /admin
INSERT INTO menu (nombre, ruta, activo, padre_id)
VALUES ('Admin', '/admin', true, NULL);

-- 2. Hacer que todos los menús raíz actuales sean hijos de "Admin"
UPDATE menu
SET padre_id = (SELECT id FROM menu WHERE nombre = 'Admin' AND ruta = '/admin' AND padre_id IS NULL)
WHERE padre_id IS NULL
  AND id != (SELECT id FROM menu WHERE nombre = 'Admin' AND ruta = '/admin' AND padre_id IS NULL);
