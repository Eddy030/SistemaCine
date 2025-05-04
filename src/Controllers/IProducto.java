package Controllers;

import Models.CategoriaProducto;
import Models.Producto;
import Repository.IRepositorioCRUD;
import Repository.IRepositorioGET;
import java.util.List;

public interface IProducto extends IRepositorioCRUD<Producto>, IRepositorioGET<Producto> {
    List<Producto> buscarPorNombre(String nombre);
    List<CategoriaProducto> obtenerTodasCategorias();
    String obtenerNombreCategoria(int categoriaId);
    int obtenerCategoriaId(String nombre);
}