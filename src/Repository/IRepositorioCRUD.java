package Repository;

import java.util.List;

public interface IRepositorioCRUD<T> {
    boolean registrar(T objeto);
    boolean actualizar(T objeto);
    boolean eliminar(int id);
    List<T> obtenerTodos();
}
