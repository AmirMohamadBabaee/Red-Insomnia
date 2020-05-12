package RedInsomnia.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * RedInsomnia.gui.OptionFrame
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
    private JButton applyButton;

    /**
     * Constructor of RedInsomnia.gui.OptionFrame in RedInsomnia
     *
     * @param theme theme of this frame
     * @param mainPanel main panel of this program
     */
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

        add(createFollowRedirectState(theme, mainPanel));
        add(createCloseOperationState(theme, mainPanel));
        add(createSelectedTheme(theme, mainPanel));
        add(createApplyButton());

        setLayout(new BorderLayout());
        setVisible(true);

    }

    /**
     * getter of FollowRedirectState
     *
     * @return state of follow redirect
     */
    public JCheckBox getFollowRedirectState() {
        return followRedirectState;
    }

    /**
     * getter of CloseOperationState
     *
     * @return state of close operation
     */
    public JCheckBox getCloseOperationState() {
        return closeOperationState;
    }

    /**
     * getter of SelectedTheme
     *
     * @return selected theme
     */
    public List<JRadioButton> getSelectedTheme() {
        return selectedTheme;
    }

    /**
     * getter of ApplyButton
     *
     * @return apply button
     */
    public JButton getApplyButton() {
        return applyButton;
    }

    /**
     * this method create follow redirect panel
     *
     * @param theme theme of this frame
     * @param mainFrame main frame of this program
     * @return prepared panel of followRedirect
     */
    private JPanel createFollowRedirectState(int theme, JFrame mainFrame) {

        JPanel followRedirectPanel = new JPanel();
        followRedirectPanel.setBorder(BorderFactory.createTitledBorder("Follow Redirect"));
        followRedirectPanel.setBounds(5, 5, 585, 100);

        followRedirectState = new JCheckBox();
        followRedirectState.setText("follow redirect");
        followRedirectState.setAlignmentX(Component.LEFT_ALIGNMENT);

        if(((MainFrame)mainFrame).isFollowDirect()) {
            followRedirectState.setSelected(true);
        } else {
            followRedirectState.setSelected(false);
        }

        followRedirectPanel.add(followRedirectState);

        return followRedirectPanel;

    }


    /**
     * this method create closeOperation panel
     *
     * @param theme theme of this frame
     * @param mainFrame main frame of this program
     * @return prepared panel of close operation
     */
    private JPanel createCloseOperationState(int theme, JFrame mainFrame) {

        JPanel closeOperationPanel = new JPanel();
        closeOperationPanel.setBorder(BorderFactory.createTitledBorder("Close Operation Type"));
        closeOperationPanel.setBounds(5, 110, 585, 100);

        closeOperationState = new JCheckBox();
        closeOperationState.setText("Hide on System Tray");
        closeOperationState.setAlignmentX(Component.LEFT_ALIGNMENT);

        if(((MainFrame)mainFrame).isCloseOperation()) {
            closeOperationState.setSelected(true);
        } else {
            closeOperationState.setSelected(false);
        }

        closeOperationPanel.add(closeOperationState);

        return closeOperationPanel;

    }

    /**
     * this method create select theme part in optionFrame
     *
     * @param theme theme of this frame
     * @param mainFrame main frame of this program
     * @return prepared panel of select theme part
     */
    private JPanel createSelectedTheme(int theme, JFrame mainFrame) {

        JPanel selectedThemePanel = new JPanel();
        selectedThemePanel.setBorder(BorderFactory.createTitledBorder("Theme"));
        selectedThemePanel.setBounds(5, 215, 585, 100);

        selectedTheme.add(new JRadioButton("Light"));
        selectedTheme.add(new JRadioButton("Dark"));

        if(((MainFrame)mainFrame).getTheme() == MainFrame.LIGHT_THEME) {

            selectedTheme.get(0).setSelected(true);
            selectedTheme.get(1).setSelected(false);

        } else if(((MainFrame)mainFrame).getTheme() == MainFrame.DARK_THEME) {

            selectedTheme.get(0).setSelected(false);
            selectedTheme.get(1).setSelected(true);

        }

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


    /**
     * this method create gui of apply button
     *
     * @return prepared apply button
     */
    private JButton createApplyButton() {

        applyButton = new JButton("Apply");
        applyButton.setFont(new Font("Santa Fe Let", Font.PLAIN, 16));
        applyButton.setBounds(510, 320, 80, 40);
        applyButton.setContentAreaFilled(false);
        applyButton.setOpaque(true);

        return applyButton;

    }

}
