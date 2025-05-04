package Controllers;

import Models.Pelicula;
import Repository.IRepositorioCRUD;
import Repository.IRepositorioGET;
import java.util.List;

public interface IPelicula extends IRepositorioCRUD<Pelicula>, IRepositorioGET<Pelicula> {
    List<Pelicula> buscarPorTitulo(String titulo);
}