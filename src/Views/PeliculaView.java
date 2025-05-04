package Views;

import Controllers.CRUDPelicula;
import Controllers.IPelicula;
import Models.Pelicula;
import Util.Icono;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class PeliculaView extends JInternalFrame {
    private JPanel mainPanel;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tablaPeliculas;
    private DefaultTableModel tableModel;
    private JTextField txtId;
    private JTextField txtTitulo;
    private JTextField txtGenero;
    private JTextField txtIdiomaOriginal;
    private JTextField txtDuracionMinutos;
    private JTextField txtClasificacion;
    private JButton btnAgregar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnVolver;
    private IPelicula peliculaController;
    private LogoPanel logoPanel;
    private JFrame parentFrame;

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

    public PeliculaView(JDesktopPane desktop, JFrame parent) {
        super("", true, true, true, true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setBorder(null);
        putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        this.parentFrame = parent;

        peliculaController = new CRUDPelicula();

        mainPanel = new MainPanel("/img/FondoBlancoGigante.png");
        mainPanel.setLayout(null);
        setContentPane(mainPanel);

        logoPanel = new LogoPanel("/img/CineplanetLogoV2.png");
        logoPanel.setBounds(270, 20, 260, 130);
        mainPanel.add(logoPanel);

        JLabel lblBuscar = new JLabel("Buscar (ID o Título):");
        lblBuscar.setBounds(50, 160, 150, 25);
        lblBuscar.setOpaque(true);
        lblBuscar.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblBuscar);

        txtBuscar = new JTextField();
        txtBuscar.setBounds(200, 160, 200, 25);
        txtBuscar.setOpaque(true);
        txtBuscar.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtBuscar);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(410, 160, 100, 25);
        btnBuscar.setBackground(new Color(0, 123, 255));
        btnBuscar.setFocusPainted(false);
        mainPanel.add(btnBuscar);

        String[] columnas = {"ID", "Título", "Género", "Idioma", "Duración (min)", "Clasificación"};
        tableModel = new DefaultTableModel(columnas, 0);
        tablaPeliculas = new JTable(tableModel);
        tablaPeliculas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaPeliculas);
        scrollPane.setBounds(50, 200, 700, 150);
        mainPanel.add(scrollPane);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(50, 360, 100, 25);
        lblId.setOpaque(true);
        lblId.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblId);

        txtId = new JTextField();
        txtId.setBounds(150, 360, 100, 25);
        txtId.setOpaque(true);
        txtId.setBackground(new Color(255, 255, 255, 230));
        txtId.setEditable(false);
        mainPanel.add(txtId);

        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setBounds(50, 390, 100, 25);
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblTitulo);

        txtTitulo = new JTextField();
        txtTitulo.setBounds(150, 390, 200, 25);
        txtTitulo.setOpaque(true);
        txtTitulo.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtTitulo);

        JLabel lblGenero = new JLabel("Género:");
        lblGenero.setBounds(50, 420, 100, 25);
        lblGenero.setOpaque(true);
        lblGenero.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblGenero);

        txtGenero = new JTextField();
        txtGenero.setBounds(150, 420, 200, 25);
        txtGenero.setOpaque(true);
        txtGenero.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtGenero);

        JLabel lblIdiomaOriginal = new JLabel("Idioma:");
        lblIdiomaOriginal.setBounds(50, 450, 100, 25);
        lblIdiomaOriginal.setOpaque(true);
        lblIdiomaOriginal.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblIdiomaOriginal);

        txtIdiomaOriginal = new JTextField();
        txtIdiomaOriginal.setBounds(150, 450, 200, 25);
        txtIdiomaOriginal.setOpaque(true);
        txtIdiomaOriginal.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtIdiomaOriginal);

        JLabel lblDuracionMinutos = new JLabel("Duración (min):");
        lblDuracionMinutos.setBounds(50, 480, 100, 25);
        lblDuracionMinutos.setOpaque(true);
        lblDuracionMinutos.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblDuracionMinutos);

        txtDuracionMinutos = new JTextField();
        txtDuracionMinutos.setBounds(150, 480, 200, 25);
        txtDuracionMinutos.setOpaque(true);
        txtDuracionMinutos.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtDuracionMinutos);

        JLabel lblClasificacion = new JLabel("Clasificación:");
        lblClasificacion.setBounds(50, 510, 100, 25);
        lblClasificacion.setOpaque(true);
        lblClasificacion.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblClasificacion);

        txtClasificacion = new JTextField();
        txtClasificacion.setBounds(150, 510, 200, 25);
        txtClasificacion.setOpaque(true);
        txtClasificacion.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtClasificacion);

        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(400, 390, 100, 30);
        btnAgregar.setBackground(new Color(0, 123, 255));
        btnAgregar.setFocusPainted(false);
        mainPanel.add(btnAgregar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(510, 390, 100, 30);
        btnActualizar.setBackground(new Color(0, 123, 255));
        btnActualizar.setFocusPainted(false);
        mainPanel.add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(620, 390, 100, 30);
        btnEliminar.setBackground(new Color(255, 102, 102));
        btnEliminar.setFocusPainted(false);
        mainPanel.add(btnEliminar);

        btnVolver = new JButton("Volver");
        btnVolver.setBounds(510, 510, 100, 30);
        btnVolver.setBackground(new Color(0, 123, 255));
        btnVolver.setFocusPainted(false);
        mainPanel.add(btnVolver);

        cargarPeliculas();

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPeliculas();
            }
        });

        tablaPeliculas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaPeliculas.getSelectedRow() != -1) {
                int selectedRow = tablaPeliculas.getSelectedRow();
                txtId.setText(tableModel.getValueAt(selectedRow, 0).toString());
                txtTitulo.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtGenero.setText(tableModel.getValueAt(selectedRow, 2).toString());
                txtIdiomaOriginal.setText(tableModel.getValueAt(selectedRow, 3).toString());
                txtDuracionMinutos.setText(tableModel.getValueAt(selectedRow, 4).toString());
                txtClasificacion.setText(tableModel.getValueAt(selectedRow, 5).toString());
            }
        });

        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarPelicula();
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarPelicula();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarPelicula();
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                if (parentFrame != null) {
                    parentFrame.dispose();
                }
            }
        });

        desktop.add(this);
        setVisible(true);

        Icono.establecerIcono(this);
    }

    private void cargarPeliculas() {
        tableModel.setRowCount(0);
        List<Pelicula> peliculas = peliculaController.obtenerTodos();
        for (Pelicula pelicula : peliculas) {
            tableModel.addRow(new Object[]{
                pelicula.getId(),
                pelicula.getTitulo(),
                pelicula.getGenero(),
                pelicula.getIdiomaOriginal(),
                pelicula.getDuracionMinutos(),
                pelicula.getClasificacion()
            });
        }
    }

    private void buscarPeliculas() {
        String criterio = txtBuscar.getText().trim();
        List<Pelicula> peliculas;
        try {
            int id = Integer.parseInt(criterio);
            Pelicula pelicula = peliculaController.obtenerPorId(id);
            peliculas = pelicula != null ? List.of(pelicula) : List.of();
        } catch (NumberFormatException e) {
            peliculas = peliculaController.buscarPorTitulo(criterio);
        }

        tableModel.setRowCount(0);
        for (Pelicula pelicula : peliculas) {
            tableModel.addRow(new Object[]{
                pelicula.getId(),
                pelicula.getTitulo(),
                pelicula.getGenero(),
                pelicula.getIdiomaOriginal(),
                pelicula.getDuracionMinutos(),
                pelicula.getClasificacion()
            });
        }
    }

    private void agregarPelicula() {
        int response = JOptionPane.showConfirmDialog(
            this,
            "Para agregar una película, necesita completar un formulario. ¿Desea continuar?",
            "Confirmar Registro",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.INFORMATION_MESSAGE
        );

        if (response == JOptionPane.OK_OPTION) {
            RegistroPeliculaView registroView = new RegistroPeliculaView("add", null);
            registroView.setVisible(true);

            registroView.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    if (registroView.isRegistroExitoso()) {
                        cargarPeliculas();
                        limpiarCampos();
                    }
                }
            });
        }
    }

    private void actualizarPelicula() {
        try {
            int id = Integer.parseInt(txtId.getText());
            Pelicula pelicula = peliculaController.obtenerPorId(id);
            if (pelicula == null) {
                JOptionPane.showMessageDialog(this, "Película no encontrada.");
                return;
            }

            RegistroPeliculaView registroView = new RegistroPeliculaView("update", pelicula);
            registroView.setVisible(true);

            registroView.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    if (registroView.isRegistroExitoso()) {
                        cargarPeliculas();
                        limpiarCampos();
                    }
                }
            });
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Seleccione una película válida.");
        }
    }

    private void eliminarPelicula() {
        try {
            int id = Integer.parseInt(txtId.getText());
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar esta película?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (peliculaController.eliminar(id)) {
                    JOptionPane.showMessageDialog(this, "Película eliminada exitosamente");
                    cargarPeliculas();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar película.");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Seleccione una película para eliminar.");
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtTitulo.setText("");
        txtGenero.setText("");
        txtIdiomaOriginal.setText("");
        txtDuracionMinutos.setText("");
        txtClasificacion.setText("");
    }
}