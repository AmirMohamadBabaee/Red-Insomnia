package RedInsomnia.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * RedInsomnia.gui.AboutFrame
 *
 * This class define new frame for about menuItem
 *
 * @author Amir01
 * @version
 *
 * @see MainFrame
 * @see JFrame
 */
public class AboutFrame extends JFrame {

    public AboutFrame(MainFrame mainFrame, int theme) {

        super();

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        SwingUtilities.updateComponentTreeUI(this);

        setTitle("About Me");
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(mainFrame);
        Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
        setIconImage(icon);
        setLayout(null);
        setVisible(true);

        setLayout(new BorderLayout());

        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File(mainFrame.getCurrentDir() + "\\resource\\about-me.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert myPicture != null;
        JLabel aboutMePic = new JLabel(new ImageIcon(myPicture));
        add(aboutMePic, BorderLayout.NORTH);

        JLabel developerName = new JLabel();
        developerName.setFont(new Font("Santa Fe Let", Font.PLAIN, 20));
        developerName.setText("Developer: AmirMohamad Babaee (Amir01)");
        developerName.setAlignmentX(Component.CENTER_ALIGNMENT);
        developerName.setHorizontalAlignment(JLabel.CENTER);

        JLabel developerID = new JLabel();
        developerID.setFont(new Font("Santa Fe Let", Font.PLAIN, 20));
        developerID.setText("Student ID: 9831011");
        developerID.setAlignmentX(Component.CENTER_ALIGNMENT);
        developerID.setHorizontalAlignment(JLabel.CENTER);

        JLabel developerEmail = new JLabel();
        developerEmail.setFont(new Font("Santa Fe Let", Font.PLAIN, 20));
        developerEmail.setText("Email: amir.babaee79@aut.ac.ir");
        developerEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
        developerEmail.setHorizontalAlignment(JLabel.CENTER);

        JPanel centerPanel = new JPanel(new GridLayout(0, 1));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(developerName);
        centerPanel.add(developerID);
        centerPanel.add(developerEmail);

        add(centerPanel, BorderLayout.CENTER);

    }

}
