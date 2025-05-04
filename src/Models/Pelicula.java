package Models;

import java.time.LocalDate;

public class Pelicula {
    private int id;
    private String titulo;
    private String sinopsis;
    private String genero;
    private String director;
    private String actoresPrincipales;
    private LocalDate fechaLanzamiento;
    private String idiomaOriginal;
    private String subtitulosDisponibles;
    private int duracionMinutos;
    private String clasificacion;
    private String posterURL;

    // Constructor vacío
    public Pelicula() {}

    // Constructor completo
    public Pelicula(int id, String titulo, String sinopsis, String genero, String director, String actoresPrincipales,
                    LocalDate fechaLanzamiento, String idiomaOriginal, String subtitulosDisponibles, int duracionMinutos,
                    String clasificacion, String posterURL) {
        this.id = id;
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.genero = genero;
        this.director = director;
        this.actoresPrincipales = actoresPrincipales;
        this.fechaLanzamiento = fechaLanzamiento;
        this.idiomaOriginal = idiomaOriginal;
        this.subtitulosDisponibles = subtitulosDisponibles;
        this.duracionMinutos = duracionMinutos;
        this.clasificacion = clasificacion;
        this.posterURL = posterURL;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActoresPrincipales() {
        return actoresPrincipales;
    }

    public void setActoresPrincipales(String actoresPrincipales) {
        this.actoresPrincipales = actoresPrincipales;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public String getIdiomaOriginal() {
        return idiomaOriginal;
    }

    public void setIdiomaOriginal(String idiomaOriginal) {
        this.idiomaOriginal = idiomaOriginal;
    }

    public String getSubtitulosDisponibles() {
        return subtitulosDisponibles;
    }

    public void setSubtitulosDisponibles(String subtitulosDisponibles) {
        this.subtitulosDisponibles = subtitulosDisponibles;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }
}