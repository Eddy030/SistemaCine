package Models;

public class Usuario {
    private int id;
    private String usuario;
    private String contraseña;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String fechaContratacion;
    private String direccion;
    private int rolId;

    // Constructor vacío
    public Usuario() {
    }

    // Constructor para el registro (sin ID y RolID, ya que se generan automáticamente o se asignan)
    public Usuario(String usuario, String contraseña, String nombre, String apellido, String email, String telefono, String direccion) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // Constructor completo (incluyendo ID y RolID para cuando se recupera de la BD)
    public Usuario(int id, String usuario, String contraseña, String nombre, String apellido, String email, String telefono, String fechaContratacion, String direccion, int rolId) {
        this.id = id;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.fechaContratacion = fechaContratacion;
        this.direccion = direccion;
        this.rolId = rolId;
    }

    // Getters y Setters para todos los campos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(String fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getRolId() {
        return rolId;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
    }
    
    /**
    * Verifica si el usuario tiene rol de administrador
    * @return true si es administrador (rolId = 2 según la BD)
    */
    public boolean esAdmin() {
        return this.rolId == 2; // El ID 2 corresponde a Administrador en la BD
    }
    
    /**
     * Verifica si el usuario tiene rol de empleado
     * @return true si es empleado (rolId = 1 según la BD)
     */
    public boolean esEmpleado() {
        return this.rolId == 1; // El ID 1 corresponde a Empleado en la BD
    }
}