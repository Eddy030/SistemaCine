package Views;

import Controllers.CRUDVenta;
import Models.Venta;
import Models.Funcion;
import Models.Producto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class VentaView extends JInternalFrame {

    private JPanel mainPanel;
    private JComboBox<String> comboClientes;
    private JComboBox<String> comboFunciones;
    private JComboBox<String> comboTiposEntrada;
    private JTextField txtFila;
    private JTextField txtAsiento;
    private JComboBox<String> comboMetodoPago;
    private JTextField txtPrecioTotal;
    private JTable tablaProductos;
    private DefaultTableModel productosTableModel;
    private JButton btnAgregarProducto;
    private JButton btnBorrarProducto;
    private JButton btnRegistrar;
    private JButton btnVolver;
    private JButton btnNuevoCliente;
    private CRUDVenta controlador;
    private LogoPanel logoPanel;
    private JDesktopPane desktop;
    private JFrame parentFrame;
    private int empleadoId;

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

    public VentaView(JDesktopPane desktop, JFrame parent, int empleadoId) {
        super("Nueva Venta", true, true, true, true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setBorder(null);
        putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        this.desktop = desktop;
        this.parentFrame = parent;
        this.empleadoId = empleadoId;

        controlador = new CRUDVenta();

        mainPanel = new MainPanel("/img/FondoBlancoGigante.png");
        mainPanel.setLayout(null);
        setContentPane(mainPanel);

        logoPanel = new LogoPanel("/img/CineplanetLogoV2.png");
        logoPanel.setBounds(270, 20, 260, 130);
        mainPanel.add(logoPanel);

        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setBounds(50, 160, 100, 25);
        lblCliente.setOpaque(true);
        lblCliente.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblCliente);

        comboClientes = new JComboBox<>();
        cargarClientes();
        comboClientes.setBounds(150, 160, 200, 25);
        comboClientes.setOpaque(true);
        comboClientes.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(comboClientes);

        btnNuevoCliente = new JButton("Nuevo Cliente");
        btnNuevoCliente.setBounds(360, 160, 120, 25);
        btnNuevoCliente.setBackground(new Color(0, 123, 255));
        btnNuevoCliente.setFocusPainted(false);
        btnNuevoCliente.addActionListener(e -> registrarNuevoCliente());
        mainPanel.add(btnNuevoCliente);

        JLabel lblFuncion = new JLabel("Función:");
        lblFuncion.setBounds(50, 200, 100, 25);
        lblFuncion.setOpaque(true);
        lblFuncion.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblFuncion);

        List<Funcion> funciones = controlador.obtenerFuncionesDisponibles();
        String[] funcionItems = funciones.stream()
                .map(f -> "F" + f.getId() + " - " + f.getTituloPelicula() + " (Sala " + f.getSalaId() + ", " + f.getFechaHora() + ")")
                .toArray(String[]::new);
        comboFunciones = new JComboBox<>(funcionItems);
        comboFunciones.setBounds(150, 200, 400, 25);
        comboFunciones.setOpaque(true);
        comboFunciones.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(comboFunciones);

        JLabel lblTipoEntrada = new JLabel("Tipo Entrada:");
        lblTipoEntrada.setBounds(50, 240, 100, 25);
        lblTipoEntrada.setOpaque(true);
        lblTipoEntrada.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblTipoEntrada);

        List<String> tiposEntrada = controlador.obtenerTiposEntrada();
        comboTiposEntrada = new JComboBox<>(tiposEntrada.toArray(new String[0]));
        comboTiposEntrada.setBounds(150, 240, 200, 25);
        comboTiposEntrada.setOpaque(true);
        comboTiposEntrada.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(comboTiposEntrada);

        JLabel lblFila = new JLabel("Fila:");
        lblFila.setBounds(50, 280, 100, 25);
        lblFila.setOpaque(true);
        lblFila.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblFila);

        txtFila = new JTextField("A");
        txtFila.setBounds(150, 280, 50, 25);
        txtFila.setOpaque(true);
        txtFila.setBackground(new Color(255, 255, 255, 255));
        mainPanel.add(txtFila);

        JLabel lblAsiento = new JLabel("Asiento:");
        lblAsiento.setBounds(220, 280, 100, 25);
        lblAsiento.setOpaque(true);
        lblAsiento.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblAsiento);

        txtAsiento = new JTextField("1");
        txtAsiento.setBounds(280, 280, 100, 25); // Increased width to 100 for better visibility
        txtAsiento.setOpaque(true);
        txtAsiento.setBackground(new Color(255, 255, 255, 255));
        txtAsiento.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Added border for better visibility
        mainPanel.add(txtAsiento);

        JLabel lblMetodoPago = new JLabel("Método de Pago:");
        lblMetodoPago.setBounds(50, 320, 100, 25);
        lblMetodoPago.setOpaque(true);
        lblMetodoPago.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblMetodoPago);

        comboMetodoPago = new JComboBox<>(new String[]{"Efectivo", "Tarjeta de Crédito", "Tarjeta de Débito"});
        comboMetodoPago.setBounds(150, 320, 200, 25);
        comboMetodoPago.setOpaque(true);
        comboMetodoPago.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(comboMetodoPago);

        JLabel lblPrecioTotal = new JLabel("Precio Total:");
        lblPrecioTotal.setBounds(50, 360, 100, 25);
        lblPrecioTotal.setOpaque(true);
        lblPrecioTotal.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblPrecioTotal);

        txtPrecioTotal = new JTextField("0.00");
        txtPrecioTotal.setBounds(150, 360, 200, 25);
        txtPrecioTotal.setEditable(false);
        txtPrecioTotal.setOpaque(true);
        txtPrecioTotal.setBackground(new Color(255, 255, 255, 255)); // Fully opaque background
        txtPrecioTotal.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Added border for better visibility
        mainPanel.add(txtPrecioTotal);

        JLabel lblProductos = new JLabel("Productos (Opcional):");
        lblProductos.setBounds(50, 400, 150, 25);
        lblProductos.setOpaque(true);
        lblProductos.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblProductos);

        String[] columnasProductos = {"ID", "Descripción", "Precio", "Cantidad"};
        productosTableModel = new DefaultTableModel(columnasProductos, 0);
        tablaProductos = new JTable(productosTableModel);
        JScrollPane scrollPaneProductos = new JScrollPane(tablaProductos);
        scrollPaneProductos.setBounds(50, 430, 700, 100);
        mainPanel.add(scrollPaneProductos);

        btnAgregarProducto = new JButton("Agregar Producto");
        btnAgregarProducto.setBounds(400, 540, 150, 30);
        btnAgregarProducto.setBackground(new Color(0, 123, 255));
        btnAgregarProducto.setFocusPainted(false);
        btnAgregarProducto.addActionListener(e -> agregarProducto());
        mainPanel.add(btnAgregarProducto);

        btnBorrarProducto = new JButton("Borrar Producto");
        btnBorrarProducto.setBounds(560, 400, 150, 30);
        btnBorrarProducto.setBackground(new Color(0, 123, 255));
        btnBorrarProducto.setFocusPainted(false);
        btnBorrarProducto.addActionListener(e -> borrarProducto());
        mainPanel.add(btnBorrarProducto);

        btnRegistrar = new JButton("Registrar Venta");
        btnRegistrar.setBounds(560, 540, 150, 30);
        btnRegistrar.setBackground(new Color(0, 123, 255));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.addActionListener(e -> registrarVenta());
        mainPanel.add(btnRegistrar);

        btnVolver = new JButton("Volver");
        btnVolver.setBounds(650, 10, 100, 30);
        btnVolver.setBackground(new Color(0, 123, 255));
        btnVolver.setFocusPainted(false);
        mainPanel.add(btnVolver);

        comboFunciones.addActionListener(e -> calcularPrecioTotal());
        comboTiposEntrada.addActionListener(e -> calcularPrecioTotal());

        calcularPrecioTotal();

        desktop.add(this);
        setVisible(true);

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                if (parentFrame != null) {
                    parentFrame.dispose();
                }
            }
        });
    }

    private void cargarClientes() {
        String sql = "SELECT ID, Nombre, Apellido FROM Clientes";
        try (Connection conn = new CRUDVenta().Conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            comboClientes.removeAllItems(); // Limpiar antes de cargar
            comboClientes.addItem("Sin cliente (0)");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nombre = rs.getString("Nombre");
                String apellido = rs.getString("Apellido");
                comboClientes.addItem(nombre + " " + apellido + " (" + id + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarFunciones() {
        List<Funcion> funciones = controlador.obtenerFuncionesDisponibles();
        String[] funcionItems = funciones.stream()
                .map(f -> "F" + f.getId() + " - " + f.getTituloPelicula() + " (Sala " + f.getSalaId() + ", " + f.getFechaHora() + ")")
                .toArray(String[]::new);
        comboFunciones.removeAllItems();
        for (String item : funcionItems) {
            comboFunciones.addItem(item);
        }
    }

    private void cargarTiposEntrada() {
        List<String> tiposEntrada = controlador.obtenerTiposEntrada();
        comboTiposEntrada.removeAllItems();
        for (String tipo : tiposEntrada) {
            comboTiposEntrada.addItem(tipo);
        }
    }

    private void reiniciarFormulario() {
        // Reiniciar combo de clientes
        cargarClientes();
        comboClientes.setSelectedIndex(0); // Seleccionar "Sin cliente (0)"

        // Reiniciar combo de funciones
        cargarFunciones();
        if (comboFunciones.getItemCount() > 0) {
            comboFunciones.setSelectedIndex(0);
        }

        // Reiniciar combo de tipos de entrada
        cargarTiposEntrada();
        if (comboTiposEntrada.getItemCount() > 0) {
            comboTiposEntrada.setSelectedIndex(0);
        }

        // Reiniciar campos de fila y asiento
        txtFila.setText("A");
        txtAsiento.setText("1");

        // Reiniciar método de pago
        comboMetodoPago.setSelectedIndex(0); // Seleccionar "Efectivo"

        // Limpiar tabla de productos
        productosTableModel.setRowCount(0);

        // Reiniciar precio total
        txtPrecioTotal.setText("0.00");

        // Recalcular precio total (esto también manejará el precio inicial de la entrada)
        calcularPrecioTotal();
    }

    private void registrarNuevoCliente() {
        JTextField txtNombre = new JTextField();
        JTextField txtApellido = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtTelefono = new JTextField();

        Object[] fields = {
            "Nombre:", txtNombre,
            "Apellido:", txtApellido,
            "Email:", txtEmail,
            "Teléfono:", txtTelefono
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Registrar Nuevo Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String email = txtEmail.getText().trim();
            String telefono = txtTelefono.getText().trim();

            if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sql = "INSERT INTO Clientes (Nombre, Apellido, Email, Telefono, FechaRegistro) VALUES (?, ?, ?, ?, CURDATE())";
            try (Connection conn = new CRUDVenta().Conectar(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, nombre);
                stmt.setString(2, apellido);
                stmt.setString(3, email);
                stmt.setString(4, telefono);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        int nuevoId = rs.getInt(1);
                        comboClientes.addItem(nombre + " " + apellido + " (" + nuevoId + ")");
                        comboClientes.setSelectedItem(nombre + " " + apellido + " (" + nuevoId + ")");
                        JOptionPane.showMessageDialog(this, "Cliente registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    }
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al registrar cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void agregarProducto() {
        List<Producto> productos = controlador.obtenerProductosDisponibles();
        if (productos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos disponibles.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String[] opciones = productos.stream().map(p -> "P" + p.getId() + " - " + p.getNombre()).toArray(String[]::new);
        String seleccion = (String) JOptionPane.showInputDialog(this, "Seleccione un producto:", "Agregar Producto", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (seleccion != null) {
            String idStr = seleccion.split(" - ")[0].substring(1);
            int id;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID de producto inválido: " + idStr, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Producto producto = productos.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
            if (producto == null) {
                JOptionPane.showMessageDialog(this, "Producto no encontrado con ID: " + id, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String cantidadStr = JOptionPane.showInputDialog(this, "Ingrese cantidad:", "1");
            if (cantidadStr == null) { // Si el usuario cancela el diálogo
                return; // Salir del método sin agregar la fila
            }
            int cantidad;
            try {
                cantidad = Integer.parseInt(cantidadStr);
                if (cantidad <= 0 || cantidad > producto.getStock()) {
                    JOptionPane.showMessageDialog(this, "Cantidad inválida o insuficiente stock.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Cantidad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Buscar si el producto ya existe en la tabla
            boolean productoExistente = false;
            for (int i = 0; i < productosTableModel.getRowCount(); i++) {
                // Asegurarse de que el ID en la tabla es válido
                Object idObj = productosTableModel.getValueAt(i, 0);
                if (idObj == null) {
                    continue; // Evitar valores nulos
                }
                String existingIdStr = idObj.toString();
                int existingId;
                try {
                    existingId = Integer.parseInt(existingIdStr.split(" - ")[0].substring(1));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Error al procesar ID existente en la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                if (existingId == id) {
                    // Obtener la cantidad actual de la tabla
                    Object cantidadObj = productosTableModel.getValueAt(i, 3);
                    int cantidadActual;
                    try {
                        cantidadActual = cantidadObj != null ? Integer.parseInt(cantidadObj.toString()) : 0;
                    } catch (NumberFormatException e) {
                        cantidadActual = 0; // Asumir 0 si no se puede parsear
                    }

                    int nuevaCantidad = cantidadActual + cantidad;
                    if (nuevaCantidad <= producto.getStock()) {
                        productosTableModel.setValueAt(nuevaCantidad, i, 3);
                        productoExistente = true;
                    } else {
                        JOptionPane.showMessageDialog(this, "Cantidad total excede el stock disponible.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
            }

            // Si no existe, agregar nueva fila
            if (!productoExistente) {
                productosTableModel.addRow(new Object[]{"P" + id + " - " + producto.getNombre(), producto.getNombre(), producto.getPrecioUnitario(), cantidad});
            }
            calcularPrecioTotal();
        }
    }

    private void borrarProducto() {
        int selectedRow = tablaProductos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para borrar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        productosTableModel.removeRow(selectedRow);
        calcularPrecioTotal();
    }

    private void calcularPrecioTotal() {
        double total = 0;

        // Precio de la entrada
        if (comboTiposEntrada.getSelectedItem() != null) {
            String tipoEntrada = (String) comboTiposEntrada.getSelectedItem();
            double precioEntrada = Double.parseDouble(tipoEntrada.split("\\$")[1].replace(")", ""));
            total += precioEntrada;
        }

        // Precio de los productos
        for (int i = 0; i < productosTableModel.getRowCount(); i++) {
            double precio = Double.parseDouble(productosTableModel.getValueAt(i, 2).toString());
            int cantidad = Integer.parseInt(productosTableModel.getValueAt(i, 3).toString());
            total += precio * cantidad * (1 - 0.05); // Aplica descuento del 5%
        }

        txtPrecioTotal.setText(String.format("%.2f", total));
    }

    private void registrarVenta() {
        if (comboFunciones.getSelectedItem() == null || comboTiposEntrada.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una función y un tipo de entrada.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String clienteSeleccionado = (String) comboClientes.getSelectedItem();
        int clienteId;
        if (clienteSeleccionado.equals("Sin cliente (0)")) {
            clienteId = 0;
        } else {
            clienteId = Integer.parseInt(clienteSeleccionado.split(" \\(")[1].replace(")", ""));
        }

        String funcionSeleccionada = (String) comboFunciones.getSelectedItem();
        int funcionId = Integer.parseInt(funcionSeleccionada.split(" - ")[0].substring(1));
        String tipoEntradaSeleccionado = (String) comboTiposEntrada.getSelectedItem();
        int precioEntradaId = Integer.parseInt(tipoEntradaSeleccionado.split(" - ")[0]);
        String numeroFila = txtFila.getText().trim();
        int numeroAsiento;
        try {
            numeroAsiento = Integer.parseInt(txtAsiento.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Número de asiento inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String metodoPago = (String) comboMetodoPago.getSelectedItem();
        double precioTotal = Double.parseDouble(txtPrecioTotal.getText());

        Venta venta = new Venta(0, empleadoId, LocalDateTime.now(), "Entradas", metodoPago, precioTotal);
        boolean success = controlador.registrarVenta(venta, funcionId, precioEntradaId, numeroFila, numeroAsiento, productosTableModel, clienteId);
        if (success) {
            JOptionPane.showMessageDialog(this, "Venta registrada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            reiniciarFormulario(); // Reiniciar la interfaz en lugar de cerrar
        }
    }
}
