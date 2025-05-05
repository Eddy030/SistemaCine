package Models;

import java.util.Date;

public class EntradaDetallada {

    private int id;
    private String pelicula;
    private int clienteID;
    private String cliente;
    private int precioEntradaID;
    private String precioCategoria;
    private int funcionID;
    private String numeroFila;
    private int numeroAsiento;
    private Date fechaVenta;
    private String estado;

    public EntradaDetallada() {
    }

    public EntradaDetallada(int id, String pelicula, int clienteID, String cliente, int precioEntradaID, String precioCategoria, int funcionID, String numeroFila, int numeroAsiento, Date fechaVenta, String estado) {
        this.id = id;
        this.pelicula = pelicula;
        this.clienteID = clienteID;
        this.cliente = cliente;
        this.precioEntradaID = precioEntradaID;
        this.precioCategoria = precioCategoria;
        this.funcionID = funcionID;
        this.numeroFila = numeroFila;
        this.numeroAsiento = numeroAsiento;
        this.fechaVenta = fechaVenta;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPelicula() {
        return pelicula;
    }

    public void setPelicula(String pelicula) {
        this.pelicula = pelicula;
    }

    public int getClienteID() {
        return clienteID;
    }

    public void setClienteID(int clienteID) {
        this.clienteID = clienteID;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getPrecioEntradaID() {
        return precioEntradaID;
    }

    public void setPrecioEntradaID(int precioEntradaID) {
        this.precioEntradaID = precioEntradaID;
    }

    public String getPrecioCategoria() {
        return precioCategoria;
    }

    public void setPrecioCategoria(String precioCategoria) {
        this.precioCategoria = precioCategoria;
    }

    public int getFuncionID() {
        return funcionID;
    }

    public void setFuncionID(int funcionID) {
        this.funcionID = funcionID;
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
        return "EntradaDetallada{" + "id=" + id + ", pelicula=" + pelicula + ", clienteID=" + clienteID + ", cliente=" + cliente + ", precioCategoria=" + precioCategoria + ", funcionID=" + funcionID + ", numeroFila=" + numeroFila + ", numeroAsiento=" + numeroAsiento + ", fechaVenta=" + fechaVenta + ", estado=" + estado + '}';
    }
}
