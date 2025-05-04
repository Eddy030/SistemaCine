package Controllers;

import Models.ConexionMySQL;
import Models.Pelicula;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class CRUDPelicula extends ConexionMySQL implements IPelicula {

    public CRUDPelicula() {
        // Constructor vacío, hereda conexión de ConexionMySQL
    }

    @Override
    public boolean registrar(Pelicula pelicula) {
        if (pelicula.getTitulo() == null || pelicula.getTitulo().trim().isEmpty()) {
            return false;
        }
        if (pelicula.getGenero() == null || pelicula.getGenero().trim().isEmpty()) {
            return false;
        }
        if (pelicula.getIdiomaOriginal() == null || pelicula.getIdiomaOriginal().trim().isEmpty()) {
            return false;
        }
        if (pelicula.getDuracionMinutos() <= 0) {
            return false;
        }
        if (pelicula.getClasificacion() == null || pelicula.getClasificacion().trim().isEmpty()) {
            return false;
        }
        if (pelicula.getFechaLanzamiento() == null) {
            return false;
        }

        String posterURL = generatePosterURL(pelicula.getTitulo());
        pelicula.setPosterURL(posterURL);

        String call = "{CALL sp_registrar_pelicula(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call)) {

            cs.setString(1, pelicula.getTitulo());
            cs.setString(2, pelicula.getSinopsis());
            cs.setString(3, pelicula.getGenero());
            cs.setString(4, pelicula.getDirector());
            cs.setString(5, pelicula.getActoresPrincipales());
            cs.setDate(6, java.sql.Date.valueOf(pelicula.getFechaLanzamiento()));
            cs.setString(7, pelicula.getIdiomaOriginal());
            cs.setString(8, pelicula.getSubtitulosDisponibles());
            cs.setInt(9, pelicula.getDuracionMinutos());
            cs.setString(10, pelicula.getClasificacion());
            cs.setString(11, pelicula.getPosterURL());
            cs.registerOutParameter(12, Types.TINYINT);

            cs.execute();
            return cs.getByte(12) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Pelicula pelicula) {
        if (pelicula.getTitulo() == null || pelicula.getTitulo().trim().isEmpty()) {
            return false;
        }
        if (pelicula.getGenero() == null || pelicula.getGenero().trim().isEmpty()) {
            return false;
        }
        if (pelicula.getIdiomaOriginal() == null || pelicula.getIdiomaOriginal().trim().isEmpty()) {
            return false;
        }
        if (pelicula.getDuracionMinutos() <= 0) {
            return false;
        }
        if (pelicula.getClasificacion() == null || pelicula.getClasificacion().trim().isEmpty()) {
            return false;
        }
        if (pelicula.getFechaLanzamiento() == null) {
            return false;
        }

        String posterURL = generatePosterURL(pelicula.getTitulo());
        pelicula.setPosterURL(posterURL);

        String call = "{CALL sp_actualizar_pelicula(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, pelicula.getId());
            cs.setString(2, pelicula.getTitulo());
            cs.setString(3, pelicula.getSinopsis());
            cs.setString(4, pelicula.getGenero());
            cs.setString(5, pelicula.getDirector());
            cs.setString(6, pelicula.getActoresPrincipales());
            cs.setDate(7, java.sql.Date.valueOf(pelicula.getFechaLanzamiento()));
            cs.setString(8, pelicula.getIdiomaOriginal());
            cs.setString(9, pelicula.getSubtitulosDisponibles());
            cs.setInt(10, pelicula.getDuracionMinutos());
            cs.setString(11, pelicula.getClasificacion());
            cs.setString(12, pelicula.getPosterURL());
            cs.registerOutParameter(13, Types.TINYINT);

            cs.execute();
            return cs.getByte(13) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String call = "{CALL sp_eliminar_pelicula(?, ?)}";
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
    public Pelicula obtenerPorId(int id) {
        String call = "{CALL sp_obtener_pelicula_por_id(?)}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearPelicula(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Pelicula obtenerPorUsuario(String usuario) {
        // No aplica para películas
        return null;
    }

    @Override
    public List<Pelicula> buscarPorTitulo(String titulo) {
        List<Pelicula> peliculas = new ArrayList<>();
        String call = "{CALL sp_buscar_pelicula_por_titulo(?)}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call)) {

            cs.setString(1, titulo == null ? "" : titulo);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    peliculas.add(mapearPelicula(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return peliculas;
    }

    @Override
    public List<Pelicula> obtenerTodos() {
        List<Pelicula> peliculas = new ArrayList<>();
        String call = "{CALL sp_obtener_todas_peliculas()}";
        try (Connection conn = this.Conectar(); CallableStatement cs = conn.prepareCall(call); ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                peliculas.add(mapearPelicula(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return peliculas;
    }

    private Pelicula mapearPelicula(ResultSet rs) throws SQLException {
        return new Pelicula(
                rs.getInt("ID"),
                rs.getString("Titulo"),
                rs.getString("Sinopsis"),
                rs.getString("Genero"),
                rs.getString("Director"),
                rs.getString("ActoresPrincipales"),
                rs.getDate("FechaLanzamiento") != null
                ? rs.getDate("FechaLanzamiento").toLocalDate()
                : null,
                rs.getString("IdiomaOriginal"),
                rs.getString("SubtitulosDisponibles"),
                rs.getInt("DuracionMinutos"),
                rs.getString("Clasificacion"),
                rs.getString("PosterURL")
        );
    }

    private String generatePosterURL(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            return "/posters/default.jpg";
        }
        String normalized = titulo.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")
                .replaceAll("\\s+", "-");
        return "/posters/" + normalized + ".jpg";
    }
}
