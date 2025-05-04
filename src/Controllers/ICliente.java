package Controllers;

import Models.Cliente;
import Repository.IRepositorioCRUD;
import Repository.IRepositorioGET;
import java.util.List;

public interface ICliente extends IRepositorioCRUD<Cliente>, IRepositorioGET<Cliente> {
    List<Cliente> buscarPorNombre(String nombre);
}