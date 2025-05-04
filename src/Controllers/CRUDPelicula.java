package Controllers;

import Models.ConexionMySQL;
import Models.Pelicula;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CRUDPelicula extends ConexionMySQL implements IPelicula {

    public CRUDPelicula() {
        // Constructor vacío, hereda conexión de ConexionMySQL
    }

    @Override
    public boolean registrar(Pelicula pelicula) {
        // Validaciones
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

        // Generar PosterURL automáticamente
        String posterURL = generatePosterURL(pelicula.getTitulo());
        pelicula.setPosterURL(posterURL);

        String sql = "INSERT INTO Peliculas (Titulo, Sinopsis, Genero, Director, ActoresPrincipales, FechaLanzamiento, " +
                     "IdiomaOriginal, SubtitulosDisponibles, DuracionMinutos, Clasificacion, PosterURL) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pelicula.getTitulo());
            stmt.setString(2, pelicula.getSinopsis());
            stmt.setString(3, pelicula.getGenero());
            stmt.setString(4, pelicula.getDirector());
            stmt.setString(5, pelicula.getActoresPrincipales());
            stmt.setDate(6, pelicula.getFechaLanzamiento() != null ? java.sql.Date.valueOf(pelicula.getFechaLanzamiento()) : null);
            stmt.setString(7, pelicula.getIdiomaOriginal());
            stmt.setString(8, pelicula.getSubtitulosDisponibles());
            stmt.setInt(9, pelicula.getDuracionMinutos());
            stmt.setString(10, pelicula.getClasificacion());
            stmt.setString(11, pelicula.getPosterURL());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Pelicula pelicula) {
        // Mismas validaciones que en registrar
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

        // Actualizar PosterURL
        pelicula.setPosterURL(generatePosterURL(pelicula.getTitulo()));

        String sql = "UPDATE Peliculas SET Titulo=?, Sinopsis=?, Genero=?, Director=?, ActoresPrincipales=?, " +
                     "FechaLanzamiento=?, IdiomaOriginal=?, SubtitulosDisponibles=?, DuracionMinutos=?, " +
                     "Clasificacion=?, PosterURL=? WHERE ID=?";

        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pelicula.getTitulo());
            stmt.setString(2, pelicula.getSinopsis());
            stmt.setString(3, pelicula.getGenero());
            stmt.setString(4, pelicula.getDirector());
            stmt.setString(5, pelicula.getActoresPrincipales());
            stmt.setDate(6, pelicula.getFechaLanzamiento() != null ? java.sql.Date.valueOf(pelicula.getFechaLanzamiento()) : null);
            stmt.setString(7, pelicula.getIdiomaOriginal());
            stmt.setString(8, pelicula.getSubtitulosDisponibles());
            stmt.setInt(9, pelicula.getDuracionMinutos());
            stmt.setString(10, pelicula.getClasificacion());
            stmt.setString(11, pelicula.getPosterURL());
            stmt.setInt(12, pelicula.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Peliculas WHERE ID=?";

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
    public Pelicula obtenerPorId(int id) {
        String sql = "SELECT * FROM Peliculas WHERE ID=?";

        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
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
        // La tabla Peliculas no tiene un campo relacionado con usuario
        return null;
    }

    @Override
    public List<Pelicula> buscarPorTitulo(String titulo) {
        List<Pelicula> peliculas = new ArrayList<>();
        String sql;
        PreparedStatement stmt;

        if (titulo == null || titulo.trim().isEmpty()) {
            sql = "SELECT * FROM Peliculas";
            try (Connection conn = this.Conectar();
                 PreparedStatement stmtAll = conn.prepareStatement(sql);
                 ResultSet rs = stmtAll.executeQuery()) {
                while (rs.next()) {
                    peliculas.add(mapearPelicula(rs));
                }
                return peliculas;
            } catch (SQLException e) {
                e.printStackTrace();
                return peliculas;
            }
        }

        sql = "SELECT * FROM Peliculas WHERE Titulo LIKE ?";
        try (Connection conn = this.Conectar();
             PreparedStatement stmtSearch = conn.prepareStatement(sql)) {
            stmtSearch.setString(1, "%" + titulo + "%");
            try (ResultSet rs = stmtSearch.executeQuery()) {
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
        String sql = "SELECT * FROM Peliculas";

        try (Connection conn = this.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
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
            rs.getDate("FechaLanzamiento") != null ? rs.getDate("FechaLanzamiento").toLocalDate() : null,
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