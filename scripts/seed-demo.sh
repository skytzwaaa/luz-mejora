#!/usr/bin/env bash
# ============================================================
# Luz Mejora · Datos de demostración
# Uso (desde la raíz del repo, con `docker compose up` en marcha):
#   ./scripts/seed-demo.sh
# Crea: 1 administrador, 1 cliente, 6 recibos en kWh y 1 corte.
# Solo para demos: las claves de abajo son públicas.
# ============================================================
set -euo pipefail
cd "$(dirname "$0")/.."

BASE_URL="${BASE_URL:-http://localhost:8080}"
ADMIN_SUM="900000001";  ADMIN_PASS="Admin12345"
CLI_SUM="123456789";    CLI_PASS="Cliente12345"

if [ -z "${PROMOTE_CMD:-}" ]; then
  [ -f .env ] || { echo "ERROR: falta .env (cp .env.example .env)" >&2; exit 1; }
  set -a; . ./.env; set +a
  PROMOTE_CMD="docker compose exec -T db psql -U ${POSTGRES_USER} -d ${POSTGRES_DB} -qc"
fi

json() { python3 -c "import sys,json;print(json.load(sys.stdin).get('$1',''))"; }
post() { curl -s -X POST "$BASE_URL/api$1" -H 'Content-Type: application/json' ${3:+-H "Authorization: Bearer $3"} -d "$2"; }

echo "→ Creando cuentas…"
post /auth/register "{\"correo\":\"admin@luzmejora.demo\",\"numero_suministro\":\"$ADMIN_SUM\",\"password\":\"$ADMIN_PASS\"}" >/dev/null
post /auth/register "{\"correo\":\"cliente@luzmejora.demo\",\"numero_suministro\":\"$CLI_SUM\",\"password\":\"$CLI_PASS\"}" >/dev/null

echo "→ Asignando rol admin…"
$PROMOTE_CMD "UPDATE usuarios SET rol='admin' WHERE correo='admin@luzmejora.demo'"

TOKEN=$(post /auth/login "{\"numero_suministro\":\"$ADMIN_SUM\",\"password\":\"$ADMIN_PASS\"}" | json token)
[ -n "$TOKEN" ] || { echo "ERROR: no se obtuvo token de admin" >&2; exit 1; }

echo "→ Cargando recibos (periodo|emisión|vencimiento|monto S/|kWh)…"
for r in "Mayo 2026|2026-05-05|2026-05-20|118.40|140" \
         "Junio 2026|2026-06-05|2026-06-20|142.50|168" \
         "Julio 2026|2026-07-05|2026-07-20|160.30|190" \
         "Agosto 2026|2026-08-05|2026-08-20|171.90|205" \
         "Setiembre 2026|2026-09-05|2026-09-20|233.40|320" \
         "Octubre 2026|2026-10-05|2026-10-20|196.10|250"; do
  IFS='|' read -r per em ve mo kwh <<<"$r"
  post /admin/recibos "{\"numero_suministro\":\"$CLI_SUM\",\"periodo\":\"$per\",\"fecha_emision\":\"$em\",\"fecha_vencimiento\":\"$ve\",\"monto\":$mo,\"consumo_kwh\":$kwh}" "$TOKEN" >/dev/null
done

echo "→ Programando un corte de ejemplo…"
if ! curl -s "$BASE_URL/api/cortes" | grep -q "Monterrico"; then
INI=$(TZ=America/Lima date -d "+2 days 09:00" +%Y-%m-%dT%H:%M:00-05:00); FIN=$(TZ=America/Lima date -d "+2 days 15:00" +%Y-%m-%dT%H:%M:00-05:00)
post /admin/cortes "{\"alcance\":\"Zona\",\"distrito\":\"Santiago de Surco\",\"zona\":\"Monterrico\",\"motivo\":\"Mantenimiento programado de la red de media tensión\",\"fecha_inicio\":\"$INI\",\"fecha_fin\":\"$FIN\"}" "$TOKEN" >/dev/null
fi

echo
echo "Listo. Entra en $BASE_URL con:"
echo "  Cliente: suministro $CLI_SUM · clave $CLI_PASS"
echo "  Admin:   suministro $ADMIN_SUM · clave $ADMIN_PASS"
