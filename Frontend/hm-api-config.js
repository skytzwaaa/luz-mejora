/* Luz Mejora — configuración pública del backend.
 *
 * Despliegue normal (Docker/Nginx): dejar '' → se usan URLs relativas
 * mismo-origen (/api/..., /socket.io/... vía Nginx). No tocar.
 *
 * Solo si el backend está en otro dominio HTTPS:
 *   window.HM_API_BASE = 'https://api.TU-DOMINIO';
 *
 * - NO poner secretos aquí (sin JWT, sin POSTGRES_PASSWORD, sin .env).
 * - Afecta a REST (apiUrl/apiFetch) y a Socket.IO (socketBaseUrl) a la vez.
 */
window.HM_API_BASE = '';
