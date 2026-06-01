-- ============================================
-- SCRIPT PARA CORREGIR RUTAS DEL MENÚ
-- Ejecutar este script en PostgreSQL
-- ============================================

-- Ver estado actual de los menús
SELECT id, nombre, ruta, activo, padre_id 
FROM menu 
ORDER BY padre_id NULLS FIRST, id;

-- Corregir Dashboard
UPDATE menu SET ruta = '/admin' 
WHERE nombre = 'Dashboard' AND padre_id IS NULL;

-- Corregir Pagos y Facturas (ambos van a la misma página)
UPDATE menu SET ruta = '/admin/pagos-facturas' 
WHERE nombre = 'Pagos' AND padre_id IS NULL;

UPDATE menu SET ruta = '/admin/pagos-facturas' 
WHERE nombre = 'Facturas' AND padre_id IS NULL;

-- Corregir Menús (es "menu" no "menus")
UPDATE menu SET ruta = '/admin/menu' 
WHERE nombre = 'Menús' AND padre_id IS NULL;

-- Corregir submenús de Sedes (todos van a /admin/sedes)
UPDATE menu SET ruta = '/admin/sedes' 
WHERE ruta = '/admin/sedes/listar';

UPDATE menu SET ruta = '/admin/sedes' 
WHERE ruta = '/admin/sedes/crear';

-- Corregir submenús de Zonas (todos van a /admin/zonas)
UPDATE menu SET ruta = '/admin/zonas' 
WHERE ruta = '/admin/zonas/listar';

UPDATE menu SET ruta = '/admin/zonas' 
WHERE ruta = '/admin/zonas/crear';

-- Verificar rutas corregidas
SELECT id, nombre, ruta, activo, padre_id 
FROM menu 
ORDER BY padre_id NULLS FIRST, id;

-- Resumen de cambios
SELECT 
    COUNT(*) as total_menus,
    COUNT(CASE WHEN ruta IS NOT NULL THEN 1 END) as con_ruta,
    COUNT(CASE WHEN ruta IS NULL THEN 1 END) as sin_ruta
FROM menu;
