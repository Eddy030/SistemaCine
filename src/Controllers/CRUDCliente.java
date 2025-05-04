package Controllers;

import Models.Cliente;
import Models.ConexionMySQL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class CRUDCliente extends ConexionMySQL implements ICliente {

    public CRUDCliente() {
        // Constructor vacío
    }

    @Override
    public boolean registrar(Cliente cliente) {
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

        String call = "{CALL sp_registrar_cliente(?, ?, ?, ?, ?, ?)}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call)) {

            cs.setString(1, cliente.getNombre());
            cs.setString(2, cliente.getApellido());
            cs.setString(3, cliente.getEmail());
            cs.setString(4, cliente.getTelefono());
            cs.setDate(5, java.sql.Date.valueOf(cliente.getFechaRegistro()));
            cs.registerOutParameter(6, Types.TINYINT);

            cs.execute();
            return cs.getByte(6) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Cliente cliente) {
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

        String call = "{CALL sp_actualizar_cliente(?, ?, ?, ?, ?, ?, ?)}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, cliente.getId());
            cs.setString(2, cliente.getNombre());
            cs.setString(3, cliente.getApellido());
            cs.setString(4, cliente.getEmail());
            cs.setString(5, cliente.getTelefono());
            cs.setDate(6, java.sql.Date.valueOf(cliente.getFechaRegistro()));
            cs.registerOutParameter(7, Types.TINYINT);

            cs.execute();
            return cs.getByte(7) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String call = "{CALL sp_eliminar_cliente(?, ?)}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, id);
            cs.registerOutParameter(2, Types.TINYINT);

            cs.execute();
            return cs.getByte(2) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Cliente obtenerPorId(int id) {
        String call = "{CALL sp_obtener_cliente_por_id(?)}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
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
        // No aplica para clientes
        return null;
    }

    @Override
    public List<Cliente> buscarPorNombre(String nombre) {
        List<Cliente> clientes = new ArrayList<>();
        String call = "{CALL sp_buscar_cliente_por_nombre(?)}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call)) {

            cs.setString(1, nombre == null ? "" : nombre);
            try (ResultSet rs = cs.executeQuery()) {
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
        String call = "{CALL sp_obtener_todos_clientes()}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call); ResultSet rs = cs.executeQuery()) {

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
        cliente.setFechaRegistro(
                rs.getDate("FechaRegistro") != null
                ? rs.getDate("FechaRegistro").toLocalDate()
                : null
        );
        return cliente;
    }
}
