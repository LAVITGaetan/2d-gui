import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JPanel {

    private int x = 100;
    private int y = 100;
    private final int taille = 50;

    private boolean menuVisible = true;
    private String[] menuOptions = { "Play", "Options", "Quit" };
    private boolean inOptionsMenu = false;
    private int menuSelection = 0;
    private boolean isFullScreen = false;

    public Main() {
        JFrame frame = new JFrame("2D Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(this);
        frame.setVisible(true);

        setBackground(Color.BLACK);
        setFocusable(true);
        SoundManager.playTheme();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                if (menuVisible) {
                    // Menu nav controls
                    switch (key) {
                        case KeyEvent.VK_UP:
                            menuSelection--;
                            if (menuSelection < 0)
                                menuSelection = menuOptions.length - 1;
                            SoundManager.playNavigate();
                            break;
                        case KeyEvent.VK_DOWN:
                            menuSelection++;
                            if (menuSelection >= menuOptions.length)
                                menuSelection = 0;
                            SoundManager.playNavigate();
                            break;
                        case KeyEvent.VK_ENTER:
                            selectMenuOption();
                            SoundManager.playSelect();
                            break;
                    }
                } else {
                    // Character controls
                    switch (key) {
                        case KeyEvent.VK_LEFT:
                            x -= 10;
                            break;
                        case KeyEvent.VK_RIGHT:
                            x += 10;
                            break;
                        case KeyEvent.VK_UP:
                            y -= 10;
                            break;
                        case KeyEvent.VK_DOWN:
                            y += 10;
                            break;
                    }
                }

                if (key == KeyEvent.VK_ESCAPE) {
                    menuVisible = !menuVisible;
                }

                repaint();
            }
        });
    }

    private void selectMenuOption() {
        if (!inOptionsMenu) {
            // Continue action
            if (menuSelection == 0) {
                menuVisible = false;
            }
            // Options action
            else if (menuSelection == 1) {
                inOptionsMenu = true;
                menuOptions = new String[] { "Plein écran", "Contrôles", "Retour" };
                menuSelection = 0;
            }
            // Quit action
            else if (menuSelection == 2) {
                System.exit(0);
            }
        } else {
            // Full screen action
            if (menuOptions[menuSelection].equals("Plein écran")) {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                topFrame.dispose();
                if (!isFullScreen) {
                    topFrame.setUndecorated(true);
                    topFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    isFullScreen = true;
                } else {
                    topFrame.setUndecorated(false);
                    topFrame.setExtendedState(JFrame.NORMAL);
                    topFrame.setSize(800, 600);
                    isFullScreen = false;
                }
                topFrame.setVisible(true);
            }
            // Return action
            if (menuOptions[menuSelection].equals("Retour")) {
                inOptionsMenu = false;
                menuOptions = new String[] { "Continuer", "Options" };
                menuSelection = 0;
            }

        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Character
        g.setColor(Color.WHITE);
        g.fillRect(x, y, taille, taille);

        if (menuVisible) {
            // Background
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, getWidth(), getHeight());

            // Fonts
            try {
                Font menuFont = Font.createFont(Font.TRUETYPE_FONT,
                        getClass().getResourceAsStream("/assets/fonts/PressStart2P-Regular.ttf"));
                menuFont = menuFont.deriveFont(Font.BOLD, 26);
                g.setFont(menuFont);
            } catch (Exception e) {
                g.setFont(new Font("Dialog", Font.BOLD, 36));
            }

            // Center menu
            FontMetrics fm = g.getFontMetrics();
            for (int i = 0; i < menuOptions.length; i++) {
                String text = menuOptions[i];
                int textWidth = fm.stringWidth(text);
                int xPos = (getWidth() - textWidth) / 2;
                int yPos = 200 + i * 50;
                if (i == menuSelection) {
                    // Current option
                    g.setColor(Color.decode("#441294"));
                } else {
                    // Default option
                    g.setColor(Color.WHITE);
                }
                g.drawString(text, xPos, yPos);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
