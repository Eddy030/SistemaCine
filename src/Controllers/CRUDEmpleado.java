package Controllers;

import Models.Usuario;
import Models.ConexionMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CRUDEmpleado extends ConexionMySQL implements IEmpleado {

    public CRUDEmpleado() {
    }

    @Override
    public boolean registrar(Usuario usuario) {
        // Validaciones
        if (usuario.getUsuario() == null || usuario.getUsuario().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El usuario es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (usuario.getContraseña() == null || usuario.getContraseña().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "La contraseña es obligatoria.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (usuario.getApellido() == null || usuario.getApellido().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El apellido es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!isValidEmail(usuario.getEmail())) {
            JOptionPane.showMessageDialog(null, "El email no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!isValidTelefono(usuario.getTelefono())) {
            JOptionPane.showMessageDialog(null, "El teléfono debe tener entre 8 y 15 dígitos o estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!isValidFecha(usuario.getFechaContratacion())) {
            JOptionPane.showMessageDialog(null, "La fecha de contratación debe tener el formato YYYY-MM-DD o estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Verificar unicidad de usuario y email
        String sqlCheck = "SELECT COUNT(*) FROM Empleados WHERE Usuario=? OR Email=?";
        try (Connection conn = this.Conectar();
             PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck)) {
            stmtCheck.setString(1, usuario.getUsuario());
            stmtCheck.setString(2, usuario.getEmail() != null ? usuario.getEmail() : "");
            ResultSet rs = stmtCheck.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "El usuario o email ya está registrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al verificar el usuario o email: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String sql = "INSERT INTO Empleados (Usuario, Contraseña, Nombre, Apellido, Email, Telefono, FechaContratacion, Direccion, RolID) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getContraseña());
            stmt.setString(3, usuario.getNombre());
            stmt.setString(4, usuario.getApellido());
            stmt.setString(5, usuario.getEmail());
            stmt.setString(6, usuario.getTelefono());
            if (usuario.getFechaContratacion() != null && !usuario.getFechaContratacion().isEmpty()) {
                stmt.setDate(7, Date.valueOf(usuario.getFechaContratacion()));
            } else {
                stmt.setNull(7, java.sql.Types.DATE);
            }
            stmt.setString(8, usuario.getDireccion());
            stmt.setInt(9, usuario.getRolId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al registrar el empleado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean actualizar(Usuario usuario) {
        // Validaciones
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (usuario.getApellido() == null || usuario.getApellido().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El apellido es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!isValidEmail(usuario.getEmail())) {
            JOptionPane.showMessageDialog(null, "El email no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!isValidTelefono(usuario.getTelefono())) {
            JOptionPane.showMessageDialog(null, "El teléfono debe tener entre 8 y 15 dígitos o estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!isValidFecha(usuario.getFechaContratacion())) {
            JOptionPane.showMessageDialog(null, "La fecha de contratación debe tener el formato YYYY-MM-DD o estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Verificar unicidad de email (excluyendo el propio registro)
        String sqlCheck = "SELECT COUNT(*) FROM Empleados WHERE Email=? AND ID!=?";
        try (Connection conn = this.Conectar();
             PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck)) {
            stmtCheck.setString(1, usuario.getEmail() != null ? usuario.getEmail() : "");
            stmtCheck.setInt(2, usuario.getId());
            ResultSet rs = stmtCheck.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "El email ya está registrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al verificar el email: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String sql = "UPDATE Empleados SET Nombre=?, Apellido=?, Email=?, Telefono=?, FechaContratacion=?, Direccion=? WHERE ID=? AND RolID=1";

        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getTelefono());
            if (usuario.getFechaContratacion() != null && !usuario.getFechaContratacion().isEmpty()) {
                stmt.setDate(5, Date.valueOf(usuario.getFechaContratacion()));
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }
            stmt.setString(6, usuario.getDireccion());
            stmt.setInt(7, usuario.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar el empleado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Empleados WHERE ID=? AND RolID=1";

        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar el empleado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public List<Usuario> obtenerTodos() {
        List<Usuario> empleados = new ArrayList<>();
        String sql = "SELECT ID, Nombre, Apellido, Email, Telefono, FechaContratacion, Direccion, RolID " +
                     "FROM Empleados WHERE RolID=1";

        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                empleados.add(mapearUsuario(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al listar los empleados: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return empleados;
    }

    @Override
    public Usuario obtenerPorId(int id) {
        String sql = "SELECT ID, Nombre, Apellido, Email, Telefono, FechaContratacion, Direccion, RolID " +
                     "FROM Empleados WHERE ID=? AND RolID=1";

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
            JOptionPane.showMessageDialog(null, "Error al buscar el empleado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public Usuario obtenerPorUsuario(String usuario) {
        String sql = "SELECT ID, Nombre, Apellido, Email, Telefono, FechaContratacion, Direccion, RolID " +
                     "FROM Empleados WHERE Usuario=? AND RolID=1";

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
            JOptionPane.showMessageDialog(null, "Error al buscar el empleado por usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public List<Usuario> obtenerPorRol(String rol) {
        List<Usuario> empleados = new ArrayList<>();
        int rolId = rol.equalsIgnoreCase("EMPLEADO") ? 1 : (rol.equalsIgnoreCase("ADMINISTRADOR") ? 2 : -1);
        if (rolId == -1) {
            JOptionPane.showMessageDialog(null, "Rol no válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return empleados;
        }

        String sql = "SELECT ID, Nombre, Apellido, Email, Telefono, FechaContratacion, Direccion, RolID " +
                     "FROM Empleados WHERE RolID=?";

        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, rolId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    empleados.add(mapearUsuario(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al buscar empleados por rol: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return empleados;
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("ID"));
        usuario.setNombre(rs.getString("Nombre"));
        usuario.setApellido(rs.getString("Apellido"));
        usuario.setEmail(rs.getString("Email"));
        usuario.setTelefono(rs.getString("Telefono"));
        Date fechaContratacion = rs.getDate("FechaContratacion");
        usuario.setFechaContratacion(fechaContratacion != null ? fechaContratacion.toString() : "");
        usuario.setDireccion(rs.getString("Direccion"));
        usuario.setRolId(rs.getInt("RolID"));
        return usuario;
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return true; // Email es opcional
        }
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    private boolean isValidTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return true; // Teléfono es opcional
        }
        return telefono.matches("\\+?[0-9]{8,15}");
    }

    private boolean isValidFecha(String fecha) {
        if (fecha == null || fecha.trim().isEmpty()) {
            return true; // Fecha es opcional
        }
        return fecha.matches("\\d{4}-\\d{2}-\\d{2}");
    }
}