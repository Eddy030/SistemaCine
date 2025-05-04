package Controllers;

import Models.Usuario;
import Views.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {

    private final LoginView loginView;
    private final UsuarioController usuarioController;
    private Usuario usuarioLogueado;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        this.usuarioController = new UsuarioController();

        loginView.addLoginListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginView.getUsername();
                String password = loginView.getPassword();

                usuarioLogueado = usuarioController.login(username, password);

                if (usuarioLogueado != null) {
                    loginView.redirigirAlMenu(usuarioLogueado);
                } else {
                    loginView.mostrarMensaje("Credenciales incorrectas");
                }
            }
        });
    }

    public void mostrarLogin() {
        loginView.setVisible(true);
    }
}