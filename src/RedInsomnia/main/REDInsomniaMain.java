package RedInsomnia.main;

import RedInsomnia.gui.MainFrame;

import javax.swing.*;

public class REDInsomniaMain {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new MainFrame("RedInsomnia", MainFrame.LIGHT_THEME));

    }

    public static void startAgain(int theme) {

        SwingUtilities.invokeLater(() -> new MainFrame("RedInsomnia", theme));

    }
}
