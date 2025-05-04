package Views;

import Controllers.CRUDEmpleado;
import Controllers.LoginController;
import Models.Usuario;
import Util.Icono;
import Util.TextFieldValidator;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.text.AbstractDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegistroView extends JFrame {
    private JTextField txtUsuario, txtNombre, txtApellido, txtEmail, txtTelefono, txtFechaContratacion, txtDireccion;
    private JPasswordField txtContraseña;
    private JButton btnRegistrar, btnVolver;
    private JPanel mainPanel;
    private LogoPanel logoPanel;
    private CRUDEmpleado empleadoController;
    private boolean registroExitoso;
    private String context;

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

    public RegistroView() {
        this("login", null);
    }

    public RegistroView(String context, Usuario empleado) {
        this.context = context;
        setTitle("Registro de Empleado");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(450, 600);
        setLocationRelativeTo(null);
        registroExitoso = false;
        empleadoController = new CRUDEmpleado();

        Icono.establecerIcono(this);

        mainPanel = new MainPanel("/img/FondoBlancoGigante.png");
        mainPanel.setLayout(null);
        setContentPane(mainPanel);

        logoPanel = new LogoPanel("/img/CineplanetLogoV2.png");
        logoPanel.setBounds(95, 20, 260, 130);
        mainPanel.add(logoPanel);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(50, 160, 120, 25);
        lblUsuario.setOpaque(true);
        lblUsuario.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblUsuario);
        txtUsuario = new JTextField();
        txtUsuario.setBounds(180, 160, 200, 25);
        txtUsuario.setOpaque(true);
        txtUsuario.setBackground(new Color(255, 255, 255, 230));
        ((AbstractDocument) txtUsuario.getDocument()).setDocumentFilter(TextFieldValidator.getUsernameFilter());
        mainPanel.add(txtUsuario);

        JLabel lblContraseña = new JLabel("Contraseña:");
        lblContraseña.setBounds(50, 200, 120, 25);
        lblContraseña.setOpaque(true);
        lblContraseña.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblContraseña);
        txtContraseña = new JPasswordField();
        txtContraseña.setBounds(180, 200, 200, 25);
        txtContraseña.setOpaque(true);
        txtContraseña.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtContraseña);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(50, 240, 120, 25);
        lblNombre.setOpaque(true);
        lblNombre.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblNombre);
        txtNombre = new JTextField();
        txtNombre.setBounds(180, 240, 200, 25);
        txtNombre.setOpaque(true);
        txtNombre.setBackground(new Color(255, 255, 255, 230));
        ((AbstractDocument) txtNombre.getDocument()).setDocumentFilter(TextFieldValidator.getSoloTextoFilter());
        mainPanel.add(txtNombre);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setBounds(50, 280, 120, 25);
        lblApellido.setOpaque(true);
        lblApellido.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblApellido);
        txtApellido = new JTextField();
        txtApellido.setBounds(180, 280, 200, 25);
        txtApellido.setOpaque(true);
        txtApellido.setBackground(new Color(255, 255, 255, 230));
        ((AbstractDocument) txtApellido.getDocument()).setDocumentFilter(TextFieldValidator.getSoloTextoFilter());
        mainPanel.add(txtApellido);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(50, 320, 120, 25);
        lblEmail.setOpaque(true);
        lblEmail.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblEmail);
        txtEmail = new JTextField();
        txtEmail.setBounds(180, 320, 200, 25);
        txtEmail.setOpaque(true);
        txtEmail.setBackground(new Color(255, 255, 255, 230));
        ((AbstractDocument) txtEmail.getDocument()).setDocumentFilter(TextFieldValidator.getEmailFilter());
        mainPanel.add(txtEmail);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(50, 360, 120, 25);
        lblTelefono.setOpaque(true);
        lblTelefono.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblTelefono);
        txtTelefono = new JTextField();
        txtTelefono.setBounds(180, 360, 200, 25);
        txtTelefono.setOpaque(true);
        txtTelefono.setBackground(new Color(255, 255, 255, 230));
        ((AbstractDocument) txtTelefono.getDocument()).setDocumentFilter(TextFieldValidator.getSoloNumerosFilter());
        mainPanel.add(txtTelefono);

        JLabel lblFechaContratacion = new JLabel("Fecha de Contratación:");
        lblFechaContratacion.setBounds(50, 400, 150, 25);
        lblFechaContratacion.setOpaque(true);
        lblFechaContratacion.setBackground(new Color(255, 255, 255, 200));
        lblFechaContratacion.setVisible(context.equals("empleados"));
        mainPanel.add(lblFechaContratacion);
        txtFechaContratacion = new JTextField("YYYY-MM-DD");
        txtFechaContratacion.setBounds(200, 400, 180, 25);
        txtFechaContratacion.setOpaque(true);
        txtFechaContratacion.setBackground(new Color(255, 255, 255, 230));
        txtFechaContratacion.setVisible(context.equals("empleados"));
        mainPanel.add(txtFechaContratacion);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(50, context.equals("empleados") ? 440 : 400, 120, 25);
        lblDireccion.setOpaque(true);
        lblDireccion.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblDireccion);
        txtDireccion = new JTextField();
        txtDireccion.setBounds(180, context.equals("empleados") ? 440 : 400, 200, 25);
        txtDireccion.setOpaque(true);
        txtDireccion.setBackground(new Color(255, 255, 255, 230));
        ((AbstractDocument) txtDireccion.getDocument()).setDocumentFilter(TextFieldValidator.getSoloTextoFilter());
        mainPanel.add(txtDireccion);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(70, context.equals("empleados") ? 490 : 450, 130, 30);
        btnRegistrar.setBackground(new Color(0, 123, 255));
        btnRegistrar.setFocusPainted(false);
        mainPanel.add(btnRegistrar);

        btnVolver = new JButton(context.equals("empleados") ? "Cancelar" : "Volver");
        btnVolver.setBounds(230, context.equals("empleados") ? 490 : 450, 130, 30);
        btnVolver.setBackground(new Color(0, 123, 255));
        btnVolver.setFocusPainted(false);
        mainPanel.add(btnVolver);

        // Prellenar campos si se proporciona un objeto Usuario
        if (empleado != null) {
            txtNombre.setText(empleado.getNombre());
            txtApellido.setText(empleado.getApellido());
            txtEmail.setText(empleado.getEmail());
            txtTelefono.setText(empleado.getTelefono());
            txtFechaContratacion.setText(empleado.getFechaContratacion() != null && !empleado.getFechaContratacion().isEmpty() ? empleado.getFechaContratacion() : "YYYY-MM-DD");
            txtDireccion.setText(empleado.getDireccion());
        }

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });

        btnVolver.addActionListener(e -> {
            if (context.equals("login")) {
                LoginView login = new LoginView();
                new LoginController(login);
                login.setVisible(true);
            }
            dispose();
        });
    }

    private void registrarUsuario() {
        try {
            String usuario = txtUsuario.getText().trim();
            String contraseña = new String(txtContraseña.getPassword()).trim();
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String email = txtEmail.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String fechaContratacion = txtFechaContratacion.getText().trim();
            String direccion = txtDireccion.getText().trim();

            // Validaciones
            if (usuario.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El usuario no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (contraseña.length() < 8) {
                JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 8 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (apellido.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El apellido no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!email.isEmpty() && !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                JOptionPane.showMessageDialog(this, "El email no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!telefono.isEmpty() && !telefono.matches("\\+?[0-9]{8,15}")) {
                JOptionPane.showMessageDialog(this, "El teléfono debe tener entre 8 y 15 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (context.equals("empleados") && !fechaContratacion.equals("YYYY-MM-DD") && !fechaContratacion.matches("\\d{4}-\\d{2}-\\d{2}")) {
                JOptionPane.showMessageDialog(this, "La fecha de contratación debe tener el formato YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setUsuario(usuario);
            nuevoUsuario.setContraseña(contraseña);
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setApellido(apellido);
            nuevoUsuario.setEmail(email);
            nuevoUsuario.setTelefono(telefono);
            nuevoUsuario.setFechaContratacion(context.equals("empleados") && !fechaContratacion.equals("YYYY-MM-DD") ? fechaContratacion : "");
            nuevoUsuario.setDireccion(direccion);
            nuevoUsuario.setRolId(1); // Siempre empleado

            if (empleadoController.registrar(nuevoUsuario)) {
                registroExitoso = true;
                JOptionPane.showMessageDialog(this, "Empleado registrado exitosamente");
                if (context.equals("login")) {
                    limpiarFormulario();
                    LoginView login = new LoginView();
                    new LoginController(login);
                    login.setVisible(true);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar el empleado. Verifique que el usuario o email no estén registrados.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar el empleado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isRegistroExitoso() {
        return registroExitoso;
    }

    private void limpiarFormulario() {
        txtUsuario.setText("");
        txtContraseña.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        txtFechaContratacion.setText("YYYY-MM-DD");
        txtDireccion.setText("");
    }
}