package Controllers;

import Models.Usuario;

public class RegistroController {

    private final CRUDEmpleado empleadoController;

    public RegistroController() {
        this.empleadoController = new CRUDEmpleado();
    }

    public boolean registrar(Usuario usuario) {
        // Validaciones mínimas, confiando en los filtros de RegistroView y CRUDEmpleado
        if (usuario.getUsuario() == null || usuario.getUsuario().trim().isEmpty()) {
            return false;
        }
        if (usuario.getContraseña() == null || usuario.getContraseña().trim().isEmpty()) {
            return false;
        }
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            return false;
        }
        if (usuario.getApellido() == null || usuario.getApellido().trim().isEmpty()) {
            return false;
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            return false;
        }
        if (usuario.getTelefono() == null || usuario.getTelefono().trim().isEmpty()) {
            return false;
        }
        if (usuario.getDireccion() == null || usuario.getDireccion().trim().isEmpty()) {
            return false;
        }

        return empleadoController.registrar(usuario);
    }
}