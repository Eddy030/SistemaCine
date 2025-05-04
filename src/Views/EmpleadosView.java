package Views;

import Controllers.CRUDEmpleado;
import Controllers.IEmpleado;
import Models.Usuario;
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

public class EmpleadosView extends JInternalFrame {
    private JPanel mainPanel;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tablaEmpleados;
    private DefaultTableModel tableModel;
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEmail;
    private JTextField txtTelefono;
    private JTextField txtFechaContratacion;
    private JTextField txtDireccion;
    private JButton btnAgregar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnVolver;
    private IEmpleado empleadoController;
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

    public EmpleadosView(JDesktopPane desktop, JFrame parent) {
        super("", true, true, true, true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setBorder(null);
        putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        this.parentFrame = parent;

        empleadoController = new CRUDEmpleado();

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

        String[] columnas = {"ID", "Nombre", "Apellido", "Email", "Teléfono", "Fecha Contratación", "Dirección"};
        tableModel = new DefaultTableModel(columnas, 0);
        tablaEmpleados = new JTable(tableModel);
        tablaEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaEmpleados);
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

        JLabel lblFechaContratacion = new JLabel("Fecha Contratación:");
        lblFechaContratacion.setBounds(50, 510, 150, 25);
        lblFechaContratacion.setOpaque(true);
        lblFechaContratacion.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblFechaContratacion);

        txtFechaContratacion = new JTextField("YYYY-MM-DD");
        txtFechaContratacion.setBounds(200, 510, 150, 25);
        txtFechaContratacion.setOpaque(true);
        txtFechaContratacion.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtFechaContratacion);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(400, 360, 100, 25);
        lblDireccion.setOpaque(true);
        lblDireccion.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblDireccion);

        txtDireccion = new JTextField();
        txtDireccion.setBounds(500, 360, 200, 25);
        txtDireccion.setOpaque(true);
        txtDireccion.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtDireccion);

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

        cargarEmpleados();

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarEmpleados();
            }
        });

        tablaEmpleados.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaEmpleados.getSelectedRow() != -1) {
                int selectedRow = tablaEmpleados.getSelectedRow();
                txtId.setText(tableModel.getValueAt(selectedRow, 0).toString());
                txtNombre.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtApellido.setText(tableModel.getValueAt(selectedRow, 2).toString());
                txtEmail.setText(tableModel.getValueAt(selectedRow, 3).toString());
                txtTelefono.setText(tableModel.getValueAt(selectedRow, 4).toString());
                txtFechaContratacion.setText(tableModel.getValueAt(selectedRow, 5).toString());
                txtDireccion.setText(tableModel.getValueAt(selectedRow, 6).toString());
            }
        });

        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEmpleado();
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarEmpleado();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarEmpleado();
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

    private void cargarEmpleados() {
        tableModel.setRowCount(0);
        List<Usuario> empleados = empleadoController.obtenerPorRol("EMPLEADO");
        for (Usuario empleado : empleados) {
            tableModel.addRow(new Object[]{
                empleado.getId(),
                empleado.getNombre(),
                empleado.getApellido(),
                empleado.getEmail() != null ? empleado.getEmail() : "",
                empleado.getTelefono() != null ? empleado.getTelefono() : "",
                empleado.getFechaContratacion() != null ? empleado.getFechaContratacion() : "",
                empleado.getDireccion() != null ? empleado.getDireccion() : ""
            });
        }
    }

    private void buscarEmpleados() {
        String criterio = txtBuscar.getText().trim();
        List<Usuario> empleados;
        try {
            int id = Integer.parseInt(criterio);
            Usuario empleado = empleadoController.obtenerPorId(id);
            empleados = empleado != null ? List.of(empleado) : List.of();
        } catch (NumberFormatException e) {
            empleados = empleadoController.obtenerPorRol("EMPLEADO").stream()
                .filter(emp -> emp.getNombre().toLowerCase().contains(criterio.toLowerCase()) ||
                               emp.getApellido().toLowerCase().contains(criterio.toLowerCase()))
                .toList();
        }

        tableModel.setRowCount(0);
        for (Usuario empleado : empleados) {
            tableModel.addRow(new Object[]{
                empleado.getId(),
                empleado.getNombre(),
                empleado.getApellido(),
                empleado.getEmail() != null ? empleado.getEmail() : "",
                empleado.getTelefono() != null ? empleado.getTelefono() : "",
                empleado.getFechaContratacion() != null ? empleado.getFechaContratacion() : "",
                empleado.getDireccion() != null ? empleado.getDireccion() : ""
            });
        }
    }

    private void agregarEmpleado() {
        int response = JOptionPane.showConfirmDialog(
            this,
            "Para agregar un empleado, necesita registrarse mediante un formulario. ¿Desea ir al registro?",
            "Confirmar Registro",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.INFORMATION_MESSAGE
        );

        if (response == JOptionPane.OK_OPTION) {
            // Crear un objeto Usuario con los datos actuales
            Usuario empleado = new Usuario();
            empleado.setNombre(txtNombre.getText().trim());
            empleado.setApellido(txtApellido.getText().trim());
            empleado.setEmail(txtEmail.getText().trim());
            empleado.setTelefono(txtTelefono.getText().trim());
            String fecha = txtFechaContratacion.getText().trim();
            empleado.setFechaContratacion(fecha.equals("YYYY-MM-DD") ? "" : fecha);
            empleado.setDireccion(txtDireccion.getText().trim());
            empleado.setRolId(1); // Empleado

            // Abrir RegistroView con los datos prellenados
            RegistroView registroView = new RegistroView("empleados", empleado);
            registroView.setVisible(true);

            // Listener para manejar el resultado del registro
            registroView.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    if (registroView.isRegistroExitoso()) {
                        cargarEmpleados();
                        limpiarCampos();
                    }
                }
            });
        }
    }

    private void actualizarEmpleado() {
        try {
            int id = Integer.parseInt(txtId.getText());
            Usuario empleado = new Usuario();
            empleado.setId(id);
            empleado.setNombre(txtNombre.getText().trim());
            empleado.setApellido(txtApellido.getText().trim());
            empleado.setEmail(txtEmail.getText().trim());
            empleado.setTelefono(txtTelefono.getText().trim());
            String fecha = txtFechaContratacion.getText().trim();
            empleado.setFechaContratacion(fecha.equals("YYYY-MM-DD") ? "" : fecha);
            empleado.setDireccion(txtDireccion.getText().trim());
            empleado.setRolId(1); // Empleado

            if (empleadoController.actualizar(empleado)) {
                JOptionPane.showMessageDialog(this, "Empleado actualizado exitosamente");
                cargarEmpleados();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar empleado. Verifique los datos.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado válido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar empleado: " + e.getMessage());
        }
    }

    private void eliminarEmpleado() {
        try {
            int id = Integer.parseInt(txtId.getText());
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este empleado?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (empleadoController.eliminar(id)) {
                    JOptionPane.showMessageDialog(this, "Empleado eliminado exitosamente");
                    cargarEmpleados();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar empleado.");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado para eliminar.");
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        txtFechaContratacion.setText("YYYY-MM-DD");
        txtDireccion.setText("");
    }
}