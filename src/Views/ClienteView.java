package Views;

import Controllers.CRUDCliente;
import Controllers.ICliente;
import Models.Cliente;
import Util.Icono;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

public class ClienteView extends JInternalFrame {
    private JPanel mainPanel;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tablaClientes;
    private DefaultTableModel tableModel;
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEmail;
    private JTextField txtTelefono;
    private JTextField txtFechaRegistro;
    private JButton btnAgregar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnVolver;
    private ICliente clienteController;
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

    public ClienteView(JDesktopPane desktop, JFrame parent) {
        super("", true, true, true, true); 
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setBorder(null);
        putClientProperty("JInternalFrame.isPalette", Boolean.TRUE); // Remove title bar
        this.parentFrame = parent;

        mainPanel = new MainPanel("/img/FondoBlancoGigante.png");
        mainPanel.setLayout(null);
        setContentPane(mainPanel);

        logoPanel = new LogoPanel("/img/CineplanetLogoV2.png");
        logoPanel.setBounds(270, 20, 260, 130);
        mainPanel.add(logoPanel);

        JLabel lblBuscar = new JLabel("Buscar (ID o Nombre):");
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

        String[] columnas = {"ID", "Nombre", "Apellido", "Email", "Teléfono", "Fecha Registro"};
        tableModel = new DefaultTableModel(columnas, 0);
        tablaClientes = new JTable(tableModel);
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaClientes);
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

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(50, 390, 100, 25);
        lblNombre.setOpaque(true);
        lblNombre.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(150, 390, 200, 25);
        txtNombre.setOpaque(true);
        txtNombre.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtNombre);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setBounds(50, 420, 100, 25);
        lblApellido.setOpaque(true);
        lblApellido.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblApellido);

        txtApellido = new JTextField();
        txtApellido.setBounds(150, 420, 200, 25);
        txtApellido.setOpaque(true);
        txtApellido.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtApellido);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(50, 450, 100, 25);
        lblEmail.setOpaque(true);
        lblEmail.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(150, 450, 200, 25);
        txtEmail.setOpaque(true);
        txtEmail.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtEmail);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(50, 480, 100, 25);
        lblTelefono.setOpaque(true);
        lblTelefono.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(150, 480, 200, 25);
        txtTelefono.setOpaque(true);
        txtTelefono.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtTelefono);

        JLabel lblFechaRegistro = new JLabel("Fecha Registro:");
        lblFechaRegistro.setBounds(50, 510, 100, 25);
        lblFechaRegistro.setOpaque(true);
        lblFechaRegistro.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblFechaRegistro);

        txtFechaRegistro = new JTextField();
        txtFechaRegistro.setBounds(150, 510, 200, 25);
        txtFechaRegistro.setOpaque(true);
        txtFechaRegistro.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtFechaRegistro);

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

        clienteController = new CRUDCliente();
        cargarClientes();

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarClientes();
            }
        });

        tablaClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaClientes.getSelectedRow() != -1) {
                int selectedRow = tablaClientes.getSelectedRow();
                txtId.setText(tableModel.getValueAt(selectedRow, 0).toString());
                txtNombre.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtApellido.setText(tableModel.getValueAt(selectedRow, 2).toString());
                txtEmail.setText(tableModel.getValueAt(selectedRow, 3).toString());
                txtTelefono.setText(tableModel.getValueAt(selectedRow, 4).toString());
                txtFechaRegistro.setText(tableModel.getValueAt(selectedRow, 5).toString());
            }
        });

        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarCliente();
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCliente();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCliente();
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

    private void cargarClientes() {
        tableModel.setRowCount(0);
        List<Cliente> clientes = clienteController.obtenerTodos();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (Cliente cliente : clientes) {
            tableModel.addRow(new Object[]{
                cliente.getId(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getEmail(),
                cliente.getTelefono(),
                cliente.getFechaRegistro() != null ? cliente.getFechaRegistro().format(formatter) : ""
            });
        }
    }

    private void buscarClientes() {
        String criterio = txtBuscar.getText().trim();
        List<Cliente> clientes;
        try {
            int id = Integer.parseInt(criterio);
            Cliente cliente = clienteController.obtenerPorId(id);
            clientes = cliente != null ? List.of(cliente) : List.of();
        } catch (NumberFormatException e) {
            clientes = clienteController.buscarPorNombre(criterio);
        }

        tableModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (Cliente cliente : clientes) {
            tableModel.addRow(new Object[]{
                cliente.getId(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getEmail(),
                cliente.getTelefono(),
                cliente.getFechaRegistro() != null ? cliente.getFechaRegistro().format(formatter) : ""
            });
        }
    }

    private void agregarCliente() {
        try {
            Cliente cliente = new Cliente();
            cliente.setNombre(txtNombre.getText());
            cliente.setApellido(txtApellido.getText());
            cliente.setEmail(txtEmail.getText());
            cliente.setTelefono(txtTelefono.getText());
            cliente.setFechaRegistro(LocalDate.parse(txtFechaRegistro.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            if (clienteController.registrar(cliente)) {
                JOptionPane.showMessageDialog(this, "Cliente registrado exitosamente");
                cargarClientes();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar cliente. Verifique los datos.");
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use yyyy-MM-dd.");
        }
    }

    private void actualizarCliente() {
        try {
            int id = Integer.parseInt(txtId.getText());
            Cliente cliente = new Cliente();
            cliente.setId(id);
            cliente.setNombre(txtNombre.getText());
            cliente.setApellido(txtApellido.getText());
            cliente.setEmail(txtEmail.getText());
            cliente.setTelefono(txtTelefono.getText());
            cliente.setFechaRegistro(LocalDate.parse(txtFechaRegistro.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            if (clienteController.actualizar(cliente)) {
                JOptionPane.showMessageDialog(this, "Cliente actualizado exitosamente");
                cargarClientes();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar cliente. Verifique los datos.");
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use yyyy-MM-dd.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para actualizar.");
        }
    }

    private void eliminarCliente() {
        try {
            int id = Integer.parseInt(txtId.getText());
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este cliente?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (clienteController.eliminar(id)) {
                    JOptionPane.showMessageDialog(this, "Cliente eliminado exitosamente");
                    cargarClientes();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar cliente.");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar.");
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        txtFechaRegistro.setText("");
    }
}