import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * OptionController
 *
 * This class implement listener of Option frame's components.
 *
 * @author Amir01
 * @version
 *
 * @see OptionFrame
 */
public class OptionController {

    private OptionFrame optionFrame;
    private String currentDir;

    public OptionController(int theme, JFrame mainPanel, String currentDir) {

        this.currentDir = currentDir;
        optionFrame = new OptionFrame(theme, mainPanel);
        initController(mainPanel);

    }

    public void initController(JFrame mainFrame) {

        optionFrame.getFollowRedirectState().addItemListener(e -> {
            if(e.getItemSelectable() == optionFrame.getFollowRedirectState() &&
                    optionFrame.getFollowRedirectState().isSelected()) {

                // Todo

            } else if(e.getItemSelectable() == optionFrame.getFollowRedirectState() &&
                    !optionFrame.getFollowRedirectState().isSelected()) {

                // Todo

            }
        });

        optionFrame.getCloseOperationState().addItemListener(e -> {

           if(e.getItemSelectable() == optionFrame.getCloseOperationState() &&
                   optionFrame.getCloseOperationState().isSelected()) {

               mainFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
               mainFrame.revalidate();
               mainFrame.repaint();
               System.out.println(mainFrame.getDefaultCloseOperation());

           } else if(e.getItemSelectable() == optionFrame.getCloseOperationState() &&
                    !optionFrame.getCloseOperationState().isSelected()){

               mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               mainFrame.revalidate();
               mainFrame.repaint();
               System.out.println(mainFrame.getDefaultCloseOperation());

           }

        });

        Color currentColor = optionFrame.getApplyButton().getBackground();
        optionFrame.getApplyButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {

                optionFrame.getApplyButton().setBackground(new Color(200, 200, 200));
                optionFrame.getApplyButton().setForeground(Color.white);

            }

            @Override
            public void mouseExited(MouseEvent e) {

                optionFrame.getApplyButton().setBackground(currentColor);
                optionFrame.getApplyButton().setForeground(Color.black);

            }
        });

        optionFrame.getApplyButton().addActionListener(e -> {

            int result = JOptionPane.showConfirmDialog(optionFrame, "The program need to be restarted!\nAre you sure you want apply new settting?",
                    "Warning", JOptionPane.YES_NO_OPTION);

            if(result == JOptionPane.YES_OPTION) {

                try (FileOutputStream out = new FileOutputStream(currentDir + "\\data\\options")){

                    if(optionFrame.getFollowRedirectState().isSelected()) {

                        out.write(1);
                        ((MainFrame)mainFrame).setFollowDirect(true);

                    } else if(!optionFrame.getFollowRedirectState().isSelected()) {

                        out.write(0);
                        ((MainFrame)mainFrame).setFollowDirect(false);

                    }

                    if(optionFrame.getCloseOperationState().isSelected()) {

                        out.write(1);
                        ((MainFrame)mainFrame).setCloseOperation(true);

                    } else if(!optionFrame.getCloseOperationState().isSelected()) {

                        out.write(0);
                        ((MainFrame)mainFrame).setCloseOperation(false);

                    }

                    if(optionFrame.getSelectedTheme().get(0).isSelected()) {

                        out.write(1);
                        ((MainFrame)mainFrame).setTheme(MainFrame.LIGHT_THEME);

                    } else if(!optionFrame.getSelectedTheme().get(0).isSelected()) {

                        out.write(0);

                    }

                    if(optionFrame.getSelectedTheme().get(1).isSelected()) {

                        out.write(1);
                        ((MainFrame)mainFrame).setTheme(MainFrame.DARK_THEME);

                    } else if(!optionFrame.getSelectedTheme().get(1).isSelected()) {

                        out.write(0);

                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                optionFrame.dispose();
                Main.startAgain(((MainFrame)mainFrame).getTheme());
                mainFrame.dispose();

            }

        });

    }
}
