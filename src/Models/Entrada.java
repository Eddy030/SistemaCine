package Models;

import java.util.Date;

public class Entrada {

    private int id;
    private int funcionID;
    private int clienteID;
    private int precioEntradaID;
    private String numeroFila;
    private int numeroAsiento;
    private Date fechaVenta;
    private String estado;

    public Entrada() {
    }

    public Entrada(int id, int funcionID, int clienteID, int precioEntradaID,
            String numeroFila, int numeroAsiento, Date fechaVenta, String estado) {
        this.id = id;
        this.funcionID = funcionID;
        this.clienteID = clienteID;
        this.precioEntradaID = precioEntradaID;
        this.numeroFila = numeroFila;
        this.numeroAsiento = numeroAsiento;
        this.fechaVenta = fechaVenta;
        this.estado = estado;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFuncionID() {
        return funcionID;
    }

    public void setFuncionID(int funcionID) {
        this.funcionID = funcionID;
    }

    public int getClienteID() {
        return clienteID;
    }

    public void setClienteID(int clienteID) {
        this.clienteID = clienteID;
    }

    public int getPrecioEntradaID() {
        return precioEntradaID;
    }

    public void setPrecioEntradaID(int precioEntradaID) {
        this.precioEntradaID = precioEntradaID;
    }

    public String getNumeroFila() {
        return numeroFila;
    }

    public void setNumeroFila(String numeroFila) {
        this.numeroFila = numeroFila;
    }

    public int getNumeroAsiento() {
        return numeroAsiento;
    }

    public void setNumeroAsiento(int numeroAsiento) {
        this.numeroAsiento = numeroAsiento;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Entrada{"
                + "id=" + id
                + ", funcionID=" + funcionID
                + ", clienteID=" + clienteID
                + ", precioEntradaID=" + precioEntradaID
                + ", numeroFila='" + numeroFila + '\''
                + ", numeroAsiento=" + numeroAsiento
                + ", fechaVenta=" + fechaVenta
                + ", estado='" + estado + '\''
                + '}';
    }
}
