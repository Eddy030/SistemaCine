package App;

import Controllers.LoginController;
import Views.LoginView;

public class AppCine {
    public static void main(String[] args) {
        // Crear la vista de Login
        LoginView loginView = new LoginView();
        
        // Crear el controlador y pasar la vista
        LoginController loginController = new LoginController(loginView);
        
        // Mostrar la vista de Login
        loginController.mostrarLogin();
    }
}

