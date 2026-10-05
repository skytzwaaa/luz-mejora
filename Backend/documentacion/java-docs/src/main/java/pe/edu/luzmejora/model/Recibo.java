package pe.edu.luzmejora.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import pe.edu.luzmejora.enums.EstadoRecibo;

/**
 * Recibo mensual de consumo de energía eléctrica administrado por Luz Mejora
 * (modelo TO-BE).
 *
 * <p>Reproduce el objeto que la función {@code generarRecibos()} del
 * prototipo web coloca en el arreglo {@code recibos[]}: identificador,
 * número de recibo, periodo, mes, año, fechas de emisión y vencimiento,
 * monto, estado, consumo en kWh y lecturas del medidor. El código de pago
 * ({@code paymentCode}) solo existe cuando el recibo fue cancelado con el
 * flujo simulado.</p>
 *
 * <p>Los valores del prototipo actual son demostrativos y no corresponden
 * a información productiva de Luz del Sur.</p>
 */
public class Recibo {

    private int id;
    private String numeroRecibo;
    private String etiqueta;
    private String periodo;
    private int mes;
    private int anio;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
    private BigDecimal monto;
    private EstadoRecibo estado;
    private double consumoKwh;
    private double lecturaAnterior;
    private double lecturaActual;
    private String codigoPago;

    /** Crea un recibo vacío para construcción por etapas. */
    public Recibo() {
    }

    /**
     * Crea un recibo con sus datos esenciales de facturación demostrativa.
     *
     * @param id correlativo dentro del historial (0 = más reciente)
     * @param numeroRecibo código visible del recibo, por ejemplo “R-2026-09-2450”
     * @param mes mes calendario de 1 a 12
     * @param anio año del periodo facturado
     * @param monto importe simulado del recibo
     * @param estado estado inicial del recibo
     * @param consumoKwh consumo mensual en kilovatios hora
     */
    public Recibo(int id, String numeroRecibo, int mes, int anio,
                  BigDecimal monto, EstadoRecibo estado, double consumoKwh) {
        this.id = id;
        this.numeroRecibo = numeroRecibo;
        this.mes = mes;
        this.anio = anio;
        this.monto = monto;
        this.estado = estado;
        this.consumoKwh = consumoKwh;
    }

    /**
     * Indica si el recibo mantiene deuda pendiente en el prototipo, es
     * decir, si su estado es distinto de pagado.
     *
     * @return {@code true} cuando el recibo está pendiente o vencido
     */
    public boolean tieneDeudaPendiente() {
        return estado != EstadoRecibo.PAGADO;
    }

    /**
     * Indica si existe constancia de pago disponible, lo que en el
     * prototipo exige estado pagado y código de operación registrado.
     *
     * @return {@code true} si puede descargarse la constancia demostrativa
     */
    public boolean tieneConstanciaDisponible() {
        return estado == EstadoRecibo.PAGADO && codigoPago != null && !codigoPago.isBlank();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroRecibo() {
        return numeroRecibo;
    }

    public void setNumeroRecibo(String numeroRecibo) {
        this.numeroRecibo = numeroRecibo;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public EstadoRecibo getEstado() {
        return estado;
    }

    public void setEstado(EstadoRecibo estado) {
        this.estado = estado;
    }

    public double getConsumoKwh() {
        return consumoKwh;
    }

    public void setConsumoKwh(double consumoKwh) {
        this.consumoKwh = consumoKwh;
    }

    public double getLecturaAnterior() {
        return lecturaAnterior;
    }

    public void setLecturaAnterior(double lecturaAnterior) {
        this.lecturaAnterior = lecturaAnterior;
    }

    public double getLecturaActual() {
        return lecturaActual;
    }

    public void setLecturaActual(double lecturaActual) {
        this.lecturaActual = lecturaActual;
    }

    public String getCodigoPago() {
        return codigoPago;
    }

    public void setCodigoPago(String codigoPago) {
        this.codigoPago = codigoPago;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }
        if (!(objeto instanceof Recibo)) {
            return false;
        }
        Recibo otro = (Recibo) objeto;
        return Objects.equals(numeroRecibo, otro.numeroRecibo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroRecibo);
    }

    @Override
    public String toString() {
        return "Recibo{numeroRecibo='" + numeroRecibo + "', estado=" + estado
                + ", monto=" + monto + ", consumoKwh=" + consumoKwh + '}';
    }
}
