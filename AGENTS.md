# AGENTS.md — Luz Mejora

Prototipo académico (Universidad Norbert Wiener · Software I · 2026-II): propuesta de mejora de la
experiencia digital de **Luz del Sur** (distribuidora eléctrica de Lima). El usuario entra con su
número de suministro y consulta recibos, consumo en kWh, pagos simulados, incidencias, cortes de
servicio, comprobantes PDF, tutorial guiado y el asistente **LuzBot**.

## Idioma
Todo el texto visible, comentarios y documentación van **en español**. No traducir identificadores existentes.

## Estructura
- `Frontend/index.html` — SPA de un solo archivo (HTML + CSS + JS vanilla). Es la fuente de toda la interfaz. Servida por Nginx (`Frontend/nginx/default.conf`).
- `Frontend/hm-api-config.js` — `window.HM_API_BASE` (vacío = mismo origen).
- `Backend/server.js` — API REST (Node 22, Express 5, `pg`, JWT, Socket.IO). Un solo archivo.
- `Base-de-Datos/init.sql` — esquema PostgreSQL inicial; `migrations/` para bases ya creadas.
- `Backend/documentacion/java-docs/` — modelo TO-BE en Java (solo documentación UML/Javadoc; no es backend).
- `scripts/` — `seed-demo.sh` (datos demo) y `backup-db.sh`.
- `compose.yaml` (desarrollo) y `compose.prod.yaml` (producción, ver `DEPLOY.md`).

## Cómo ejecutar
```bash
cp .env.example .env
docker compose up --build        # http://localhost:8080
./scripts/seed-demo.sh           # cliente 123456789 / Cliente12345 · admin 900000001 / Admin12345
```

## Convenciones del dominio
- Unidad de consumo: **kWh** (columna `recibos.consumo_kwh`, campo JSON `consumo_kwh`, variable JS `consumoKwh`).
- Suministro: **9 dígitos** (validado en backend y frontend).
- Desglose referencial de factura: energía 60 % · distribución y alumbrado público 22 % · IGV 18 %.
- Umbrales de consumo en `Frontend/index.html`: > 220 kWh "Sobre el promedio", > 300 kWh "Alto consumo".
- Tipos de incidencia: Cable caído, Falta de luz, Baja tensión, Poste dañado, Problema con medidor, Alumbrado público, Facturación, Otro.
- Intenciones de LuzBot: ver `LUZBOT_INTENTS` en `Frontend/index.html` (asistente local por reglas, sin IA externa).

## Reglas al modificar
- Cambios de esquema: editar `init.sql` **y** añadir un archivo nuevo en `Base-de-Datos/migrations/` (numeración correlativa, idempotente).
- Si cambia un nombre de campo de la API, actualizar backend, frontend, `init.sql` y los docs Java a la vez.
- Es una demo: los pagos son simulados; no añadir cobros reales ni secretos al repo (`.env` está en `.gitignore`).
- No hay suite de tests; antes de dar algo por hecho, levantar con Docker y probar login → recibos → detalle → PDF → LuzBot.
