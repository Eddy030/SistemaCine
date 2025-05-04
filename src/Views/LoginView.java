package Views;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Util.Icono;
import Models.Usuario;

public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegistro;
    private JPanel mainPanel;
    private LogoPanel logoPanel;

    // Panel principal con fondo
    class MainPanel extends JPanel {
        private Image backgroundImage;

        public MainPanel(String backgroundPath) {
            try {
                backgroundImage = new ImageIcon(getClass().getResource(backgroundPath)).getImage();
            } catch (Exception e) {
                System.err.println("Error al cargar el fondo: " + backgroundPath);
                e.printStackTrace();
                backgroundImage = null;
            }
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                setBackground(new Color(220, 220, 220));
            }
        }
    }

    class LogoPanel extends JPanel {
        private Image logoImage;

        public LogoPanel(String logoPath) {
            try {
                logoImage = new ImageIcon(getClass().getResource(logoPath)).getImage();
            } catch (Exception e) {
                System.err.println("Error al cargar el logo: " + logoPath);
                e.printStackTrace();
                logoImage = null;
            }
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (logoImage != null) {
                int imgWidth = logoImage.getWidth(this);
                int imgHeight = logoImage.getHeight(this);
                int panelWidth = getWidth();
                int panelHeight = getHeight();

                double scale = Math.min((double) panelWidth / imgWidth, (double) panelHeight / imgHeight);
                int scaledWidth = (int) (imgWidth * scale);
                int scaledHeight = (int) (imgHeight * scale);

                int x = (panelWidth - scaledWidth) / 2;
                int y = (panelHeight - scaledHeight) / 2;

                g.drawImage(logoImage, x, y, scaledWidth, scaledHeight, this);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            if (logoImage != null) {
                return new Dimension(logoImage.getWidth(this), logoImage.getHeight(this));
            } else {
                return new Dimension(150, 50);
            }
        }

        @Override
        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, width, height);
            repaint();
        }
    }

    public LoginView() {
        setTitle("Sistema de Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        Icono.establecerIcono(this);

        mainPanel = new MainPanel("/img/FondoBlancoGigante.png");
        mainPanel.setLayout(null);
        setContentPane(mainPanel);
        setSize(430, 440);
        setLocationRelativeTo(null);

        logoPanel = new LogoPanel("/img/CineplanetLogoV2.png");
        logoPanel.setBounds(85, 40, 260, 130);
        mainPanel.add(logoPanel);

        JLabel lblUsername = new JLabel("Usuario:");
        lblUsername.setBounds(60, 180, 120, 25);
        lblUsername.setOpaque(true);
        lblUsername.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblUsername);
        txtUsername = new JTextField(15);
        txtUsername.setBounds(150, 180, 200, 25);
        txtUsername.setOpaque(true);
        txtUsername.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtUsername);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(60, 220, 120, 25);
        lblPassword.setOpaque(true);
        lblPassword.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblPassword);
        txtPassword = new JPasswordField(15);
        txtPassword.setBounds(150, 220, 200, 25);
        txtPassword.setOpaque(true);
        txtPassword.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtPassword);

        btnLogin = new JButton("INGRESAR");
        btnLogin.setBounds(150, 270, 100, 30);
        btnLogin.setBackground(new Color(0, 123, 255));
        btnLogin.setFocusPainted(false);
        mainPanel.add(btnLogin);

        btnRegistro = new JButton("REGISTRARSE");
        btnRegistro.setBounds(150, 310, 120, 30);
        btnRegistro.setBackground(new Color(0, 123, 255));
        btnRegistro.setFocusPainted(false);
        mainPanel.add(btnRegistro);

        btnRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistroView registroView = new RegistroView();
                registroView.setVisible(true);
                dispose();
            }
        });

        setVisible(true);
    }

    public String getUsername() {
        return txtUsername.getText();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword());
    }

    public void addLoginListener(ActionListener listener) {
        btnLogin.addActionListener(listener);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void redirigirAlMenu(Usuario usuario) {
        this.setVisible(false);
        MenuView menu = new MenuView(usuario); // Pasar el usuario
        menu.setVisible(true);
    }
}