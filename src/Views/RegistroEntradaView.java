package Views;

import Controllers.CRUDEntrada;
import Models.Entrada;
import Models.EntradaDetallada;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RegistroEntradaView extends JInternalFrame {
    private JPanel mainPanel;
    private CRUDEntrada controlador;
    private Entrada entrada;
    private boolean esActualizar;
    private JDesktopPane desktop;
    private JFrame parentFrame;
    private JTextField txtFuncionID, txtClienteID, txtPrecioEntradaID, txtNumeroFila, txtNumeroAsiento, txtFechaVenta, txtEstado;
    private JButton btnGuardar, btnCancelar;
    private LogoPanel logoPanel;

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

    public RegistroEntradaView(CRUDEntrada controlador, EntradaDetallada entradaDetallada, boolean esActualizar, JDesktopPane desktop, JFrame parentFrame) {
        super(esActualizar ? "Actualizar Entrada" : "Registrar Entrada", true, true, true, true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 600);
        setResizable(false);
        setBorder(null);
        putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        this.controlador = controlador;
        this.esActualizar = esActualizar;
        this.desktop = desktop;
        this.parentFrame = parentFrame;

        // Initialize entrada based on entradaDetallada
        if (esActualizar && entradaDetallada != null) {
            this.entrada = new Entrada();
            this.entrada.setId(entradaDetallada.getId());
            // Note: We need to fetch or set the correct IDs for FuncionID, ClienteID, and PrecioEntradaID
            // Since EntradaDetallada doesn't have these IDs, we'll leave them as 0 or require manual input
            this.entrada.setNumeroFila(entradaDetallada.getNumeroFila());
            this.entrada.setNumeroAsiento(entradaDetallada.getNumeroAsiento());
            this.entrada.setFechaVenta(entradaDetallada.getFechaVenta());
            this.entrada.setEstado(entradaDetallada.getEstado());
        } else {
            this.entrada = new Entrada();
        }

        mainPanel = new MainPanel("/img/FondoBlancoGigante.png");
        mainPanel.setLayout(null);
        setContentPane(mainPanel);

        logoPanel = new LogoPanel("/img/CineplanetLogoV2.png");
        logoPanel.setBounds(120, 20, 260, 130);
        mainPanel.add(logoPanel);

        // Form fields
        int y = 160;
        JLabel lblFuncionID = new JLabel("Función ID:");
        lblFuncionID.setBounds(50, y, 100, 25);
        lblFuncionID.setOpaque(true);
        lblFuncionID.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblFuncionID);
        txtFuncionID = new JTextField(esActualizar ? String.valueOf(entrada.getFuncionID()) : "");
        txtFuncionID.setBounds(150, y, 300, 25);
        txtFuncionID.setOpaque(true);
        txtFuncionID.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtFuncionID);

        y += 40;
        JLabel lblClienteID = new JLabel("Cliente ID:");
        lblClienteID.setBounds(50, y, 100, 25);
        lblClienteID.setOpaque(true);
        lblClienteID.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblClienteID);
        txtClienteID = new JTextField(esActualizar ? String.valueOf(entrada.getClienteID()) : "");
        txtClienteID.setBounds(150, y, 300, 25);
        txtClienteID.setOpaque(true);
        txtClienteID.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtClienteID);

        y += 40;
        JLabel lblPrecioEntradaID = new JLabel("Precio Entrada ID:");
        lblPrecioEntradaID.setBounds(50, y, 100, 25);
        lblPrecioEntradaID.setOpaque(true);
        lblPrecioEntradaID.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblPrecioEntradaID);
        txtPrecioEntradaID = new JTextField(esActualizar ? String.valueOf(entrada.getPrecioEntradaID()) : "");
        txtPrecioEntradaID.setBounds(150, y, 300, 25);
        txtPrecioEntradaID.setOpaque(true);
        txtPrecioEntradaID.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtPrecioEntradaID);

        y += 40;
        JLabel lblNumeroFila = new JLabel("Fila:");
        lblNumeroFila.setBounds(50, y, 100, 25);
        lblNumeroFila.setOpaque(true);
        lblNumeroFila.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblNumeroFila);
        txtNumeroFila = new JTextField(esActualizar ? entrada.getNumeroFila() : "");
        txtNumeroFila.setBounds(150, y, 300, 25);
        txtNumeroFila.setOpaque(true);
        txtNumeroFila.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtNumeroFila);

        y += 40;
        JLabel lblNumeroAsiento = new JLabel("Asiento:");
        lblNumeroAsiento.setBounds(50, y, 100, 25);
        lblNumeroAsiento.setOpaque(true);
        lblNumeroAsiento.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblNumeroAsiento);
        txtNumeroAsiento = new JTextField(esActualizar ? String.valueOf(entrada.getNumeroAsiento()) : "");
        txtNumeroAsiento.setBounds(150, y, 300, 25);
        txtNumeroAsiento.setOpaque(true);
        txtNumeroAsiento.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtNumeroAsiento);

        y += 40;
        JLabel lblFechaVenta = new JLabel("Fecha Venta (yyyy-MM-dd HH:mm):");
        lblFechaVenta.setBounds(50, y, 200, 25);
        lblFechaVenta.setOpaque(true);
        lblFechaVenta.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblFechaVenta);
        txtFechaVenta = new JTextField(esActualizar && entrada.getFechaVenta() != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm").format(entrada.getFechaVenta()) : "");
        txtFechaVenta.setBounds(250, y, 200, 25);
        txtFechaVenta.setOpaque(true);
        txtFechaVenta.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtFechaVenta);

        y += 40;
        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(50, y, 100, 25);
        lblEstado.setOpaque(true);
        lblEstado.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblEstado);
        txtEstado = new JTextField(esActualizar ? entrada.getEstado() : "");
        txtEstado.setBounds(150, y, 300, 25);
        txtEstado.setOpaque(true);
        txtEstado.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtEstado);

        // Buttons
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

        desktop.add(this);
        setVisible(true);
    }

    private void guardarEntrada() {
        try {
            Entrada entradaToSave = new Entrada();
            if (esActualizar) {
                entradaToSave.setId(entrada.getId());
            }
            entradaToSave.setFuncionID(Integer.parseInt(txtFuncionID.getText().trim()));
            entradaToSave.setClienteID(Integer.parseInt(txtClienteID.getText().trim()));
            entradaToSave.setPrecioEntradaID(Integer.parseInt(txtPrecioEntradaID.getText().trim()));
            entradaToSave.setNumeroFila(txtNumeroFila.getText().trim());
            entradaToSave.setNumeroAsiento(Integer.parseInt(txtNumeroAsiento.getText().trim()));
            entradaToSave.setFechaVenta(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(txtFechaVenta.getText().trim()));
            entradaToSave.setEstado(txtEstado.getText().trim());

            boolean success;
            if (esActualizar) {
                success = controlador.actualizar(entradaToSave);
                JOptionPane.showMessageDialog(this, success ? "Entrada actualizada con éxito" : "Error al actualizar la entrada", 
                    success ? "Éxito" : "Error", success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
            } else {
                success = controlador.registrar(entradaToSave);
                JOptionPane.showMessageDialog(this, success ? "Entrada registrada con éxito" : "Error al registrar la entrada", 
                    success ? "Éxito" : "Error", success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
            }

            if (success) {
                dispose();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos para los IDs y el asiento", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (java.text.ParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use yyyy-MM-dd HH:mm", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}