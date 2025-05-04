package Views;

import Controllers.CRUDFuncion;
import Models.Funcion;
import Models.Pelicula;
import Models.Sala;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

public class RegistroFuncionView extends JInternalFrame {
    private JPanel mainPanel;
    private CRUDFuncion controlador;
    private Funcion funcion;
    private boolean esActualizar;
    private JTable tablaPeliculas, tablaSalas;
    private DefaultTableModel modeloPeliculas, modeloSalas;
    private JTextField txtFechaHora, txtPrecioBase;
    private JComboBox<String> cbxEstado, cbxFormato;
    private JButton btnGuardar, btnCancelar;
    private LogoPanel logoPanel;
    private JFrame parentFrame;
    private JDesktopPane desktop;

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

    public RegistroFuncionView(CRUDFuncion controlador, Funcion funcion, boolean esActualizar, JDesktopPane desktop, JFrame parent) {
        super("", true, true, true, true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setBorder(null);
        putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        this.controlador = controlador;
        this.funcion = funcion != null ? new Funcion(funcion.getId(), funcion.getPeliculaId(), funcion.getSalaId(), 
            funcion.getFechaHora(), funcion.getPrecioBase(), funcion.getEstado(), funcion.getFormato(), funcion.getTituloPelicula()) : null;
        this.esActualizar = esActualizar;
        this.desktop = desktop;
        this.parentFrame = parent;

        mainPanel = new MainPanel("/img/FondoBlancoGigante.png");
        mainPanel.setLayout(null);
        setContentPane(mainPanel);

        logoPanel = new LogoPanel("/img/CineplanetLogoV2.png");
        logoPanel.setBounds(270, 20, 260, 130);
        mainPanel.add(logoPanel);

        // Tabla de Películas
        JLabel lblPelicula = new JLabel("Seleccionar Película:");
        lblPelicula.setBounds(50, 160, 150, 25);
        lblPelicula.setOpaque(true);
        lblPelicula.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblPelicula);

        String[] columnasPeliculas = {"ID", "Título", "Género", "Idioma", "Duración", "Clasificación"};
        modeloPeliculas = new DefaultTableModel(columnasPeliculas, 0);
        tablaPeliculas = new JTable(modeloPeliculas);
        tablaPeliculas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPeliculas = new JScrollPane(tablaPeliculas);
        scrollPeliculas.setBounds(50, 190, 700, 100);
        mainPanel.add(scrollPeliculas);

        // Tabla de Salas
        JLabel lblSala = new JLabel("Seleccionar Sala:");
        lblSala.setBounds(50, 300, 150, 25);
        lblSala.setOpaque(true);
        lblSala.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblSala);

        String[] columnasSalas = {"ID", "Número", "Nombre Sala", "Descripción"};
        modeloSalas = new DefaultTableModel(columnasSalas, 0);
        tablaSalas = new JTable(modeloSalas);
        tablaSalas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollSalas = new JScrollPane(tablaSalas);
        scrollSalas.setBounds(50, 330, 700, 100);
        mainPanel.add(scrollSalas);

