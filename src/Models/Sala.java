package Models;

public class Sala {
    private int id;
    private int numero;
    private String nombreSala;
    private String descripcionAdicional;

    // Constructor vacío
    public Sala() {}

    // Constructor completo
    public Sala(int id, int numero, String nombreSala, String descripcionAdicional) {
        this.id = id;
        this.numero = numero;
        this.nombreSala = nombreSala;
        this.descripcionAdicional = descripcionAdicional;
    }

    // Getters y Setters
    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }

    public int getNumero() { 
        return numero; 
    }
    
    public void setNumero(int numero) { 
        this.numero = numero; 
    }

    public String getNombreSala() { 
        return nombreSala; 
    }
    
    public void setNombreSala(String nombreSala) { 
        this.nombreSala = nombreSala; 
    }

    public String getDescripcionAdicional() { 
        return descripcionAdicional; 
    }
    
    public void setDescripcionAdicional(String descripcionAdicional) { 
        this.descripcionAdicional = descripcionAdicional; 
    }

    @Override
    public String toString() {
        return "Sala{" +
               "id=" + id +
               ", numero=" + numero +
               ", nombreSala='" + nombreSala + '\'' +
               ", descripcionAdicional='" + descripcionAdicional + '\'' +
               '}';
    }
}