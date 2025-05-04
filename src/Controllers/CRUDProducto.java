package Controllers;

import Models.CategoriaProducto;
import Models.ConexionMySQL;
import Models.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CRUDProducto extends ConexionMySQL implements IProducto {

    public CRUDProducto() {
        // No se necesita inicializar nada, ya que se hereda ConexionMySQL
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

        // Verificar unicidad del nombre
        String sqlCheck = "SELECT COUNT(*) FROM Productos WHERE Nombre=?";
        try (Connection conn = this.Conectar();
             PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck)) {
            stmtCheck.setString(1, producto.getNombre());
            ResultSet rs = stmtCheck.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "Ya existe un producto con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al verificar el nombre del producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String sql = "INSERT INTO Productos (CategoriaID, Nombre, Descripcion, PrecioUnitario, Stock) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, producto.getCategoriaId());
            stmt.setString(2, producto.getNombre());
            stmt.setString(3, producto.getDescripcion());
            stmt.setDouble(4, producto.getPrecioUnitario());
            stmt.setInt(5, producto.getStock());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al registrar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
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

        // Verificar unicidad del nombre (excluyendo el producto actual)
        String sqlCheck = "SELECT COUNT(*) FROM Productos WHERE Nombre=? AND ID!=?";
        try (Connection conn = this.Conectar();
             PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck)) {
            stmtCheck.setString(1, producto.getNombre());
            stmtCheck.setInt(2, producto.getId());
            ResultSet rs = stmtCheck.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "Ya existe otro producto con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al verificar el nombre del producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String sql = "UPDATE Productos SET CategoriaID=?, Nombre=?, Descripcion=?, PrecioUnitario=?, Stock=? WHERE ID=?";

        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, producto.getCategoriaId());
            stmt.setString(2, producto.getNombre());
            stmt.setString(3, producto.getDescripcion());
            stmt.setDouble(4, producto.getPrecioUnitario());
            stmt.setInt(5, producto.getStock());
            stmt.setInt(6, producto.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Productos WHERE ID=?";

        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public List<Producto> obtenerTodos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM Productos";

        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al listar los productos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return productos;
    }

    @Override
    public Producto obtenerPorId(int id) {
        String sql = "SELECT * FROM Productos WHERE ID=?";

        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearProducto(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al buscar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM Productos WHERE Nombre LIKE ?";

        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nombre + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    productos.add(mapearProducto(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al buscar productos por nombre.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return productos;
    }

    @Override
    public Producto obtenerPorUsuario(String usuario) {
        // No se usa para productos, se retorna null
        return null;
    }

    @Override
    public List<CategoriaProducto> obtenerTodasCategorias() {
        List<CategoriaProducto> categorias = new ArrayList<>();
        String sql = "SELECT * FROM CategoriasProducto ORDER BY Nombre";

        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categorias.add(new CategoriaProducto(
                    rs.getInt("ID"),
                    rs.getString("Nombre"),
                    rs.getString("Descripcion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar las categorías.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return categorias;
    }

    @Override
    public String obtenerNombreCategoria(int categoriaId) {
        String sql = "SELECT Nombre FROM CategoriasProducto WHERE ID=?";
        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoriaId);
            try (ResultSet rs = stmt.executeQuery()) {
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
        String sql = "SELECT ID FROM CategoriasProducto WHERE Nombre=?";
        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Retorna 0 si no se encuentra (validado en registrar/actualizar)
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
        String sql = "SELECT COUNT(*) FROM CategoriasProducto WHERE ID=?";
        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoriaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}