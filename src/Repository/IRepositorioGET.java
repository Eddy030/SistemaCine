package Repository;

public interface IRepositorioGET<T> {
    T obtenerPorId(int id);
    T obtenerPorUsuario(String usuario);
}
