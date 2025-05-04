package Controllers;

import Models.ConexionMySQL;
import Models.Funcion;
import Models.Pelicula;
import Models.Sala;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CRUDFuncion extends ConexionMySQL implements IFuncion {

    public CRUDFuncion() {
        // Constructor vacío, hereda conexión de ConexionMySQL
    }

    @Override
    public List<Funcion> obtenerTodos() {
        List<Funcion> funciones = new ArrayList<>();
        String sql = "CALL ObtenerTodasFunciones()";

        try (Connection conn = this.Conectar();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Funcion funcion = new Funcion();
                funcion.setId(rs.getInt("ID"));
                funcion.setTituloPelicula(rs.getString("TituloPelicula"));
                funcion.setSalaId(rs.getInt("SalaID"));
                funcion.setFechaHora(rs.getObject("FechaHora", LocalDateTime.class));
                funcion.setPrecioBase(rs.getDouble("PrecioBase"));
                funcion.setEstado(rs.getString("Estado"));
                funcion.setFormato(rs.getString("Formato"));
                funciones.add(funcion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return funciones;
    }

    @Override
    public Funcion obtenerPorId(int id) {
        Funcion funcion = null;
        String sql = "CALL ObtenerFuncionPorId(?)";

        try (Connection conn = this.Conectar();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    funcion = new Funcion();
                    funcion.setId(rs.getInt("ID"));
                    funcion.setTituloPelicula(rs.getString("TituloPelicula"));
                    funcion.setPeliculaId(rs.getInt("PeliculaID"));
                    funcion.setSalaId(rs.getInt("SalaID"));
                    funcion.setFechaHora(rs.getObject("FechaHora", LocalDateTime.class));
                    funcion.setPrecioBase(rs.getDouble("PrecioBase"));
                    funcion.setEstado(rs.getString("Estado"));
                    funcion.setFormato(rs.getString("Formato"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return funcion;
    }

    @Override
    public boolean registrar(Funcion funcion) {
        String sql = "CALL RegistrarFuncion(?, ?, ?, ?, ?, ?)";
        try (Connection conn = this.Conectar();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, funcion.getPeliculaId());
            stmt.setInt(2, funcion.getSalaId());
            stmt.setObject(3, funcion.getFechaHora());
            stmt.setDouble(4, funcion.getPrecioBase());
            stmt.setString(5, funcion.getEstado());
            stmt.setString(6, funcion.getFormato());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Funcion funcion) {
        String sql = "CALL ActualizarFuncion(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = this.Conectar();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, funcion.getId());
            stmt.setInt(2, funcion.getPeliculaId());
            stmt.setInt(3, funcion.getSalaId());
            stmt.setObject(4, funcion.getFechaHora());
            stmt.setDouble(5, funcion.getPrecioBase());
            stmt.setString(6, funcion.getEstado());
            stmt.setString(7, funcion.getFormato());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "CALL EliminarFuncion(?)";
        try (Connection conn = this.Conectar();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Funcion obtenerPorUsuario(String usuario) {
        return null; // No aplicable para funciones
    }

    @Override
    public List<Funcion> buscarFunciones(String criterio, String tipoBusqueda) {
        List<Funcion> funciones = new ArrayList<>();
        String sql = "CALL BuscarFunciones(?, ?)";

        try (Connection conn = this.Conectar();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, criterio);
            stmt.setString(2, tipoBusqueda);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Funcion funcion = new Funcion();
                    funcion.setId(rs.getInt("ID"));
                    funcion.setTituloPelicula(rs.getString("TituloPelicula"));
                    funcion.setSalaId(rs.getInt("SalaID"));
                    funcion.setFechaHora(rs.getObject("FechaHora", LocalDateTime.class));
                    funcion.setPrecioBase(rs.getDouble("PrecioBase"));
                    funcion.setEstado(rs.getString("Estado"));
                    funcion.setFormato(rs.getString("Formato"));
                    funciones.add(funcion);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return funciones;
    }

    @Override
    public List<Pelicula> obtenerPeliculasDisponibles() {
        List<Pelicula> peliculas = new ArrayList<>();
        String sql = "CALL ObtenerPeliculasDisponibles()";

        try (Connection conn = this.Conectar();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Pelicula pelicula = new Pelicula();
                pelicula.setId(rs.getInt("ID"));
                pelicula.setTitulo(rs.getString("Titulo"));
                pelicula.setGenero(rs.getString("Genero"));
                pelicula.setIdiomaOriginal(rs.getString("IdiomaOriginal"));
                pelicula.setDuracionMinutos(rs.getInt("DuracionMinutos"));
                pelicula.setClasificacion(rs.getString("Clasificacion"));
                peliculas.add(pelicula);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return peliculas;
    }

    @Override
    public List<Sala> obtenerSalasDisponibles() {
        List<Sala> salas = new ArrayList<>();
        String sql = "CALL ObtenerSalasDisponibles()";

        try (Connection conn = this.Conectar();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Sala sala = new Sala();
                sala.setId(rs.getInt("ID"));
                sala.setNumero(rs.getInt("Numero"));
                sala.setNombreSala(rs.getString("NombreSala"));
                sala.setDescripcionAdicional(rs.getString("DescripcionAdicional"));
                salas.add(sala);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salas;
    }
}