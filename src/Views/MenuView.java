package Views;

import Controllers.LoginController;
import Models.Usuario;
import Util.Icono;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JDesktopPane;
import javax.swing.SwingConstants;

public class MenuView extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(MenuView.class.getName());
    private JPanel contentPane;
    private JLabel userIconLabel;
    private JLabel userNameLabel;
    private JLabel titleLabel;
    private JButton btnEmpleados;
    private JButton btnNuevaVenta;
    private JButton btnEliminarVenta;
    private JButton btnClientes;
    private JButton btnProductos;
    private JButton btnPelicula;
    private JButton btnFunciones;
    private JButton btnCrearEntrada;
    private JButton btnCerrarSesion;
    private Usuario empleadoLogueado;
    private JFrame clientesFrame = null;
    private JFrame productosFrame = null;
    private JFrame empleadosFrame = null;
    private JFrame peliculaFrame = null;
    private JFrame funcionesFrame = null;

    class MainPanel extends JPanel {
        private Image backgroundImage;

        public MainPanel(String backgroundPath) {
            try {
                backgroundImage = new ImageIcon(getClass().getResource(backgroundPath)).getImage();
            } catch (Exception e) {
                LOGGER.severe("Error al cargar el fondo: " + backgroundPath + " - " + e.getMessage());
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
        private int targetWidth = 210;  // Dimensiones del nuevo logo
        private int targetHeight = 153;

        public LogoPanel(String logoPath) {
            try {
                // Cargar la imagen original
                ImageIcon originalIcon = new ImageIcon(getClass().getResource(logoPath));
                Image originalImage = originalIcon.getImage();

                // Crear una imagen escalada de alta calidad
                BufferedImage scaledImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = scaledImage.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
                g2d.dispose();

                logoImage = scaledImage;
            } catch (Exception e) {
                LOGGER.severe("Error al cargar el logo: " + logoPath + " - " + e.getMessage());
                logoImage = null;
            }
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (logoImage != null) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.drawImage(logoImage, 0, 0, targetWidth, targetHeight, this);
                g2d.dispose();
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(targetWidth, targetHeight);
        }

        @Override
        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, targetWidth, targetHeight);
            repaint();
        }
    }

    public MenuView(Usuario empleado) {
        this.empleadoLogueado = empleado;
        setTitle("Sistema Cineplanet");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        Icono.establecerIcono(this);

        contentPane = new MainPanel("/img/FondoBlancoGigante.png");
        contentPane.setLayout(null);
        setContentPane(contentPane);

        LogoPanel logoPanel = new LogoPanel("/img/Cineplanet_Icon.png");
        logoPanel.setBounds(10, 40, 210, 153); // Ajustado para las dimensiones del nuevo logo
        contentPane.add(logoPanel);

        titleLabel = new JLabel("MENÚ PRINCIPAL", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 51, 102));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(255, 255, 255, 200));
        titleLabel.setBounds(220, 120, 300, 30); // Centered where the logo was
        contentPane.add(titleLabel);

        userIconLabel = new JLabel();
        try {
            ImageIcon userIcon = new ImageIcon(getClass().getResource("/img/icono-user.png"));
            Image scaledImage = userIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            userIconLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            LOGGER.severe("Error al cargar el ícono del usuario: /img/icono-user.png - " + e.getMessage());
        }
        userIconLabel.setBounds(580, 50, 40, 40); // Adjusted to leave more space on the right
        contentPane.add(userIconLabel);

        userNameLabel = new JLabel(empleadoLogueado.getNombre(), SwingConstants.CENTER);
        userNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userNameLabel.setOpaque(true);
        userNameLabel.setBackground(new Color(255, 255, 255, 200));
        userNameLabel.setBounds(530, 95, 130, 25); // Adjusted to leave more space and ensure full name visibility
        contentPane.add(userNameLabel);

        btnEmpleados = crearBotonMenu("Empleados", false);
        btnNuevaVenta = crearBotonMenu("Nueva venta", false);
        btnEliminarVenta = crearBotonMenu("Eliminar venta", false);
        btnClientes = crearBotonMenu("Clientes", false);
        btnProductos = crearBotonMenu("Productos", false);
        btnPelicula = crearBotonMenu("Película", false);
        btnFunciones = crearBotonMenu("Funciones", false);
        btnCrearEntrada = crearBotonMenu("Crear entrada", false);
        btnCerrarSesion = crearBotonMenu("Cerrar Sesión", true);

        configurarAccesoPorRol();

        btnEmpleados.setBounds(50, 220, 180, 50);
        btnNuevaVenta.setBounds(260, 220, 180, 50);
        btnEliminarVenta.setBounds(470, 220, 180, 50);
        contentPane.add(btnEmpleados);
        contentPane.add(btnNuevaVenta);
        contentPane.add(btnEliminarVenta);

        btnClientes.setBounds(50, 290, 180, 50);
        btnProductos.setBounds(260, 290, 180, 50);
        btnPelicula.setBounds(470, 290, 180, 50);
        contentPane.add(btnClientes);
        contentPane.add(btnProductos);
        contentPane.add(btnPelicula);

        btnFunciones.setBounds(50, 360, 180, 50);
        btnCrearEntrada.setBounds(260, 360, 180, 50);
        btnCerrarSesion.setBounds(470, 360, 180, 50);
        contentPane.add(btnFunciones);
        contentPane.add(btnCrearEntrada);
        contentPane.add(btnCerrarSesion);

        btnEmpleados.addActionListener(e -> {
            if (empleadosFrame != null && empleadosFrame.isVisible()) {
                empleadosFrame.toFront();
                return;
            }
            empleadosFrame = new JFrame("Gestión de Empleados");
            empleadosFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            empleadosFrame.setSize(820, 640);
            empleadosFrame.setLocationRelativeTo(null);
            empleadosFrame.setResizable(false);
            Icono.establecerIcono(empleadosFrame);
            JDesktopPane desktopPane = new JDesktopPane();
            empleadosFrame.setContentPane(desktopPane);
            new EmpleadosView(desktopPane, empleadosFrame).setVisible(true);
            empleadosFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    empleadosFrame = null;
                }
            });
            empleadosFrame.setVisible(true);
        });

        btnClientes.addActionListener(e -> {
            if (clientesFrame != null && clientesFrame.isVisible()) {
                clientesFrame.toFront();
                return;
            }
            clientesFrame = new JFrame("Gestión de Clientes");
            clientesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            clientesFrame.setSize(820, 640);
            clientesFrame.setLocationRelativeTo(null);
            clientesFrame.setResizable(false);
            Icono.establecerIcono(clientesFrame);
            JDesktopPane desktopPane = new JDesktopPane();
            clientesFrame.setContentPane(desktopPane);
            new ClienteView(desktopPane, clientesFrame).setVisible(true);
            clientesFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    clientesFrame = null;
                }
            });
            clientesFrame.setVisible(true);
        });

        btnProductos.addActionListener(e -> {
            if (productosFrame != null && productosFrame.isVisible()) {
                productosFrame.toFront();
                return;
            }
            productosFrame = new JFrame("Gestión de Productos");
            productosFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            productosFrame.setSize(820, 640);
            productosFrame.setLocationRelativeTo(null);
            productosFrame.setResizable(false);
            Icono.establecerIcono(productosFrame);
            JDesktopPane desktopPane = new JDesktopPane();
            productosFrame.setContentPane(desktopPane);
            new ProductoView(desktopPane, productosFrame).setVisible(true);
            productosFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    productosFrame = null;
                }
            });
            productosFrame.setVisible(true);
        });

        btnPelicula.addActionListener(e -> {
            if (peliculaFrame != null && peliculaFrame.isVisible()) {
                peliculaFrame.toFront();
                return;
            }
            peliculaFrame = new JFrame("Gestión de Películas");
            peliculaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            peliculaFrame.setSize(820, 640);
            peliculaFrame.setLocationRelativeTo(null);
            peliculaFrame.setResizable(false);
            Icono.establecerIcono(peliculaFrame);
            JDesktopPane desktopPane = new JDesktopPane();
            peliculaFrame.setContentPane(desktopPane);
            new PeliculaView(desktopPane, peliculaFrame).setVisible(true);
            peliculaFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    peliculaFrame = null;
                }
            });
            peliculaFrame.setVisible(true);
        });

        btnFunciones.addActionListener(e -> {
            if (funcionesFrame != null && funcionesFrame.isVisible()) {
                funcionesFrame.toFront();
                return;
            }
            funcionesFrame = new JFrame("Gestión de Funciones");
            funcionesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            funcionesFrame.setSize(820, 640);
            funcionesFrame.setLocationRelativeTo(null);
            funcionesFrame.setResizable(false);
            Icono.establecerIcono(funcionesFrame);
            JDesktopPane desktopPane = new JDesktopPane();
            funcionesFrame.setContentPane(desktopPane);
            new FuncionesView(desktopPane, funcionesFrame).setVisible(true);
            funcionesFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    funcionesFrame = null;
                }
            });
            funcionesFrame.setVisible(true);
        });

        btnCerrarSesion.addActionListener(e -> {
            LoginView loginView = new LoginView();
            new LoginController(loginView);
            loginView.setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void configurarAccesoPorRol() {
        btnNuevaVenta.setVisible(true);
        btnClientes.setVisible(true);
        btnProductos.setVisible(true);
        btnPelicula.setVisible(true);
        btnFunciones.setVisible(true);
        btnCrearEntrada.setVisible(true);
        btnCerrarSesion.setVisible(true);

        btnEmpleados.setVisible(empleadoLogueado.esAdmin());
        btnEliminarVenta.setVisible(empleadoLogueado.esAdmin());
    }

    private JButton crearBotonMenu(String texto, boolean esCerrarSesion) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(getBackground());
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            public boolean isContentAreaFilled() {
                return false;
            }
        };

        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setPreferredSize(new Dimension(180, 50));
        boton.setForeground(Color.WHITE);
        boton.setOpaque(true);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(esCerrarSesion ? new Color(139, 0, 0) : new Color(30, 80, 130)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        if (esCerrarSesion) {
            boton.setBackground(new Color(255, 102, 102));
            boton.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    boton.setBackground(new Color(255, 51, 51));
                    boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    boton.setBackground(new Color(255, 102, 102));
                }
            });
        } else {
            boton.setBackground(new Color(70, 130, 180));
            boton.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    boton.setBackground(new Color(100, 149, 237));
                    boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    boton.setBackground(new Color(70, 130, 180));
                }
            });
        }

        return boton;
    }

    public void actualizarInfoUsuario(Usuario empleado) {
        this.empleadoLogueado = empleado;
        userNameLabel.setText(empleadoLogueado.getNombre());
    }

    public Usuario getEmpleadoLogueado() {
        return empleadoLogueado;
    }
}