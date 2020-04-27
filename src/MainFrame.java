import com.sun.deploy.panel.JavaPanel;

import javax.swing.*;
import java.awt.*;

/**
 * This class is main Frame of insomnia app
 *
 * @author Amir01
 * @version
 */
public class MainFrame extends JFrame {

    // Field

    public static final int LIGHT_THEME = 0;
    public static final int DARK_THEME = 1;

    // Constructor

    public MainFrame(String title, int theme) {

        super();

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.updateComponentTreeUI(this);

        setTitle(title);
        setSize(1300, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        setContentPane(mainPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }


    private JPanel mainPanel() {

        JPanel mainPanel = new JPanel();

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(250, 580));
        leftPanel.setBorder(BorderFactory.createLineBorder(new Color(71, 72, 69), 1));

        JButton insomniaPart = new JButton("Insomnia");
        insomniaPart.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        insomniaPart.setBackground(new Color(105, 94, 184));
        insomniaPart.setForeground(Color.white);
        insomniaPart.setEnabled(false);
        insomniaPart.setPreferredSize(new Dimension(250, 75));
        insomniaPart.setContentAreaFilled(false);
        insomniaPart.setOpaque(true);
        leftPanel.add(insomniaPart, BorderLayout.NORTH);

        JPanel requestPanel = new JPanel();

        JPanel centerPanel = new JPanel();
        centerPanel.setPreferredSize(new Dimension(550, 580));
        centerPanel.setBorder(BorderFactory.createLineBorder(new Color(71, 72, 69), 1));

        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(450, 580));
        rightPanel.setBorder(BorderFactory.createLineBorder(new Color(71, 72, 69), 1));

        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        mainPanel.setLocation(0, 0);

        return mainPanel;

    }


}
