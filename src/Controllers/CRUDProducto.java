package Controllers;

import Models.CategoriaProducto;
import Models.ConexionMySQL;
import Models.Producto;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CRUDProducto extends ConexionMySQL implements IProducto {

    public CRUDProducto() {
        // Hereda conexión de ConexionMySQL
    }

    @Override
    public boolean registrar(Producto producto) {
        // Validaciones
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre del producto es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (producto.getPrecioUnitario() <= 0) {
            JOptionPane.showMessageDialog(null, "El precio unitario debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (producto.getStock() < 0) {
            JOptionPane.showMessageDialog(null, "El stock no puede ser negativo.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!validarCategoriaId(producto.getCategoriaId())) {
            JOptionPane.showMessageDialog(null, "La categoría seleccionada no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String call = "{CALL sp_registrar_producto(?, ?, ?, ?, ?, ?)}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, producto.getCategoriaId());
            cs.setString(2, producto.getNombre());
            cs.setString(3, producto.getDescripcion());
            cs.setDouble(4, producto.getPrecioUnitario());
            cs.setInt(5, producto.getStock());
            cs.registerOutParameter(6, Types.TINYINT);

            cs.execute();
            return cs.getByte(6) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al registrar producto: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean actualizar(Producto producto) {
        // Validaciones
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre del producto es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (producto.getPrecioUnitario() <= 0) {
            JOptionPane.showMessageDialog(null, "El precio unitario debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (producto.getStock() < 0) {
            JOptionPane.showMessageDialog(null, "El stock no puede ser negativo.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!validarCategoriaId(producto.getCategoriaId())) {
            JOptionPane.showMessageDialog(null, "La categoría seleccionada no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String call = "{CALL sp_actualizar_producto(?, ?, ?, ?, ?, ?, ?)}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, producto.getId());
            cs.setInt(2, producto.getCategoriaId());
            cs.setString(3, producto.getNombre());
            cs.setString(4, producto.getDescripcion());
            cs.setDouble(5, producto.getPrecioUnitario());
            cs.setInt(6, producto.getStock());
            cs.registerOutParameter(7, Types.TINYINT);

            cs.execute();
            return cs.getByte(7) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar producto: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        if (id <= 0) {
            return false;
        }

        String call = "{CALL sp_eliminar_producto(?, ?)}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, id);
            cs.registerOutParameter(2, Types.TINYINT);

            cs.execute();
            return cs.getByte(2) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar producto: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public List<Producto> obtenerTodos() {
        List<Producto> lista = new ArrayList<>();
        String call = "{CALL sp_obtener_todos_productos()}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call); ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearProducto(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al listar productos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }

    @Override
    public Producto obtenerPorId(int id) {
        if (id <= 0) {
            return null;
        }

        String call = "{CALL sp_obtener_producto_por_id(?)}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearProducto(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al buscar producto: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> lista = new ArrayList<>();
        String call = "{CALL sp_buscar_producto_por_nombre(?)}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call)) {

            cs.setString(1, nombre == null ? "" : nombre);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearProducto(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al buscar productos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }

    @Override
    public List<CategoriaProducto> obtenerTodasCategorias() {
        List<CategoriaProducto> lista = new ArrayList<>();
        String call = "{CALL sp_obtener_todas_categorias()}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call); ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                lista.add(new CategoriaProducto(
                        rs.getInt("ID"),
                        rs.getString("Nombre"),
                        rs.getString("Descripcion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al listar categorías: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }

    @Override
    public String obtenerNombreCategoria(int categoriaId) {
        if (categoriaId <= 0) {
            return "Desconocida";
        }

        String call = "{CALL sp_obtener_nombre_categoria(?)}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, categoriaId);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Nombre");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Desconocida";
    }

    @Override
    public int obtenerCategoriaId(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return 0;
        }

        String call = "{CALL sp_obtener_categoria_id(?)}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call)) {

            cs.setString(1, nombre);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Producto mapearProducto(ResultSet rs) throws SQLException {
        return new Producto(
                rs.getInt("ID"),
                rs.getInt("CategoriaID"),
                rs.getString("Nombre"),
                rs.getString("Descripcion"),
                rs.getDouble("PrecioUnitario"),
                rs.getInt("Stock")
        );
    }

    private boolean validarCategoriaId(int categoriaId) {
        String call = "{CALL sp_validar_categoria_id(?, ?)}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, categoriaId);
            cs.registerOutParameter(2, Types.TINYINT);
            cs.execute();
            return cs.getByte(2) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Producto obtenerPorUsuario(String usuario) {
        // No aplica para producto
        return null;
    }

}
