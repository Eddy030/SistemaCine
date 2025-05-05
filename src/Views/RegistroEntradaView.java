package Views;

import Controllers.CRUDEntrada;
import Controllers.CRUDCliente;
import Controllers.CRUDPelicula;
import Controllers.CRUDPrecioEntrada;
import Models.Entrada;
import Models.EntradaDetallada;
import Models.Cliente;
import Models.Pelicula;
import Models.PrecioEntrada;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class RegistroEntradaView extends JInternalFrame {

    private CRUDEntrada controlador;
    private Entrada entrada;
    private boolean esActualizar;
    private final JDesktopPane desktop;
    private final JFrame parentFrame;

    private JComboBox<FuncionCombo> cboFuncion;
    private JComboBox<ClienteCombo> cboCliente;
    private JComboBox<PrecioCombo> cboPrecio;
    private JTextField txtNumeroFila, txtNumeroAsiento, txtFechaVenta, txtEstado;
    private JButton btnGuardar, btnCancelar;
    private LogoPanel logoPanel;
    private MainPanel mainPanel;

    public RegistroEntradaView(CRUDEntrada controlador,
            EntradaDetallada entradaDetallada,
            boolean esActualizar,
            JDesktopPane desktop,
            JFrame parentFrame) {
        super(esActualizar ? "Actualizar Entrada" : "Registrar Entrada",
                true, true, true, true);
        this.controlador = controlador;
        this.esActualizar = esActualizar;
        this.desktop = desktop;
        this.parentFrame = parentFrame;
        setSize(500, 600);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);

        // Inicializar modelo Entrada
        if (esActualizar && entradaDetallada != null) {
            entrada = new Entrada();
            entrada.setId(entradaDetallada.getId());
            entrada.setFuncionID(entradaDetallada.getFuncionID());
            entrada.setClienteID(entradaDetallada.getClienteID());
            entrada.setPrecioEntradaID(entradaDetallada.getPrecioEntradaID());
            entrada.setNumeroFila(entradaDetallada.getNumeroFila());
            entrada.setNumeroAsiento(entradaDetallada.getNumeroAsiento());
            entrada.setFechaVenta(entradaDetallada.getFechaVenta());
            entrada.setEstado(entradaDetallada.getEstado());
        } else {
            entrada = new Entrada();
        }

        initComponents();
        cargarListados();

        desktop.add(this);
        setVisible(true);
    }

    private void initComponents() {
        mainPanel = new MainPanel("/img/FondoBlancoGigante.png");
        mainPanel.setLayout(null);
        setContentPane(mainPanel);

        logoPanel = new LogoPanel("/img/CineplanetLogoV2.png");
        logoPanel.setBounds(120, 20, 260, 130);
        mainPanel.add(logoPanel);

        int y = 160;

        JLabel lblFuncion = new JLabel("Función:");
        lblFuncion.setBounds(50, y, 100, 25);
        setOpaqueLabel(lblFuncion);
        mainPanel.add(lblFuncion);

        cboFuncion = new JComboBox<>();
        cboFuncion.setBounds(150, y, 300, 25);
        mainPanel.add(cboFuncion);

        y += 40;
        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setBounds(50, y, 100, 25);
        setOpaqueLabel(lblCliente);
        mainPanel.add(lblCliente);

        cboCliente = new JComboBox<>();
        cboCliente.setBounds(150, y, 300, 25);
        mainPanel.add(cboCliente);

        y += 40;
        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(50, y, 100, 25);
        setOpaqueLabel(lblPrecio);
        mainPanel.add(lblPrecio);

        cboPrecio = new JComboBox<>();
        cboPrecio.setBounds(150, y, 300, 25);
        mainPanel.add(cboPrecio);

        y += 40;
        JLabel lblFila = new JLabel("Fila:");
        lblFila.setBounds(50, y, 100, 25);
        setOpaqueLabel(lblFila);
        mainPanel.add(lblFila);

        txtNumeroFila = new JTextField(entrada.getNumeroFila());
        txtNumeroFila.setBounds(150, y, 300, 25);
        setOpaqueField(txtNumeroFila);
        mainPanel.add(txtNumeroFila);

        y += 40;
        JLabel lblAsiento = new JLabel("Asiento:");
        lblAsiento.setBounds(50, y, 100, 25);
        setOpaqueLabel(lblAsiento);
        mainPanel.add(lblAsiento);

        txtNumeroAsiento = new JTextField(
                esActualizar
                        ? String.valueOf(entrada.getNumeroAsiento())
                        : ""
        );
        txtNumeroAsiento.setBounds(150, y, 300, 25);
        setOpaqueField(txtNumeroAsiento);
        mainPanel.add(txtNumeroAsiento);

        y += 40;
        JLabel lblFecha = new JLabel("Fecha Venta (yyyy-MM-dd HH:mm):");
        lblFecha.setBounds(50, y, 200, 25);
        setOpaqueLabel(lblFecha);
        mainPanel.add(lblFecha);

        txtFechaVenta = new JTextField(
                esActualizar && entrada.getFechaVenta() != null
                ? new SimpleDateFormat("yyyy-MM-dd HH:mm")
                        .format(entrada.getFechaVenta())
                : ""
        );
        txtFechaVenta.setBounds(250, y, 200, 25);
        setOpaqueField(txtFechaVenta);
        mainPanel.add(txtFechaVenta);

        y += 40;
        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(50, y, 100, 25);
        setOpaqueLabel(lblEstado);
        mainPanel.add(lblEstado);

        txtEstado = new JTextField(entrada.getEstado());
        txtEstado.setBounds(150, y, 300, 25);
        setOpaqueField(txtEstado);
        mainPanel.add(txtEstado);

        // Botones
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(150, y + 50, 100, 30);
        btnGuardar.setBackground(new Color(0, 123, 255));
        btnGuardar.setFocusPainted(false);
        btnGuardar.addActionListener(e -> guardarEntrada());
        mainPanel.add(btnGuardar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(260, y + 50, 100, 30);
        btnCancelar.setBackground(new Color(255, 102, 102));
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> dispose());
        mainPanel.add(btnCancelar);
    }

    private void setOpaqueLabel(JLabel lbl) {
        lbl.setOpaque(true);
        lbl.setBackground(new Color(255, 255, 255, 200));
    }

    private void setOpaqueField(JTextField tf) {
        tf.setOpaque(true);
        tf.setBackground(new Color(255, 255, 255, 230));
    }

    private void cargarListados() {
        // Cargar funciones
        try {
            List<Pelicula> peliculas = new CRUDPelicula().obtenerTodos();
            for (Pelicula p : peliculas) {
                cboFuncion.addItem(new FuncionCombo(p.getId(), p.getTitulo()));
            }
            cboFuncion.setSelectedItem(
                    new FuncionCombo(entrada.getFuncionID(), "")
            );
        } catch (Exception e) {
            /* manejar error */ }

        // Cargar clientes
        try {
            List<Cliente> clientes = new CRUDCliente().obtenerTodos();
            for (Cliente c : clientes) {
                cboCliente.addItem(new ClienteCombo(
                        c.getId(), c.getNombre(), c.getApellido()
                ));
            }
            cboCliente.setSelectedItem(
                    new ClienteCombo(entrada.getClienteID(), "", "")
            );
        } catch (Exception e) {
            /* manejar error */ }

        // Cargar precios
        try {
            List<PrecioEntrada> precios = new CRUDPrecioEntrada().obtenerTodos();
            for (PrecioEntrada p : precios) {
                cboPrecio.addItem(new PrecioCombo(
                        p.getId(), p.getPrecio(), p.getNombre()
                ));
            }
            cboPrecio.setSelectedItem(
                    new PrecioCombo(entrada.getPrecioEntradaID(), null, "")
            );
        } catch (Exception e) {
            /* manejar error */ }
    }

    private void guardarEntrada() {
        try {
            Entrada e = new Entrada();
            if (esActualizar) {
                e.setId(entrada.getId());
            }

            e.setFuncionID(
                    ((FuncionCombo) cboFuncion.getSelectedItem()).id
            );
            e.setClienteID(
                    ((ClienteCombo) cboCliente.getSelectedItem()).id
            );
            e.setPrecioEntradaID(
                    ((PrecioCombo) cboPrecio.getSelectedItem()).id
            );
            e.setNumeroFila(txtNumeroFila.getText().trim());
            e.setNumeroAsiento(
                    Integer.parseInt(txtNumeroAsiento.getText().trim())
            );
            e.setFechaVenta(
                    new SimpleDateFormat("yyyy-MM-dd HH:mm")
                            .parse(txtFechaVenta.getText().trim())
            );
            e.setEstado(txtEstado.getText().trim());

            boolean success = esActualizar
                    ? controlador.actualizar(e)
                    : controlador.registrar(e);

            JOptionPane.showMessageDialog(
                    this,
                    success
                            ? (esActualizar ? "Entrada actualizada" : "Entrada registrada")
                            : "Error al guardar",
                    success ? "Éxito" : "Error",
                    success ? JOptionPane.INFORMATION_MESSAGE
                            : JOptionPane.ERROR_MESSAGE
            );
            if (success) {
                dispose();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Modelos para JComboBox
    class FuncionCombo {

        int id;
        String titulo;

        FuncionCombo(int id, String titulo) {
            this.id = id;
            this.titulo = titulo;
        }

        @Override
        public String toString() {
            return titulo;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof FuncionCombo && ((FuncionCombo) o).id == id;
        }
    }

    class ClienteCombo {

        int id;
        String nombre;
        String apellido;

        ClienteCombo(int id, String n, String a) {
            this.id = id;
            this.nombre = n;
            this.apellido = a;
        }

        @Override
        public String toString() {
            return nombre + " " + apellido;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof ClienteCombo && ((ClienteCombo) o).id == id;
        }
    }

    class PrecioCombo {

        int id;
        String label;

        PrecioCombo(int id, Number precio, String nombre) {
            this.id = id;
            this.label = nombre + " - S/" + precio;
        }

        @Override
        public String toString() {
            return label;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof PrecioCombo && ((PrecioCombo) o).id == id;
        }
    }

    // Clases para dibujo de fondo y logo (sin cambios)
    class MainPanel extends JPanel {

        private Image bg;

        public MainPanel(String path) {
            try {
                bg = new ImageIcon(getClass().getResource(path)).getImage();
            } catch (Exception e) {
                bg = null;
            }
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (bg != null) {
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            } else {
                setBackground(new Color(220, 220, 220));
            }
        }
    }

    class LogoPanel extends JPanel {

        private Image logo;

        public LogoPanel(String path) {
            try {
                logo = new ImageIcon(getClass().getResource(path)).getImage();
            } catch (Exception e) {
                logo = null;
            }
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (logo != null) {
                int w = getWidth(), h = getHeight();
                double scale = Math.min((double) w / logo.getWidth(this), (double) h / logo.getHeight(this));
                int nw = (int) (logo.getWidth(this) * scale), nh = (int) (logo.getHeight(this) * scale);
                g.drawImage(logo, (w - nw) / 2, (h - nh) / 2, nw, nh, this);
            }
        }
    }
}
