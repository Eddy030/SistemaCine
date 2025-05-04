package Models;

import java.time.LocalDateTime;

public class Funcion {
    private int id;
    private int peliculaId;
    private int salaId;
    private LocalDateTime fechaHora;
    private double precioBase;
    private String estado;
    private String formato;
    private String tituloPelicula; // Campo auxiliar para mostrar el título en la vista

    // Constructor vacío
    public Funcion() {}

    // Constructor completo
    public Funcion(int id, int peliculaId, int salaId, LocalDateTime fechaHora, 
                   double precioBase, String estado, String formato, String tituloPelicula) {
        this.id = id;
        this.peliculaId = peliculaId;
        this.salaId = salaId;
        this.fechaHora = fechaHora;
        this.precioBase = precioBase;
        this.estado = estado;
        this.formato = formato;
        this.tituloPelicula = tituloPelicula;
    }

    // Getters y Setters
    public int getId() {
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }

    public int getPeliculaId() { 
        return peliculaId; 
    }
    
    public void setPeliculaId(int peliculaId) { 
        this.peliculaId = peliculaId; 
    }

    public int getSalaId() { 
        return salaId; 
    }
    
    public void setSalaId(int salaId) { 
        this.salaId = salaId; 
    }

    public LocalDateTime getFechaHora() { 
        return fechaHora; 
    }
    
    public void setFechaHora(LocalDateTime fechaHora) { 
        this.fechaHora = fechaHora; 
    }

    public double getPrecioBase() { 
        return precioBase; 
    }
    
    public void setPrecioBase(double precioBase) { 
        this.precioBase = precioBase; 
    }

    public String getEstado() { 
        return estado; 
    }
    
    public void setEstado(String estado) { 
        this.estado = estado; 
    }

    public String getFormato() { 
        return formato; 
    }
    
    public void setFormato(String formato) { 
        this.formato = formato; 
    }

    public String getTituloPelicula() { 
        return tituloPelicula; 
    }
    
    public void setTituloPelicula(String tituloPelicula) { 
        this.tituloPelicula = tituloPelicula; 
    }

    @Override
    public String toString() {
        return "Funcion{" +
               "id=" + id +
               ", peliculaId=" + peliculaId +
               ", salaId=" + salaId +
               ", fechaHora=" + fechaHora +
               ", precioBase=" + precioBase +
               ", estado='" + estado + '\'' +
               ", formato='" + formato + '\'' +
               ", tituloPelicula='" + tituloPelicula + '\'' +
               '}';
    }
}