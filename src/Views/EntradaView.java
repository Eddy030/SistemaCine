package Views;

import Controllers.CRUDEntrada;
import Models.EntradaDetallada;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
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

public class EntradaView extends JInternalFrame {

    private final JPanel mainPanel;
    private final CRUDEntrada controlador;
    private final JTable tablaEntradas;
    private final DefaultTableModel modeloTabla;
    private final JButton btnAgregar, btnActualizar, btnEliminar, btnVolver, btnBuscar;
    private final LogoPanel logoPanel;
    private JFrame parentFrame;
    private final JDesktopPane desktop;
    private final JTextField txtBuscar;

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

    public EntradaView(JDesktopPane desktop, JFrame parent) {
        super("", true, true, true, true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setBorder(null);
        putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        this.parentFrame = parent;
        this.desktop = desktop;
        this.controlador = new CRUDEntrada();

        mainPanel = new MainPanel("/img/FondoBlancoGigante.png");
        mainPanel.setLayout(null);
        setContentPane(mainPanel);

        logoPanel = new LogoPanel("/img/CineplanetLogoV2.png");
        logoPanel.setBounds(270, 20, 260, 130);
        mainPanel.add(logoPanel);

        // Campo de búsqueda
        JLabel lblBuscar = new JLabel("Buscar (ID):");
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
        btnBuscar.addActionListener(e -> buscarEntradas());
        mainPanel.add(btnBuscar);

        // Tabla
        String[] columnas = {"ID", "Película", "Cliente", "Precio Categoría", "Fila", "Asiento", "Fecha Venta", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaEntradas = new JTable(modeloTabla);
        tablaEntradas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaEntradas);
        scrollPane.setBounds(50, 200, 700, 150);
        mainPanel.add(scrollPane);

        // Botones
        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(400, 390, 100, 30);
        btnAgregar.setBackground(new Color(0, 123, 255));
        btnAgregar.setFocusPainted(false);
        btnAgregar.addActionListener(e -> abrirRegistroEntrada(false));
        mainPanel.add(btnAgregar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(510, 390, 100, 30);
        btnActualizar.setBackground(new Color(0, 123, 255));
        btnActualizar.setFocusPainted(false);
        btnActualizar.addActionListener(e -> abrirRegistroEntrada(true));
        mainPanel.add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(620, 390, 100, 30);
        btnEliminar.setBackground(new Color(255, 102, 102));
        btnEliminar.setFocusPainted(false);
        btnEliminar.addActionListener(e -> eliminarEntrada());
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
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (EntradaDetallada entrada : controlador.obtenerTodaslasentradas()) {
            Object[] fila = {
                entrada.getId(),
                entrada.getPelicula(),
                entrada.getCliente(),
                entrada.getPrecioCategoria(),
                entrada.getNumeroFila(),
                entrada.getNumeroAsiento(),
                df.format(entrada.getFechaVenta()),
                entrada.getEstado()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void buscarEntradas() {
        String criterio = txtBuscar.getText().trim();
        if (criterio.isEmpty()) {
            cargarDatos();
            return;
        }

        try {
            int id = Integer.parseInt(criterio);
            modeloTabla.setRowCount(0);
            EntradaDetallada entrada = controlador.obtenerPorId(id);
            if (entrada != null) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Object[] fila = {
                    entrada.getId(),
                    entrada.getPelicula(),
                    entrada.getCliente(),
                    entrada.getPrecioCategoria(),
                    entrada.getNumeroFila(),
                    entrada.getNumeroAsiento(),
                    df.format(entrada.getFechaVenta()),
                    entrada.getEstado()
                };
                modeloTabla.addRow(fila);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró la entrada con ID: " + id, "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirRegistroEntrada(boolean esActualizar) {
        if (esActualizar) {
            int fila = tablaEntradas.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una entrada para actualizar", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int id = (int) modeloTabla.getValueAt(fila, 0);
            EntradaDetallada entrada = controlador.obtenerPorId(id);
            RegistroEntradaView registroView = new RegistroEntradaView(controlador, entrada, true, desktop, parentFrame);
            registroView.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
                @Override
                public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
                    cargarDatos();
                }
            });
        } else {
            RegistroEntradaView registroView = new RegistroEntradaView(controlador, null, false, desktop, parentFrame);
            registroView.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
                @Override
                public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
                    cargarDatos();
                }
            });
        }
    }

    private void eliminarEntrada() {
        int fila = tablaEntradas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una entrada para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        try {
            controlador.eliminar(id);
            JOptionPane.showMessageDialog(this, "Entrada eliminada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarDatos();
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
