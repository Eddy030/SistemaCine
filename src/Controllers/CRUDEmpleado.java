package Controllers;

import Models.Usuario;
import Models.ConexionMySQL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CRUDEmpleado extends ConexionMySQL implements IEmpleado {

    public CRUDEmpleado() {
        // Constructor vacío
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

        String call = "{CALL sp_registrar_empleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call)) {

            cs.setString(1, usuario.getUsuario());
            cs.setString(2, usuario.getContraseña());
            cs.setString(3, usuario.getNombre());
            cs.setString(4, usuario.getApellido());
            cs.setString(5, usuario.getEmail());
            cs.setString(6, usuario.getTelefono());
            // FechaContratacion
            if (usuario.getFechaContratacion() != null && !usuario.getFechaContratacion().isEmpty()) {
                cs.setDate(7, Date.valueOf(usuario.getFechaContratacion()));
            } else {
                cs.setNull(7, Types.DATE);
            }
            cs.setString(8, usuario.getDireccion());
            cs.setInt(9, usuario.getRolId());
            cs.registerOutParameter(10, Types.TINYINT);

            cs.execute();
            return cs.getByte(10) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al registrar el empleado: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
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

        String call = "{CALL sp_actualizar_empleado(?, ?, ?, ?, ?, ?, ?, ?)}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, usuario.getId());
            cs.setString(2, usuario.getNombre());
            cs.setString(3, usuario.getApellido());
            cs.setString(4, usuario.getEmail());
            cs.setString(5, usuario.getTelefono());
            if (usuario.getFechaContratacion() != null && !usuario.getFechaContratacion().isEmpty()) {
                cs.setDate(6, Date.valueOf(usuario.getFechaContratacion()));
            } else {
                cs.setNull(6, Types.DATE);
            }
            cs.setString(7, usuario.getDireccion());
            cs.registerOutParameter(8, Types.TINYINT);

            cs.execute();
            return cs.getByte(8) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar el empleado: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String call = "{CALL sp_eliminar_empleado(?, ?)}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, id);
            cs.registerOutParameter(2, Types.TINYINT);

            cs.execute();
            return cs.getByte(2) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar el empleado: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public List<Usuario> obtenerTodos() {
        List<Usuario> empleados = new ArrayList<>();
        String call = "{CALL sp_obtener_todos_empleados()}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call); ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                empleados.add(mapearUsuario(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al listar empleados: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return empleados;
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
            JOptionPane.showMessageDialog(null, "Error al buscar empleado por ID: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

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
            JOptionPane.showMessageDialog(null, "Error al buscar empleado por usuario: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public List<Usuario> obtenerPorRol(String rol) {
        List<Usuario> empleados = new ArrayList<>();
        String call = "{CALL sp_obtener_empleado_por_rol(?)}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call)) {

            cs.setString(1, rol);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    empleados.add(mapearUsuario(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al buscar empleados por rol: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return empleados;
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("ID"));
        u.setUsuario(rs.getString("Usuario"));
        u.setNombre(rs.getString("Nombre"));
        u.setApellido(rs.getString("Apellido"));
        u.setEmail(rs.getString("Email"));
        u.setTelefono(rs.getString("Telefono"));
        Date f = rs.getDate("FechaContratacion");
        u.setFechaContratacion(f != null ? f.toString() : "");
        u.setDireccion(rs.getString("Direccion"));
        u.setRolId(rs.getInt("RolID"));
        return u;
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
