package Views;

import Controllers.CRUDProducto;
import Controllers.IProducto;
import Models.CategoriaProducto;
import Models.Producto;
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

public class ProductoView extends JInternalFrame {
    private JPanel mainPanel;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tablaProductos;
    private DefaultTableModel tableModel;
    private JTextField txtId;
    private JComboBox<String> comboCategoria;
    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private JTextField txtPrecio;
    private JTextField txtStock;
    private JButton btnAgregar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnVolver;
    private IProducto productoController;
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

    public ProductoView(JDesktopPane desktop, JFrame parent) {
        super("", true, true, true, true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setBorder(null);
        putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        this.parentFrame = parent;

        // Inicializar productoController al inicio
        productoController = new CRUDProducto();

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

        String[] columnas = {"ID", "Categoría", "Nombre", "Descripción", "Precio", "Stock"};
        tableModel = new DefaultTableModel(columnas, 0);
        tablaProductos = new JTable(tableModel);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
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

        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setBounds(50, 390, 100, 25);
        lblCategoria.setOpaque(true);
        lblCategoria.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblCategoria);

        comboCategoria = new JComboBox<>();
        comboCategoria.setBounds(150, 390, 200, 25);
        comboCategoria.setOpaque(true);
        comboCategoria.setBackground(new Color(255, 255, 255, 230));
        cargarCategorias();
        mainPanel.add(comboCategoria);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(50, 420, 100, 25);
        lblNombre.setOpaque(true);
        lblNombre.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(150, 420, 200, 25);
        txtNombre.setOpaque(true);
        txtNombre.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtNombre);

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setBounds(50, 450, 100, 25);
        lblDescripcion.setOpaque(true);
        lblDescripcion.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblDescripcion);

        txtDescripcion = new JTextField();
        txtDescripcion.setBounds(150, 450, 200, 25);
        txtDescripcion.setOpaque(true);
        txtDescripcion.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtDescripcion);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(50, 480, 100, 25);
        lblPrecio.setOpaque(true);
        lblPrecio.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblPrecio);

        txtPrecio = new JTextField();
        txtPrecio.setBounds(150, 480, 200, 25);
        txtPrecio.setOpaque(true);
        txtPrecio.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtPrecio);

        JLabel lblStock = new JLabel("Stock:");
        lblStock.setBounds(50, 510, 100, 25);
        lblStock.setOpaque(true);
        lblStock.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblStock);

        txtStock = new JTextField();
        txtStock.setBounds(150, 510, 200, 25);
        txtStock.setOpaque(true);
        txtStock.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtStock);

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

        cargarProductos();

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductos();
            }
        });

        tablaProductos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaProductos.getSelectedRow() != -1) {
                int selectedRow = tablaProductos.getSelectedRow();
                txtId.setText(tableModel.getValueAt(selectedRow, 0).toString());
                comboCategoria.setSelectedItem(tableModel.getValueAt(selectedRow, 1).toString());
                txtNombre.setText(tableModel.getValueAt(selectedRow, 2).toString());
                txtDescripcion.setText(tableModel.getValueAt(selectedRow, 3).toString());
                txtPrecio.setText(tableModel.getValueAt(selectedRow, 4).toString());
                txtStock.setText(tableModel.getValueAt(selectedRow, 5).toString());
            }
        });

        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProducto();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
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

    private void cargarProductos() {
        tableModel.setRowCount(0);
        List<Producto> productos = productoController.obtenerTodos();
        for (Producto producto : productos) {
            String categoriaNombre = productoController.obtenerNombreCategoria(producto.getCategoriaId());
            tableModel.addRow(new Object[]{
                producto.getId(),
                categoriaNombre,
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecioUnitario(),
                producto.getStock()
            });
        }
    }

    private void buscarProductos() {
        String criterio = txtBuscar.getText().trim();
        List<Producto> productos;
        try {
            int id = Integer.parseInt(criterio);
            Producto producto = productoController.obtenerPorId(id);
            productos = producto != null ? List.of(producto) : List.of();
        } catch (NumberFormatException e) {
            productos = productoController.buscarPorNombre(criterio);
        }

        tableModel.setRowCount(0);
        for (Producto producto : productos) {
            String categoriaNombre = productoController.obtenerNombreCategoria(producto.getCategoriaId());
            tableModel.addRow(new Object[]{
                producto.getId(),
                categoriaNombre,
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecioUnitario(),
                producto.getStock()
            });
        }
    }

    private void agregarProducto() {
        try {
            Producto producto = new Producto();
            producto.setCategoriaId(productoController.obtenerCategoriaId((String) comboCategoria.getSelectedItem()));
            producto.setNombre(txtNombre.getText().trim());
            producto.setDescripcion(txtDescripcion.getText().trim());
            producto.setPrecioUnitario(Double.parseDouble(txtPrecio.getText().trim()));
            producto.setStock(Integer.parseInt(txtStock.getText().trim()));

            if (productoController.registrar(producto)) {
                JOptionPane.showMessageDialog(this, "Producto registrado exitosamente");
                cargarProductos();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar producto. Verifique los datos.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precio y stock deben ser numéricos válidos.");
        }
    }

    private void actualizarProducto() {
        try {
            int id = Integer.parseInt(txtId.getText());
            Producto producto = new Producto();
            producto.setId(id);
            producto.setCategoriaId(productoController.obtenerCategoriaId((String) comboCategoria.getSelectedItem()));
            producto.setNombre(txtNombre.getText().trim());
            producto.setDescripcion(txtDescripcion.getText().trim());
            producto.setPrecioUnitario(Double.parseDouble(txtPrecio.getText().trim()));
            producto.setStock(Integer.parseInt(txtStock.getText().trim()));

            if (productoController.actualizar(producto)) {
                JOptionPane.showMessageDialog(this, "Producto actualizado exitosamente");
                cargarProductos();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar producto. Verifique los datos.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto y verifique los datos numéricos.");
        }
    }

    private void eliminarProducto() {
        try {
            int id = Integer.parseInt(txtId.getText());
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este producto?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (productoController.eliminar(id)) {
                    JOptionPane.showMessageDialog(this, "Producto eliminado exitosamente");
                    cargarProductos();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar producto.");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar.");
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        comboCategoria.setSelectedIndex(0);
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
    }

    private void cargarCategorias() {
        List<CategoriaProducto> categorias = productoController.obtenerTodasCategorias();
        for (CategoriaProducto categoria : categorias) {
            comboCategoria.addItem(categoria.getNombre());
        }
        if (categorias.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay categorías disponibles.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
}