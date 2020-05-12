package RedInsomnia.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * RedInsomnia.gui.HelpFrame
 *
 * This class define new frame for help menuItem
 *
 * @author Amir01
 * @version
 *
 * @see MainFrame
 * @see JFrame
 */
public class HelpFrame extends JFrame{

    public HelpFrame(MainFrame mainFrame, int theme) {

        super();

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        SwingUtilities.updateComponentTreeUI(this);

        setTitle("Help");
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(mainFrame);
        Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
        setIconImage(icon);
        setLayout(null);
        setVisible(true);

    }

}