        // Campos adicionales
        JLabel lblFechaHora = new JLabel("Fecha y Hora (yyyy-MM-dd HH:mm):");
        lblFechaHora.setBounds(50, 440, 200, 25);
        lblFechaHora.setOpaque(true);
        lblFechaHora.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblFechaHora);

        txtFechaHora = new JTextField();
        txtFechaHora.setBounds(250, 440, 200, 25);
        txtFechaHora.setOpaque(true);
        txtFechaHora.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtFechaHora);

        JLabel lblPrecioBase = new JLabel("Precio Base:");
        lblPrecioBase.setBounds(50, 470, 100, 25);
        lblPrecioBase.setOpaque(true);
        lblPrecioBase.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblPrecioBase);

        txtPrecioBase = new JTextField();
        txtPrecioBase.setBounds(150, 470, 100, 25);
        txtPrecioBase.setOpaque(true);
        txtPrecioBase.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtPrecioBase);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(50, 500, 100, 25);
        lblEstado.setOpaque(true);
        lblEstado.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblEstado);

        String[] estados = {"Programada", "Cancelada", "Finalizada"};
        cbxEstado = new JComboBox<>(estados);
        cbxEstado.setBounds(150, 500, 150, 25);
        cbxEstado.setOpaque(true);
        cbxEstado.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(cbxEstado);

        JLabel lblFormato = new JLabel("Formato:");
        lblFormato.setBounds(350, 500, 100, 25);
        lblFormato.setOpaque(true);
        lblFormato.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblFormato);

        String[] formatos = {"2D", "3D", "IMAX", "Doblada", "Subtitulada"};
        cbxFormato = new JComboBox<>(formatos);
        cbxFormato.setBounds(450, 500, 150, 25);
        cbxFormato.setOpaque(true);
        cbxFormato.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(cbxFormato);

        // Botones
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(400, 530, 100, 30);
        btnGuardar.setBackground(new Color(0, 123, 255));
        btnGuardar.setFocusPainted(false);
        btnGuardar.addActionListener(e -> guardarFuncion());
        mainPanel.add(btnGuardar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(510, 530, 100, 30);
        btnCancelar.setBackground(new Color(0, 123, 255));
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> dispose());
        mainPanel.add(btnCancelar);

        if (esActualizar) {
            cargarDatosFuncion();
        }
        cargarPeliculasYSalas();

        desktop.add(this);
        setVisible(true);
    }

    private void cargarPeliculasYSalas() {
        modeloPeliculas.setRowCount(0);
        for (Pelicula pelicula : controlador.obtenerPeliculasDisponibles()) {
            modeloPeliculas.addRow(new Object[]{
                pelicula.getId(),
                pelicula.getTitulo(),
                pelicula.getGenero(),
                pelicula.getIdiomaOriginal(),
                pelicula.getDuracionMinutos(),
                pelicula.getClasificacion()
            });
        }

        modeloSalas.setRowCount(0);
        for (Sala sala : controlador.obtenerSalasDisponibles()) {
            modeloSalas.addRow(new Object[]{
                sala.getId(),
                sala.getNumero(),
                sala.getNombreSala(),
                sala.getDescripcionAdicional()
            });
        }
    }

    private void cargarDatosFuncion() {
        if (funcion != null) {
            txtFechaHora.setText(funcion.getFechaHora().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            txtPrecioBase.setText(String.valueOf(funcion.getPrecioBase()));
            cbxEstado.setSelectedItem(funcion.getEstado());
            cbxFormato.setSelectedItem(funcion.getFormato());

            // Seleccionar fila en las tablas
            for (int i = 0; i < modeloPeliculas.getRowCount(); i++) {
                if ((int) modeloPeliculas.getValueAt(i, 0) == funcion.getPeliculaId()) {
                    tablaPeliculas.setRowSelectionInterval(i, i);
                    break;
                }
            }
            for (int i = 0; i < modeloSalas.getRowCount(); i++) {
                if ((int) modeloSalas.getValueAt(i, 0) == funcion.getSalaId()) {
                    tablaSalas.setRowSelectionInterval(i, i);
                    break;
                }
            }
        }
    }

    private void guardarFuncion() {
        try {
            int peliculaId = (int) modeloPeliculas.getValueAt(tablaPeliculas.getSelectedRow(), 0);
            int salaId = (int) modeloSalas.getValueAt(tablaSalas.getSelectedRow(), 0);
            LocalDateTime fechaHora = LocalDateTime.parse(txtFechaHora.getText().trim(), 
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            double precioBase = Double.parseDouble(txtPrecioBase.getText().trim());
            String estado = (String) cbxEstado.getSelectedItem();
            String formato = (String) cbxFormato.getSelectedItem();

            Funcion nuevaFuncion = new Funcion();
            nuevaFuncion.setPeliculaId(peliculaId);
            nuevaFuncion.setSalaId(salaId);
            nuevaFuncion.setFechaHora(fechaHora);
            nuevaFuncion.setPrecioBase(precioBase);
            nuevaFuncion.setEstado(estado);
            nuevaFuncion.setFormato(formato);

            if (esActualizar && funcion != null) {
                nuevaFuncion.setId(funcion.getId());
                controlador.actualizar(nuevaFuncion);
                JOptionPane.showMessageDialog(this, "Función actualizada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                controlador.registrar(nuevaFuncion);
                JOptionPane.showMessageDialog(this, "Función registrada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Verifique el formato de fecha, hora o precio.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Seleccione una película y una sala.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar la función: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}