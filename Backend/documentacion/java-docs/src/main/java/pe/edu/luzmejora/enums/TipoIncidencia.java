package pe.edu.luzmejora.enums;

/**
 * Categorías de incidencia disponibles en el formulario
 * “Reportar incidencia” de Luz Mejora (modelo TO-BE).
 *
 * <p>Los valores reproducen exactamente las opciones del elemento
 * {@code select#inc-tipo} del prototipo web. No se agregan categorías
 * adicionales.</p>
 */
public enum TipoIncidencia {

    /** Cable de energía caído o expuesto (riesgo eléctrico). */
    CABLE_CAIDO("Cable caído"),

    /** Interrupción o ausencia del suministro. */
    FALTA_LUZ("Falta de luz"),

    /** Tensión por debajo de lo esperado o fluctuaciones. */
    BAJA_TENSION("Baja tensión"),

    /** Poste dañado o en mal estado. */
    POSTE_DANADO("Poste dañado"),

    /** Inconvenientes con el medidor del suministro. */
    PROBLEMA_MEDIDOR("Problema con medidor"),

    /** Falla del alumbrado público de la zona. */
    ALUMBRADO_PUBLICO("Alumbrado público"),

    /** Reclamo relacionado con la facturación del recibo. */
    FACTURACION("Facturación"),

    /** Cualquier otro caso no contemplado. */
    OTRO("Otro");

    private final String etiquetaWeb;

    TipoIncidencia(String etiquetaWeb) {
        this.etiquetaWeb = etiquetaWeb;
    }

    /**
     * Devuelve el texto tal como aparece en el formulario web, útil para
     * trazar el modelo Java con la interfaz del prototipo.
     *
     * @return etiqueta visible en el {@code select} de incidencias
     */
    public String getEtiquetaWeb() {
        return etiquetaWeb;
    }
}
