package Controllers;

import Models.Usuario;
import Models.ConexionMySQL;
import Repository.IRepositorioGET;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioController extends ConexionMySQL implements IRepositorioGET<Usuario> {

    @Override
    public Usuario obtenerPorUsuario(String usuario) {
        String call = "{CALL sp_obtener_empleado_por_usuario(?)}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call)) {

            cs.setString(1, usuario);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Usuario obtenerPorId(int id) {
        String call = "{CALL sp_obtener_empleado_por_id(?)}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Usuario login(String usuario, String contraseña) {
        if (usuario == null || usuario.trim().isEmpty()
                || contraseña == null || contraseña.trim().isEmpty()) {
            return null;
        }
        Usuario u = obtenerPorUsuario(usuario);
        if (u != null && contraseña.equals(u.getContraseña())) {
            return u;
        }
        return null;
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("ID"),
                rs.getString("Usuario"),
                rs.getString("Contrasena"),
                rs.getString("Nombre"),
                rs.getString("Apellido"),
                rs.getString("Email"),
                rs.getString("Telefono"),
                rs.getDate("FechaContratacion") != null
                ? rs.getDate("FechaContratacion").toString()
                : "",
                rs.getString("Direccion"),
                rs.getInt("RolID")
        );
    }
}
