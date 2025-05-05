package Controllers;

import Models.ConexionMySQL;
import Models.Entrada;
import Models.EntradaDetallada;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CRUDEntrada implementa IEntrada usando procedimientos almacenados:
 * sp_insertarEntrada, sp_actualizarEntrada, sp_eliminarEntrada,
 * sp_listarEntradasDetallado y sp_obtenerEntradaPorIdDetallado.
 */
public class CRUDEntrada extends ConexionMySQL implements IEntrada, IEntradaDetallada {

    @Override
    public boolean registrar(Entrada e) {
        String sql = "CALL sp_insertarEntrada(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = this.Conectar();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, e.getFuncionID());
            cs.setInt(2, e.getClienteID());
            cs.setInt(3, e.getPrecioEntradaID());
            cs.setString(4, e.getNumeroFila());
            cs.setInt(5, e.getNumeroAsiento());
            cs.setTimestamp(6, new Timestamp(e.getFechaVenta().getTime()));
            cs.setString(7, e.getEstado());

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    e.setId(rs.getInt("ID"));
                    return true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean actualizar(Entrada e) {
        String sql = "CALL sp_actualizarEntrada(?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = this.Conectar();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, e.getId());
            cs.setInt(2, e.getFuncionID());
            cs.setInt(3, e.getClienteID());
            cs.setInt(4, e.getPrecioEntradaID());
            cs.setString(5, e.getNumeroFila());
            cs.setInt(6, e.getNumeroAsiento());
            cs.setTimestamp(7, new Timestamp(e.getFechaVenta().getTime()));
            cs.setString(8, e.getEstado());

            int affected = cs.executeUpdate();
            return affected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "CALL sp_eliminarEntrada(?)";
        try (Connection conn = this.Conectar();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, id);
            int affected = cs.executeUpdate();
            return affected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Entrada> obtenerTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public EntradaDetallada obtenerPorId(int id) {
    EntradaDetallada entradaDetallada = null;
    Connection con = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
        con = this.Conectar();

        stmt = con.prepareCall("{CALL sp_obtenerEntradaPorIdDetallado(?)}");
        stmt.setInt(1, id);
        rs = stmt.executeQuery();

        if (rs.next()) {
            entradaDetallada = new EntradaDetallada(
                rs.getInt("ID"),
                rs.getString("Pelicula"),
                rs.getString("Cliente"),
                rs.getString("PrecioCategoria"),
                rs.getString("NumeroFila"),
                rs.getInt("NumeroAsiento"),
                rs.getTimestamp("FechaVenta"),
                rs.getString("Estado")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Manejo de errores
    } finally {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (con != null) con.close(); // ahora sí puedes cerrarlo sin error
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return entradaDetallada;
    }

    @Override
    public EntradaDetallada obtenerPorUsuario(String usuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<EntradaDetallada> obtenerTodaslasentradas() {
         List<EntradaDetallada> lista = new ArrayList<>();
    String sql = "CALL sp_listarEntradasDetallado()";

    try (Connection conn = this.Conectar();
         CallableStatement cs = conn.prepareCall(sql);
         ResultSet rs = cs.executeQuery()) {

        while (rs.next()) {
            EntradaDetallada ed = new EntradaDetallada();
            ed.setId(rs.getInt("ID"));
            ed.setPelicula(rs.getString("Pelicula"));
            ed.setCliente(rs.getString("Cliente"));
            ed.setPrecioCategoria(rs.getString("PrecioCategoria"));
            ed.setNumeroFila(rs.getString("NumeroFila"));
            ed.setNumeroAsiento(rs.getInt("NumeroAsiento"));
            ed.setFechaVenta(rs.getTimestamp("FechaVenta"));
            ed.setEstado(rs.getString("Estado"));

            lista.add(ed);
        }

    } catch (SQLException ex) {
        ex.printStackTrace(); // O usa tu sistema de logs
    }

    return lista;
    }
    

}
