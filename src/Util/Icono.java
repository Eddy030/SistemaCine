package Util;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JFrame;
import java.util.logging.Logger;

public class Icono {
    private static final Logger LOGGER = Logger.getLogger(Icono.class.getName());
    private static final String ICON_PATH = "/img/Cineplanet_Icon.png";

    public static void establecerIcono(JFrame frame) {
        try {
            java.net.URL imgURL = Icono.class.getResource(ICON_PATH);
            if (imgURL != null) {
                ImageIcon icono = new ImageIcon(imgURL);
                if (icono.getImage() != null) {
                    frame.setIconImage(icono.getImage());
                } else {
                    LOGGER.warning("El ícono no se pudo cargar: " + ICON_PATH);
                }
            } else {
                LOGGER.warning("No se encontró el recurso de ícono: " + ICON_PATH);
            }
        } catch (Exception e) {
            LOGGER.severe("Error al cargar el ícono para JFrame: " + e.getMessage());
        }
    }

    public static void establecerIcono(JInternalFrame frame) {
        try {
            java.net.URL imgURL = Icono.class.getResource(ICON_PATH);
            if (imgURL != null) {
                ImageIcon icono = new ImageIcon(imgURL);
                if (icono.getImage() != null) {
                    frame.setFrameIcon(icono);
                } else {
                    LOGGER.warning("El ícono no se pudo cargar: " + ICON_PATH);
                }
            } else {
                LOGGER.warning("No se encontró el recurso de ícono: " + ICON_PATH);
            }
        } catch (Exception e) {
            LOGGER.severe("Error al cargar el ícono para JInternalFrame: " + e.getMessage());
        }
    }
}