package esfe.presentacion;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class SwingUtils {
    /**
     * Aplica un estilo inspirado en Material Design (Azul) a un JButton.
     * @param button El botón al que se le aplicará el estilo.
     */
    public static  void ConfigInicial(){
        Font fuenteBotones = new Font("Segoe UI", Font.BOLD, 14);
        Color colorTextoBoton = Color.WHITE;
        Color colorFondoBoton = new Color(30, 144, 255); // Azul Material

        // 2. Registrar los estilos en el Administrador de UI de Swing
        UIManager.put("Button.font", fuenteBotones);
        UIManager.put("Button.foreground", colorTextoBoton);
        UIManager.put("Button.background", colorFondoBoton);

        // Opcional: Quitar el molesto recuadro de foco de los botones de forma global
        UIManager.put("Button.focusPainted", false);

        Color azulFondoGlobal = new Color(30, 144, 255); // O el color de tu preferencia

        // 2. Registrar el color de fondo para Paneles y Paneles de Contenido
       // UIManager.put("Panel.background", azulFondoGlobal);
       // UIManager.put("OptionPane.background", azulFondoGlobal);
    }
    public static void elgirEstiloMaterialAzul(JButton button) {
        // Colores oficiales de la paleta Material Design (Blue 600 y Blue 700)
        Color azulNormal = new Color(30, 144, 255); // #1E90FF - Azul primario vibrante
        Color azulHover = new Color(28, 120, 242);  // Un poco más oscuro para cuando el mouse pasa encima
        Color azulPressed = new Color(21, 101, 192); // Aún más oscuro para cuando se hace click

        // Configuración de fuentes y visibilidad base
        button.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Fuente limpia y moderna
        button.setForeground(Color.WHITE); // Texto blanco
        button.setBackground(azulNormal);

        // Propiedades críticas de Swing para quitar el aspecto antiguo del sistema operativo
        button.setContentAreaFilled(false); // Desactiva el pintado por defecto de Swing
        button.setOpaque(true);             // Permite que nuestro color de fondo personalizado sea visible
        button.setFocusPainted(false);      // Quita el molesto recuadro de puntos cuando el botón está seleccionado
        button.setBorderPainted(false);     // Quita el borde nativo tridimensional

        // Margen interno (Padding): 10px arriba/abajo, 25px a los lados para que se vea alargado y moderno
        button.setBorder(new EmptyBorder(10, 25, 10, 25));

        // Cambiar el cursor a la mano de selección al pasar sobre el botón
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Eventos del Mouse para simular el comportamiento dinámico de Material Design
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Efecto Hover (El ratón entra)
                button.setBackground(azulHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // El ratón sale (vuelve al estado original)
                button.setBackground(azulNormal);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Efecto Click (Botón presionado)
                button.setBackground(azulPressed);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Al soltar el click, regresa al color Hover si el mouse sigue dentro
                if (button.getBounds().contains(e.getPoint())) {
                    button.setBackground(azulHover);
                } else {
                    button.setBackground(azulNormal);
                }
            }
        });
    }
    /**
     * Aplica un diseño moderno con bordes redondeados y efecto de enfoque (Focus)
     * a cualquier caja de texto (JTextField o JPasswordField).
     * * @param textField El componente de texto a estilizar.
     * @param radioCurva El nivel de redondeado de las esquinas (p. ej., 15 o 20).
     */
    public static void aplicarEstiloRedondeado(JTextComponent textField, int radioCurva) {
        // Colores de diseño
        Color colorFondo = Color.WHITE;     // Gris muy claro y limpio
        Color colorBordeNormal = new Color(200, 205, 215); // Borde gris sutil
        Color colorBordeFocus = new Color(30, 144, 255);   // Azul Material al hacer clic
        Color colorTexto = new Color(40, 40, 40);         // Gris oscuro para el texto

        // Configuración básica del componente
        textField.setOpaque(false); // CRÍTICO: Evita que Swing pinte el fondo rectangular nativo
        textField.setForeground(colorTexto);
        textField.setCaretColor(colorBordeFocus); // Color de la barra parpadeante
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Margen interno (Padding): 8px arriba/abajo, 12px a los lados para separar el texto de la curva
        textField.setBorder(new EmptyBorder(8, 12, 8, 12));

        // Usamos un array de un solo elemento para mantener los estados del color del borde de forma dinámica
        final Color[] colorBordeActual = {colorBordeNormal};

        // Eventos para cambiar el color del borde cuando el usuario hace clic (Focus)
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                colorBordeActual[0] = colorBordeFocus;
                textField.repaint(); // Redibuja el componente con el nuevo color
            }

            @Override
            public void focusLost(FocusEvent e) {
                colorBordeActual[0] = colorBordeNormal;
                textField.repaint(); // Vuelve al color original
            }
        });

        // Reemplazamos la UI del componente para dibujar el fondo y borde redondeados de forma personalizada
        textField.setUI(new javax.swing.plaf.basic.BasicTextFieldUI() {
            @Override
            protected void paintBackground(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                // Activar Antialiasing para curvas ultra suaves y profesionales
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = textField.getWidth();
                int height = textField.getHeight();

                // 1. Pintar el fondo interno redondeado
                g2d.setColor(colorFondo);
                g2d.fillRoundRect(0, 0, width - 1, height - 1, radioCurva, radioCurva);

                // 2. Pintar el contorno/borde redondeado (grosor de 1.5 píxeles para verse fino)
                g2d.setColor(colorBordeActual[0]);
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawRoundRect(0, 0, width - 1, height - 1, radioCurva, radioCurva);

                g2d.dispose();
            }
        });
    }
}
