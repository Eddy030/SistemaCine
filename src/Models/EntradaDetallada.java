package Models;

import java.util.Date;

public class EntradaDetallada {
    private int id;
    private String pelicula;
    private String cliente;
    private String precioCategoria;
    private String numeroFila;
    private int numeroAsiento;
    private Date fechaVenta;
    private String estado;

    // Constructor, getters y setters

    public EntradaDetallada() {
    }

    public EntradaDetallada(int id, String pelicula, String cliente, String precioCategoria, String numeroFila, int numeroAsiento, Date fechaVenta, String estado) {
        this.id = id;
        this.pelicula = pelicula;
        this.cliente = cliente;
        this.precioCategoria = precioCategoria;
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

    public String getPelicula() {
        return pelicula;
    }

    public void setPelicula(String pelicula) {
        this.pelicula = pelicula;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getPrecioCategoria() {
        return precioCategoria;
    }

    public void setPrecioCategoria(String precioCategoria) {
        this.precioCategoria = precioCategoria;
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
    
}