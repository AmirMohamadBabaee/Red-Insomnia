import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * OptionFrame
 *
 * This class is frame of options in RedInsomnia.
 * in this frame there are 3 part; first part related to
 * follow redirect setting that handle with a checkbox.
 * second one related to function of close button.
 * and last one related to theme of RedInsomnia
 *
 * @author Amir01
 * @version
 *
 * @see JFrame
 */
public class OptionFrame extends JFrame {

    private JCheckBox followRedirectState;
    private JCheckBox closeOperationState;
    private List<JRadioButton> selectedTheme;

    public OptionFrame(int theme, JFrame mainPanel) {

        super();
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        SwingUtilities.updateComponentTreeUI(this);

        selectedTheme = new ArrayList<>();

        setTitle("Options");
        setSize(new Dimension(600, 400));
        setResizable(false);
        setLocationRelativeTo(mainPanel);
        Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
        setIconImage(icon);

        add(createFollowRedirectState(theme));
        add(createCloseOperationState(theme));
        add(createSelectedTheme(theme));
        add(createApplyButton());

        setLayout(new BorderLayout());
        setVisible(true);

    }


    private JPanel createFollowRedirectState(int theme) {

        JPanel followRedirectPanel = new JPanel();
        followRedirectPanel.setBorder(BorderFactory.createTitledBorder("Follow Redirect"));
        followRedirectPanel.setBounds(5, 5, 585, 100);

        followRedirectState = new JCheckBox();
        followRedirectState.setText("follow redirect");
        followRedirectState.setAlignmentX(Component.LEFT_ALIGNMENT);

        followRedirectPanel.add(followRedirectState);

        return followRedirectPanel;

    }


    private JPanel createCloseOperationState(int theme) {

        JPanel closeOperationPanel = new JPanel();
        closeOperationPanel.setBorder(BorderFactory.createTitledBorder("Close Operation Type"));
        closeOperationPanel.setBounds(5, 110, 585, 100);

        closeOperationState = new JCheckBox();
        closeOperationState.setText("Hide on System Tray");
        closeOperationState.setAlignmentX(Component.LEFT_ALIGNMENT);

        closeOperationPanel.add(closeOperationState);

        return closeOperationPanel;

    }


    private JPanel createSelectedTheme(int theme) {

        JPanel selectedThemePanel = new JPanel();
        selectedThemePanel.setBorder(BorderFactory.createTitledBorder("Theme"));
        selectedThemePanel.setBounds(5, 215, 585, 100);

        selectedTheme.add(new JRadioButton("Light"));
        selectedTheme.add(new JRadioButton("Dark"));

        selectedTheme.get(0).setHorizontalAlignment(SwingConstants.CENTER);
        selectedTheme.get(1).setHorizontalAlignment(SwingConstants.CENTER);
        ButtonGroup bgroup1 = new ButtonGroup();
        bgroup1.add(selectedTheme.get(0));
        bgroup1.add(selectedTheme.get(1));
        JPanel radioButtonPanel = new JPanel();
        BoxLayout radioButtonPanelLayout = new BoxLayout(radioButtonPanel, BoxLayout.X_AXIS);
        radioButtonPanel.setLayout(radioButtonPanelLayout);
        radioButtonPanel.add(selectedTheme.get(0));
        radioButtonPanel.add(Box.createRigidArea(new Dimension(100, 0)));
        radioButtonPanel.add(selectedTheme.get(1));
        selectedThemePanel.add(radioButtonPanel);

        return selectedThemePanel;

    }


    private JButton createApplyButton() {

        JButton applyButton = new JButton("Apply");
        applyButton.setFont(new Font("Santa Fe Let", Font.PLAIN, 16));
        applyButton.setBounds(510, 320, 80, 40);
        applyButton.setContentAreaFilled(false);
        applyButton.setOpaque(true);

        return applyButton;

    }

}
