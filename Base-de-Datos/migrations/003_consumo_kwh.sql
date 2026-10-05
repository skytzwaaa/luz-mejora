-- ==========================================
-- Luz Mejora · Migración 003
-- Consumo en kWh (antes m³ en la versión de agua)
-- Solo para bases de datos YA creadas con el esquema anterior.
-- En una base nueva (init.sql) la columna ya se llama consumo_kwh.
-- ==========================================
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'recibos' AND column_name = 'consumo_m3'
    ) THEN
        ALTER TABLE recibos RENAME COLUMN consumo_m3 TO consumo_kwh;
    END IF;
END $$;
