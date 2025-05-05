package Controllers;

import Models.ConexionMySQL;
import Models.PrecioEntrada;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CRUDPrecioEntrada extends ConexionMySQL implements IPrecioEntrada {

    @Override
    public boolean registrar(PrecioEntrada objeto) {
        // No implementado
        return false;
    }

    @Override
    public boolean actualizar(PrecioEntrada objeto) {
        // No implementado
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        // No implementado
        return false;
    }

    @Override
    public List<PrecioEntrada> obtenerTodos() {
        List<PrecioEntrada> lista = new ArrayList<>();
        String sql = "SELECT * FROM PrecioEntradas";
        try (Connection conn = this.Conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PrecioEntrada pe = new PrecioEntrada();
                pe.setId(rs.getInt("ID"));
                pe.setNombre(rs.getString("Nombre"));
                pe.setPrecio(rs.getBigDecimal("Precio"));
                pe.setDescripcion(rs.getString("DescripcionAdicional"));
                lista.add(pe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

}
