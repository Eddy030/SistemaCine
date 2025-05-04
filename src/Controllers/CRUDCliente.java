package Controllers;

import Models.Cliente;
import Models.ConexionMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CRUDCliente extends ConexionMySQL implements ICliente {

    public CRUDCliente() {
        // Constructor vacío
    }

    @Override
    public boolean registrar(Cliente cliente) {
        // Validaciones
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            return false;
        }
        if (cliente.getApellido() == null || cliente.getApellido().trim().isEmpty()) {
            return false;
        }
        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            return false;
        }
        if (cliente.getFechaRegistro() == null) {
            return false;
        }

        String sql = "INSERT INTO Clientes (Nombre, Apellido, Email, Telefono, FechaRegistro) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefono());
            stmt.setDate(5, cliente.getFechaRegistro() != null ? java.sql.Date.valueOf(cliente.getFechaRegistro()) : null);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Cliente cliente) {
        // Validaciones
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            return false;
        }
        if (cliente.getApellido() == null || cliente.getApellido().trim().isEmpty()) {
            return false;
        }
        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            return false;
        }
        if (cliente.getFechaRegistro() == null) {
            return false;
        }

        String sql = "UPDATE Clientes SET Nombre=?, Apellido=?, Email=?, Telefono=?, FechaRegistro=? WHERE ID=?";

        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefono());
            stmt.setDate(5, cliente.getFechaRegistro() != null ? java.sql.Date.valueOf(cliente.getFechaRegistro()) : null);
            stmt.setInt(6, cliente.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Clientes WHERE ID=?";

        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Cliente obtenerPorId(int id) {
        String sql = "SELECT * FROM Clientes WHERE ID=?";

        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Cliente obtenerPorUsuario(String usuario) {
        // La tabla Clientes no tiene un campo relacionado con usuario
        return null;
    }

    @Override
    public List<Cliente> buscarPorNombre(String nombre) {
        List<Cliente> clientes = new ArrayList<>();
        String sql;

        if (nombre == null || nombre.trim().isEmpty()) {
            sql = "SELECT * FROM Clientes";
            try (Connection conn = this.Conectar();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    clientes.add(mapearCliente(rs));
                }
                return clientes;
            } catch (SQLException e) {
                e.printStackTrace();
                return clientes;
            }
        }

        sql = "SELECT * FROM Clientes WHERE Nombre LIKE ? OR Apellido LIKE ?";
        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nombre + "%");
            stmt.setString(2, "%" + nombre + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    clientes.add(mapearCliente(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    @Override
    public List<Cliente> obtenerTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Clientes";

        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                clientes.add(mapearCliente(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("ID"));
        cliente.setNombre(rs.getString("Nombre"));
        cliente.setApellido(rs.getString("Apellido"));
        cliente.setEmail(rs.getString("Email"));
        cliente.setTelefono(rs.getString("Telefono"));
        cliente.setFechaRegistro(rs.getDate("FechaRegistro") != null ? rs.getDate("FechaRegistro").toLocalDate() : null);
        return cliente;
    }
}