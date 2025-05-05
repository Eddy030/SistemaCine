package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {

    private static final String CONTROLADOR = "com.mysql.cj.jdbc.Driver";

    private static final String URL = "jdbc:mysql://localhost:3306/CineBD?useSSL=false&serverTimezone=UTC";

    /* Manzanita's config */
    // private static final String USER = "Manzana";
    // private static final String PASSWORD = "Eddye030@";
    
    /* Lavender's config */
    private static final String USER = "root";
    private static final String PASSWORD = "Lavendernara_1";

    /* Andriy's config */
    // private static final String URL = "jdbc:mysql://localhost:3307/CineBD?serverTimezone=UTC";
    // private static final String USER = "root";
    // private static final String PASSWORD = "lioymiku123";
    static {
        try {
            Class.forName(CONTROLADOR);
            System.out.println("Controlador JDBC cargado correctamente");
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el controlador: " + e.getMessage());
            throw new RuntimeException("No se pudo cargar el controlador JDBC", e);
        }
    }

    public Connection Conectar() {
        Connection cnx = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión establecida con éxito");
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            throw new RuntimeException("Error en la conexión a la BD", e);
        }
        return cnx;
    }

    // Método adicional para probar la conexión
    public static void probarConexion() {
        ConexionMySQL conexion = new ConexionMySQL();
        try (Connection cnx = conexion.Conectar()) {
            System.out.println("¡Conexión exitosa! La base de datos está accesible");
        } catch (SQLException e) {
            System.err.println("Error al probar la conexión: " + e.getMessage());
        }
    }
}
