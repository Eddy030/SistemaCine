package Views;

import Controllers.CRUDFuncion;
import Models.Funcion;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;
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

public class FuncionesView extends JInternalFrame {
    private JPanel mainPanel;
    private CRUDFuncion controlador;
    private JTable tablaFunciones;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnVolver, btnBuscar;
    private LogoPanel logoPanel;
    private JFrame parentFrame;
    private JDesktopPane desktop;
    private JTextField txtBuscar;

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

    public FuncionesView(JDesktopPane desktop, JFrame parent) {
        super("", true, true, true, true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setBorder(null);
        putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        this.parentFrame = parent;
        this.desktop = desktop;
        this.controlador = new CRUDFuncion();

        mainPanel = new MainPanel("/img/FondoBlancoGigante.png");
        mainPanel.setLayout(null);
        setContentPane(mainPanel);

        logoPanel = new LogoPanel("/img/CineplanetLogoV2.png");
        logoPanel.setBounds(270, 20, 260, 130);
        mainPanel.add(logoPanel);

        // Campo de búsqueda
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
        btnBuscar.addActionListener(e -> buscarFunciones());
        mainPanel.add(btnBuscar);

        // Tabla
        String[] columnas = {"ID", "Título de Película", "Sala", "Fecha y Hora", "Precio Base", "Estado", "Formato"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaFunciones = new JTable(modeloTabla);
        tablaFunciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaFunciones);
        scrollPane.setBounds(50, 200, 700, 150);
        mainPanel.add(scrollPane);

        // Botones
        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(400, 390, 100, 30);
        btnAgregar.setBackground(new Color(0, 123, 255));
        btnAgregar.setFocusPainted(false);
        btnAgregar.addActionListener(e -> abrirRegistroFuncion(false));
        mainPanel.add(btnAgregar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(510, 390, 100, 30);
        btnActualizar.setBackground(new Color(0, 123, 255));
        btnActualizar.setFocusPainted(false);
        btnActualizar.addActionListener(e -> abrirRegistroFuncion(true));
        mainPanel.add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(620, 390, 100, 30);
        btnEliminar.setBackground(new Color(255, 102, 102));
        btnEliminar.setFocusPainted(false);
        btnEliminar.addActionListener(e -> eliminarFuncion());
        mainPanel.add(btnEliminar);

        btnVolver = new JButton("Volver");
        btnVolver.setBounds(510, 510, 100, 30);
        btnVolver.setBackground(new Color(0, 123, 255));
        btnVolver.setFocusPainted(false);
        btnVolver.addActionListener(e -> {
            dispose();
            if (parentFrame != null) {
                parentFrame.dispose();
            }
        });
        mainPanel.add(btnVolver);

        cargarDatos();

        desktop.add(this);
        setVisible(true);
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        for (Funcion funcion : controlador.obtenerTodos()) {
            Object[] fila = {
                funcion.getId(),
                funcion.getTituloPelicula(),
                funcion.getSalaId(),
                funcion.getFechaHora().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                funcion.getPrecioBase(),
                funcion.getEstado(),
                funcion.getFormato()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void buscarFunciones() {
        String criterio = txtBuscar.getText().trim();
        String tipoBusqueda = "TITULO";
        try {
            Integer.parseInt(criterio);
            tipoBusqueda = "ID";
        } catch (NumberFormatException e) {
            // Si no es un número, buscar por título
        }

        modeloTabla.setRowCount(0);
        for (Funcion funcion : controlador.buscarFunciones(criterio, tipoBusqueda)) {
            Object[] fila = {
                funcion.getId(),
                funcion.getTituloPelicula(),
                funcion.getSalaId(),
                funcion.getFechaHora().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                funcion.getPrecioBase(),
                funcion.getEstado(),
                funcion.getFormato()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void abrirRegistroFuncion(boolean esActualizar) {
        if (esActualizar) {
            int fila = tablaFunciones.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una función para actualizar", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int id = (int) modeloTabla.getValueAt(fila, 0);
            Funcion funcion = controlador.obtenerPorId(id);
            RegistroFuncionView registroView = new RegistroFuncionView(controlador, funcion, true, desktop, parentFrame);
            registroView.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
                @Override
                public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
                    cargarDatos();
                }
            });
        } else {
            RegistroFuncionView registroView = new RegistroFuncionView(controlador, null, false, desktop, parentFrame);
            registroView.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
                @Override
                public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
                    cargarDatos();
                }
            });
        }
    }

    private void eliminarFuncion() {
        int fila = tablaFunciones.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una función para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        try {
            controlador.eliminar(id);
            JOptionPane.showMessageDialog(this, "Función eliminada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarDatos();
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}