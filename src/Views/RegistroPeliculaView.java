package Views;

import Controllers.CRUDPelicula;
import Models.Pelicula;
import Util.Icono;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RegistroPeliculaView extends JFrame {
    private JTextField txtTitulo, txtGenero, txtDirector, txtIdiomaOriginal, txtSubtitulosDisponibles,
                      txtDuracionMinutos, txtClasificacion, txtFechaLanzamiento;
    private JTextArea txtSinopsis, txtActoresPrincipales;
    private JButton btnRegistrar, btnVolver;
    private JPanel mainPanel;
    private LogoPanel logoPanel;
    private CRUDPelicula peliculaController;
    private boolean registroExitoso;
    private String context;

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

    public RegistroPeliculaView(String context, Pelicula pelicula) {
        this.context = context;
        setTitle(context.equals("add") ? "Registrar Película" : "Actualizar Película");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(600, 750);
        setLocationRelativeTo(null);
        registroExitoso = false;
        peliculaController = new CRUDPelicula();

        Icono.establecerIcono(this);

        mainPanel = new MainPanel("/img/FondoBlancoGigante.png");
        mainPanel.setLayout(null);
        setContentPane(mainPanel);

        logoPanel = new LogoPanel("/img/CineplanetLogoV2.png");
        logoPanel.setBounds(170, 20, 260, 130);
        mainPanel.add(logoPanel);

        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setBounds(50, 160, 120, 25);
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblTitulo);
        txtTitulo = new JTextField();
        txtTitulo.setBounds(180, 160, 350, 25);
        txtTitulo.setOpaque(true);
        txtTitulo.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtTitulo);

        JLabel lblSinopsis = new JLabel("Sinopsis:");
        lblSinopsis.setBounds(50, 200, 120, 25);
        lblSinopsis.setOpaque(true);
        lblSinopsis.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblSinopsis);
        txtSinopsis = new JTextArea();
        txtSinopsis.setLineWrap(true);
        txtSinopsis.setWrapStyleWord(true);
        JScrollPane sinopsisScroll = new JScrollPane(txtSinopsis);
        sinopsisScroll.setBounds(180, 200, 350, 60);
        sinopsisScroll.setOpaque(true);
        sinopsisScroll.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(sinopsisScroll);

        JLabel lblGenero = new JLabel("Género:");
        lblGenero.setBounds(50, 270, 120, 25);
        lblGenero.setOpaque(true);
        lblGenero.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblGenero);
        txtGenero = new JTextField();
        txtGenero.setBounds(180, 270, 350, 25);
        txtGenero.setOpaque(true);
        txtGenero.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtGenero);

        JLabel lblDirector = new JLabel("Director:");
        lblDirector.setBounds(50, 310, 120, 25);
        lblDirector.setOpaque(true);
        lblDirector.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblDirector);
        txtDirector = new JTextField();
        txtDirector.setBounds(180, 310, 350, 25);
        txtDirector.setOpaque(true);
        txtDirector.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtDirector);

        JLabel lblActoresPrincipales = new JLabel("Actores Principales:");
        lblActoresPrincipales.setBounds(50, 350, 120, 25);
        lblActoresPrincipales.setOpaque(true);
        lblActoresPrincipales.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblActoresPrincipales);
        txtActoresPrincipales = new JTextArea();
        txtActoresPrincipales.setLineWrap(true);
        txtActoresPrincipales.setWrapStyleWord(true);
        JScrollPane actoresScroll = new JScrollPane(txtActoresPrincipales);
        actoresScroll.setBounds(180, 350, 350, 60);
        actoresScroll.setOpaque(true);
        actoresScroll.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(actoresScroll);

        JLabel lblFechaLanzamiento = new JLabel("Fecha Lanzamiento:");
        lblFechaLanzamiento.setBounds(50, 420, 120, 25);
        lblFechaLanzamiento.setOpaque(true);
        lblFechaLanzamiento.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblFechaLanzamiento);
        txtFechaLanzamiento = new JTextField("YYYY-MM-DD");
        txtFechaLanzamiento.setBounds(180, 420, 350, 25);
        txtFechaLanzamiento.setOpaque(true);
        txtFechaLanzamiento.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtFechaLanzamiento);

        JLabel lblIdiomaOriginal = new JLabel("Idioma Original:");
        lblIdiomaOriginal.setBounds(50, 460, 120, 25);
        lblIdiomaOriginal.setOpaque(true);
        lblIdiomaOriginal.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblIdiomaOriginal);
        txtIdiomaOriginal = new JTextField();
        txtIdiomaOriginal.setBounds(180, 460, 350, 25);
        txtIdiomaOriginal.setOpaque(true);
        txtIdiomaOriginal.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtIdiomaOriginal);

        JLabel lblSubtitulosDisponibles = new JLabel("Subtítulos:");
        lblSubtitulosDisponibles.setBounds(50, 500, 120, 25);
        lblSubtitulosDisponibles.setOpaque(true);
        lblSubtitulosDisponibles.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblSubtitulosDisponibles);
        txtSubtitulosDisponibles = new JTextField();
        txtSubtitulosDisponibles.setBounds(180, 500, 350, 25);
        txtSubtitulosDisponibles.setOpaque(true);
        txtSubtitulosDisponibles.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtSubtitulosDisponibles);

        JLabel lblDuracionMinutos = new JLabel("Duración (min):");
        lblDuracionMinutos.setBounds(50, 540, 120, 25);
        lblDuracionMinutos.setOpaque(true);
        lblDuracionMinutos.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblDuracionMinutos);
        txtDuracionMinutos = new JTextField();
        txtDuracionMinutos.setBounds(180, 540, 350, 25);
        txtDuracionMinutos.setOpaque(true);
        txtDuracionMinutos.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtDuracionMinutos);

        JLabel lblClasificacion = new JLabel("Clasificación:");
        lblClasificacion.setBounds(50, 580, 120, 25);
        lblClasificacion.setOpaque(true);
        lblClasificacion.setBackground(new Color(255, 255, 255, 200));
        mainPanel.add(lblClasificacion);
        txtClasificacion = new JTextField();
        txtClasificacion.setBounds(180, 580, 350, 25);
        txtClasificacion.setOpaque(true);
        txtClasificacion.setBackground(new Color(255, 255, 255, 230));
        mainPanel.add(txtClasificacion);

        btnRegistrar = new JButton(context.equals("add") ? "Registrar" : "Actualizar");
        btnRegistrar.setBounds(120, 620, 130, 30);
        btnRegistrar.setBackground(new Color(0, 123, 255));
        btnRegistrar.setFocusPainted(false);
        mainPanel.add(btnRegistrar);

        btnVolver = new JButton("Cancelar");
        btnVolver.setBounds(300, 620, 130, 30);
        btnVolver.setBackground(new Color(0, 123, 255));
        btnVolver.setFocusPainted(false);
        mainPanel.add(btnVolver);

        // Prellenar campos si se proporciona una película
        if (pelicula != null) {
            txtTitulo.setText(pelicula.getTitulo());
            txtSinopsis.setText(pelicula.getSinopsis());
            txtGenero.setText(pelicula.getGenero());
            txtDirector.setText(pelicula.getDirector());
            txtActoresPrincipales.setText(pelicula.getActoresPrincipales());
            txtFechaLanzamiento.setText(pelicula.getFechaLanzamiento() != null ?
                pelicula.getFechaLanzamiento().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "YYYY-MM-DD");
            txtIdiomaOriginal.setText(pelicula.getIdiomaOriginal());
            txtSubtitulosDisponibles.setText(pelicula.getSubtitulosDisponibles());
            txtDuracionMinutos.setText(String.valueOf(pelicula.getDuracionMinutos()));
            txtClasificacion.setText(pelicula.getClasificacion());
        }

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarPelicula(pelicula != null ? pelicula.getId() : 0);
            }
        });

        btnVolver.addActionListener(e -> dispose());
    }

    private void registrarPelicula(int id) {
        try {
            String titulo = txtTitulo.getText().trim();
            String sinopsis = txtSinopsis.getText().trim();
            String genero = txtGenero.getText().trim();
            String director = txtDirector.getText().trim();
            String actoresPrincipales = txtActoresPrincipales.getText().trim();
            String fechaLanzamiento = txtFechaLanzamiento.getText().trim();
            String idiomaOriginal = txtIdiomaOriginal.getText().trim();
            String subtitulosDisponibles = txtSubtitulosDisponibles.getText().trim();
            String duracionMinutos = txtDuracionMinutos.getText().trim();
            String clasificacion = txtClasificacion.getText().trim();

            // Validaciones
            if (titulo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El título no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (genero.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El género no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (idiomaOriginal.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El idioma original no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (duracionMinutos.isEmpty() || !duracionMinutos.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "La duración debe ser un número positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (clasificacion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La clasificación no puede estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!fechaLanzamiento.equals("YYYY-MM-DD") && !fechaLanzamiento.matches("\\d{4}-\\d{2}-\\d{2}")) {
                JOptionPane.showMessageDialog(this, "La fecha de lanzamiento debe tener el formato YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Pelicula pelicula = new Pelicula();
            pelicula.setId(id);
            pelicula.setTitulo(titulo);
            pelicula.setSinopsis(sinopsis);
            pelicula.setGenero(genero);
            pelicula.setDirector(director);
            pelicula.setActoresPrincipales(actoresPrincipales);
            pelicula.setFechaLanzamiento(fechaLanzamiento.equals("YYYY-MM-DD") ? null :
                LocalDate.parse(fechaLanzamiento, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            pelicula.setIdiomaOriginal(idiomaOriginal);
            pelicula.setSubtitulosDisponibles(subtitulosDisponibles);
            pelicula.setDuracionMinutos(Integer.parseInt(duracionMinutos));
            pelicula.setClasificacion(clasificacion);

            boolean exito;
            if (context.equals("add")) {
                exito = peliculaController.registrar(pelicula);
            } else {
                exito = peliculaController.actualizar(pelicula);
            }

            if (exito) {
                registroExitoso = true;
                JOptionPane.showMessageDialog(this, context.equals("add") ?
                    "Película registrada exitosamente" : "Película actualizada exitosamente");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al procesar la película. Verifique los datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La duración debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al procesar la película: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isRegistroExitoso() {
        return registroExitoso;
    }
}