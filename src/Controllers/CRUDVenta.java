package Controllers;

import Models.ConexionMySQL;
import Models.Venta;
import Models.Funcion;
import Models.Producto;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CRUDVenta extends ConexionMySQL implements IVenta {

    public CRUDVenta() {
    }

    @Override
    public boolean registrar(Venta objeto) {
        String sql = "INSERT INTO Ventas (EmpleadoID, FechaHora, TipoVenta, MetodoPago, PrecioTotal) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = this.Conectar(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, objeto.getEmpleadoId());
            stmt.setObject(2, objeto.getFechaHora());
            stmt.setString(3, objeto.getTipoVenta());
            stmt.setString(4, objeto.getMetodoPago());
            stmt.setDouble(5, objeto.getPrecioTotal());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    objeto.setId(rs.getInt(1));
                }
                rs.close();
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Venta objeto) {
        String sql = "UPDATE Ventas SET EmpleadoID = ?, FechaHora = ?, TipoVenta = ?, MetodoPago = ?, PrecioTotal = ? WHERE ID = ?";
        try (Connection conn = this.Conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, objeto.getEmpleadoId());
            stmt.setObject(2, objeto.getFechaHora());
            stmt.setString(3, objeto.getTipoVenta());
            stmt.setString(4, objeto.getMetodoPago());
            stmt.setDouble(5, objeto.getPrecioTotal());
            stmt.setInt(6, objeto.getId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Ventas WHERE ID = ?";
        try (Connection conn = this.Conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Venta> obtenerTodos() {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM Ventas";
        try (Connection conn = this.Conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Venta venta = new Venta(
                        rs.getInt("ID"),
                        rs.getInt("EmpleadoID"),
                        rs.getObject("FechaHora", LocalDateTime.class),
                        rs.getString("TipoVenta"),
                        rs.getString("MetodoPago"),
                        rs.getDouble("PrecioTotal")
                );
                ventas.add(venta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventas;
    }

    @Override
    public Venta obtenerPorId(int id) {
        String sql = "SELECT * FROM Ventas WHERE ID = ?";
        try (Connection conn = this.Conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Venta(
                        rs.getInt("ID"),
                        rs.getInt("EmpleadoID"),
                        rs.getObject("FechaHora", LocalDateTime.class),
                        rs.getString("TipoVenta"),
                        rs.getString("MetodoPago"),
                        rs.getDouble("PrecioTotal")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Venta obtenerPorUsuario(String usuario) {
        return null; // No aplicable para ventas
    }

    @Override
    public boolean registrarVenta(Venta venta, int funcionId, int precioEntradaId, String numeroFila, int numeroAsiento, DefaultTableModel productosTable, int clienteId) {
        String sqlVenta = "CALL RegistrarVenta(?, ?, ?, ?, ?, ?)";
        try (Connection conn = this.Conectar()) {
            // Iniciar transacción
            conn.setAutoCommit(false);

            // Registrar la venta
            CallableStatement stmtVenta = conn.prepareCall(sqlVenta);
            stmtVenta.setInt(1, venta.getEmpleadoId());
            stmtVenta.setObject(2, venta.getFechaHora());
            stmtVenta.setString(3, venta.getTipoVenta());
            stmtVenta.setString(4, venta.getMetodoPago());
            stmtVenta.setDouble(5, venta.getPrecioTotal());
            stmtVenta.registerOutParameter(6, Types.INTEGER);
            stmtVenta.execute();
            int ventaId = stmtVenta.getInt(6);

            // Registrar la entrada
            CallableStatement stmtEntrada = conn.prepareCall("{CALL RegistrarEntrada(?, ?, ?, ?, ?, ?, ?)}");
            stmtEntrada.setInt(1, funcionId);
            if (clienteId == 0) {
                stmtEntrada.setNull(2, Types.INTEGER);
            } else {
                stmtEntrada.setInt(2, clienteId);
            }
            stmtEntrada.setInt(3, precioEntradaId);
            stmtEntrada.setString(4, numeroFila);
            stmtEntrada.setInt(5, numeroAsiento);
            stmtEntrada.setObject(6, LocalDateTime.now());
            stmtEntrada.registerOutParameter(7, Types.BOOLEAN);
            stmtEntrada.execute();
            boolean entradaSuccess = stmtEntrada.getBoolean(7);
            if (!entradaSuccess) {
                conn.rollback();
                JOptionPane.showMessageDialog(null, "El asiento seleccionado ya está ocupado.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Actualizar la entrada con el VentaID
            String sqlUpdateEntrada = "UPDATE Entradas SET VentaID = ? WHERE ID = LAST_INSERT_ID()";
            try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdateEntrada)) {
                stmtUpdate.setInt(1, ventaId);
                stmtUpdate.executeUpdate();
            }

            // Registrar productos (si los hay)
            for (int i = 0; i < productosTable.getRowCount(); i++) {
                String itemId = productosTable.getValueAt(i, 0).toString();
                int productoId = Integer.parseInt(itemId.split(" - ")[0].substring(1));
                double precio = Double.parseDouble(productosTable.getValueAt(i, 2).toString());
                int cantidad = Integer.parseInt(productosTable.getValueAt(i, 3).toString());

                CallableStatement stmtDetalle = conn.prepareCall("{CALL RegistrarDetalleVenta(?, ?, ?, ?, ?)}");
                stmtDetalle.setInt(1, ventaId);
                stmtDetalle.setInt(2, productoId);
                stmtDetalle.setInt(3, cantidad);
                stmtDetalle.setDouble(4, precio);
                stmtDetalle.setDouble(5, 0.05); // Descuento fijo
                stmtDetalle.execute();
            }

            // Confirmar transacción
            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try (Connection conn = this.Conectar()) {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Error al registrar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public List<Funcion> obtenerFuncionesDisponibles() {
        List<Funcion> funciones = new ArrayList<>();
        String sql = "CALL ObtenerFuncionesDisponibles()";
        try (Connection conn = this.Conectar(); CallableStatement stmt = conn.prepareCall(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Funcion funcion = new Funcion();
                funcion.setId(rs.getInt("ID"));
                funcion.setSalaId(rs.getInt("Sala"));
                funcion.setFechaHora(rs.getObject("FechaHora", LocalDateTime.class));
                funcion.setPrecioBase(rs.getDouble("PrecioBase"));
                funcion.setFormato(rs.getString("Formato"));
                funcion.setTituloPelicula(rs.getString("Titulo"));
                funciones.add(funcion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener funciones: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return funciones;
    }

    @Override
    public List<Producto> obtenerProductosDisponibles() {
        List<Producto> productos = new ArrayList<>();
        String sql = "CALL ObtenerProductosDisponibles()";
        try (Connection conn = this.Conectar(); CallableStatement stmt = conn.prepareCall(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("ID"));
                producto.setNombre(rs.getString("Nombre"));
                producto.setDescripcion(rs.getString("Descripcion"));
                producto.setPrecioUnitario(rs.getDouble("PrecioUnitario"));
                producto.setStock(rs.getInt("Stock"));
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return productos;
    }

    @Override
    public List<String> obtenerTiposEntrada() {
        List<String> tipos = new ArrayList<>();
        String sql = "SELECT ID, Nombre, Precio FROM PrecioEntradas";
        try (Connection conn = this.Conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tipos.add(rs.getInt("ID") + " - " + rs.getString("Nombre") + " ($" + rs.getDouble("Precio") + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tipos;
    }
}
