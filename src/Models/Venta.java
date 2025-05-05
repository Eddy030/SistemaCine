package Models;

import java.time.LocalDateTime;

public class Venta {

    private int id;
    private int empleadoId;
    private LocalDateTime fechaHora;
    private String tipoVenta;
    private String metodoPago;
    private double precioTotal;

    public Venta() {
    }

    public Venta(int id, int empleadoId, LocalDateTime fechaHora, String tipoVenta, String metodoPago, double precioTotal) {
        this.id = id;
        this.empleadoId = empleadoId;
        this.fechaHora = fechaHora;
        this.tipoVenta = tipoVenta;
        this.metodoPago = metodoPago;
        this.precioTotal = precioTotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }
}
