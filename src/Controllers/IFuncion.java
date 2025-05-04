package Controllers;

import Models.Funcion;
import Models.Pelicula;
import Models.Sala;
import Repository.IRepositorioCRUD;
import Repository.IRepositorioGET;
import java.util.List;

public interface IFuncion extends IRepositorioCRUD<Funcion>, IRepositorioGET<Funcion> {
    List<Funcion> buscarFunciones(String criterio, String tipoBusqueda);
    List<Pelicula> obtenerPeliculasDisponibles();
    List<Sala> obtenerSalasDisponibles();
}