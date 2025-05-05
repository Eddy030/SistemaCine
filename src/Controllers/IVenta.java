package Controllers;

import Models.Venta;
import Models.Funcion;
import Models.Producto;
import Repository.IRepositorioCRUD;
import Repository.IRepositorioGET;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface IVenta extends IRepositorioCRUD<Venta>, IRepositorioGET<Venta> {

    boolean registrarVenta(Venta venta, int funcionId, int precioEntradaId, String numeroFila, int numeroAsiento, DefaultTableModel productosTable, int clienteId);

    List<Funcion> obtenerFuncionesDisponibles();

    List<Producto> obtenerProductosDisponibles();

    List<String> obtenerTiposEntrada();
}
