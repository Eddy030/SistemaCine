package Controllers;

import Models.Usuario;
import Models.ConexionMySQL;
import Repository.IRepositorioGET;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioController extends ConexionMySQL implements IRepositorioGET<Usuario> {

    public UsuarioController() {
        // No se necesita inicializar nada, ya que se hereda ConexionMySQL
    }

    @Override
    public Usuario obtenerPorUsuario(String usuario) {
        String sql = "SELECT * FROM Empleados WHERE Usuario=?";

        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario);
            try (ResultSet rs = stmt.executeQuery()) {
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
        String sql = "SELECT * FROM Empleados WHERE id=?";

        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
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
        if (usuario == null || usuario.trim().isEmpty() || contraseña == null || contraseña.trim().isEmpty()) {
            return null;
        }
        Usuario u = obtenerPorUsuario(usuario);
        if (u != null && u.getContraseña().equals(contraseña)) {
            return u;
        }
        return null;
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
            rs.getInt("id"),
            rs.getString("Usuario"),
            rs.getString("Contraseña"),
            rs.getString("Nombre"),
            rs.getString("Apellido"),
            rs.getString("Email"),
            rs.getString("Telefono"),
            rs.getString("FechaContratacion"),
            rs.getString("Direccion"),
            rs.getInt("RolId")
        );
    }
}