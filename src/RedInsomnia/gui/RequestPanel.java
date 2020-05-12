package RedInsomnia.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * RedInsomnia.gui.RequestPanel
 *
 * This class is for panels of each request in request List
 * panel in leftPanel. it can be a folder or a http request.
 *
 * @author Amir01
 * @version
 *
 * @see JPanel
 * @see MainFrame
 */
public class RequestPanel extends JButton {

    private static final long serialVersionUID = 6529685098267757690L;
    private JPanel buttonPanel;
    private JPanel coverPanel;
    private JLabel requestMethod;
    private JLabel requsetName;
    private JPopupMenu requestPopup;
    private String openMenu = "\uD83D\uDF83";
    private int theme;
    private String currentDir;
    private MainFrame mainFrame;
    private boolean select;

    private String method;
    private String name;

    /**
     * Constructor of RedInsomnia.gui.RequestPanel in RedInsomnia
     *
     * @param mainFrame main frame of this program
     * @param method method type of this request
     * @param name name of this request
     * @param requestList panel of all RedInsomnia.gui.RequestPanel
     */
    public RequestPanel(MainFrame mainFrame, String method, String name, JPanel requestList) {

        this.theme = mainFrame.getTheme();
        this.setRequestMethod(method);
        this.setRequestName(name);
        this.currentDir = mainFrame.getCurrentDir();


        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        coverPanel = new JPanel();

        coverPanel.setOpaque(true);
        coverPanel.setPreferredSize(new Dimension(100, 50));
        coverPanel.setLayout(new BoxLayout(coverPanel, BoxLayout.X_AXIS));


        requestMethod = new JLabel();
        requestMethod.setOpaque(false);
        requestMethod.setPreferredSize(new Dimension(100, 50));
        requestMethod.setText(this.getRequestMethod());
        requestMethod.setForeground(new Color(0, 255, 0));
        requestMethod.setFont(new Font("Santa Fe Let" , Font.PLAIN, 13));

        requsetName = new JLabel();
        requsetName.setOpaque(false);
        requsetName.setPreferredSize(new Dimension(200, 50));
        requsetName.setText(this.getRequestName());
        requsetName.setForeground(new Color(91, 92, 90));
        requsetName.setFont(new Font("Santa Fe Let", Font.PLAIN, 16));

        coverPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        coverPanel.add(requestMethod);
        coverPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        coverPanel.add(requsetName);
        coverPanel.add(Box.createRigidArea(new Dimension(200, 0)));

        coverPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        if(theme == MainFrame.LIGHT_THEME) {

            buttonPanel.setBackground(new Color(234, 234, 235));

            coverPanel.setBackground(new Color(234, 234, 235));

            this.setBackground(new Color(235, 235, 235));

        } else if(theme == MainFrame.DARK_THEME) {

            buttonPanel.setBackground(new Color(46, 47, 43));

            coverPanel.setBackground(new Color(46, 47, 43));

            this.setBackground(new Color(46, 47, 43));

        }

        this.setContentAreaFilled(false);
        this.setOpaque(true);

        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        buttonPanel.add(coverPanel);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        this.setAlignmentX(0.0f);
        this.setPreferredSize(new Dimension(30, 50));
        this.setToolTipText(this.getRequestMethod() + " - " + this.getRequestName());
        this.add(buttonPanel);

        requestPopup = new JPopupMenu();
        requestPopup.setPreferredSize(new Dimension(150, 50));

        JMenuItem delete = new JMenuItem("Delete");

        delete.addActionListener(e -> {
            requestList.remove(this);
            requestList.revalidate();
            requestList.repaint();

            try(FileOutputStream out = new FileOutputStream(currentDir + "\\data\\request_list")) {

                ObjectOutputStream outputStream = new ObjectOutputStream(out);
                System.out.println(requestList.getComponents().length);
                for (Component component : requestList.getComponents()) {

                    outputStream.writeObject(component);

                }

            } catch (IOException err) {
                err.printStackTrace();
            }

        });

        requestPopup.add(delete);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(e.getButton() == MouseEvent.BUTTON3 && !isSelect()) {

                    Component component = (Component)e.getSource();
                    requestPopup.show(RequestPanel.this, component.getX(), component.getY());

                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                restartColor(0);

            }

            @Override
            public void mouseExited(MouseEvent e) {

                if(!isSelect()) {

                    restartColor(1);

                }

            }
        });

    }


    /**
     * setter of selected state of this button
     *
     * @param selected select state of this button
     */
    public void setSelected(boolean selected) {
        this.select = selected;
    }

    /**
     * getter of select state of this button
     *
     * @return select state of this button
     */
    public boolean isSelect() {
        return select;
    }

    /**
     * getter of request method
     *
     * @return request method
     */
    public String getRequestMethod() {
        return method;
    }

    /**
     * getter of request name
     *
     * @return request name
     */
    public String getRequestName() {
        return name;
    }

    /**
     * setter of request method
     *
     * @param method request method
     */
    public void setRequestMethod(String method) {
        this.method = method;
    }

    /**
     * setter of request name
     *
     * @param name request name
     */
    public void setRequestName(String name) {
        this.name = name;
    }


    /**
     * this method implement color changing in MouseAdaptor
     * anonymous inner class. also this method used in other classes
     * to change color when a new requestPanel object is created.
     * if input integer number be 0, this method will call mouse entered
     * implementation. if input integer number be 1, this method will call
     * mouse exited implementation.
     *
     * @param input type of this action
     */
    public void restartColor(int input) {

        if(input == 0) { // mouse entered

            if(theme == MainFrame.LIGHT_THEME) {

                RequestPanel.this.setBackground(new Color(222, 222, 225));
                RequestPanel.this.buttonPanel.setBackground(new Color(222, 222, 225));
                RequestPanel.this.coverPanel.setBackground(new Color(222, 222, 225));

            } else if(theme == MainFrame.DARK_THEME) {

                RequestPanel.this.setBackground(new Color(54, 55, 52));
                RequestPanel.this.buttonPanel.setBackground(new Color(54, 55, 52));
                RequestPanel.this.coverPanel.setBackground(new Color(54, 55, 52));

            }

        } else if(input == 1) { // mouse exited

            if(theme == MainFrame.LIGHT_THEME) {

                RequestPanel.this.setBackground(new Color(234, 234, 235));
                RequestPanel.this.buttonPanel.setBackground(new Color(234, 234, 235));
                RequestPanel.this.coverPanel.setBackground(new Color(234, 234, 235));

            } else if(theme == MainFrame.DARK_THEME) {

                RequestPanel.this.setBackground(new Color(46, 47, 43));
                RequestPanel.this.buttonPanel.setBackground(new Color(46, 47, 43));
                RequestPanel.this.coverPanel.setBackground(new Color(46, 47, 43));

            }

        }


    }
}
