package Controllers;

import Models.Usuario;
import Repository.IRepositorioCRUD;
import Repository.IRepositorioGET;
import java.util.List;

public interface IEmpleado extends IRepositorioCRUD<Usuario>, IRepositorioGET<Usuario> {
    List<Usuario> obtenerPorRol(String rol);
}