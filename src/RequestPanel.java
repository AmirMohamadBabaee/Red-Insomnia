import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * RequestPanel
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

    private String method;
    private String name;


    public RequestPanel(int theme, String method, String name, JPanel requestList, String currentDir) {

        this.theme = theme;
        this.setMethod(method);
        this.setName(name);


        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        coverPanel = new JPanel();

        coverPanel.setOpaque(true);
        coverPanel.setPreferredSize(new Dimension(100, 50));
        coverPanel.setLayout(new BoxLayout(coverPanel, BoxLayout.X_AXIS));


        requestMethod = new JLabel();
        requestMethod.setOpaque(false);
        requestMethod.setPreferredSize(new Dimension(100, 50));
        requestMethod.setText(this.getMethod());
        requestMethod.setForeground(new Color(0, 255, 0));
        requestMethod.setFont(new Font("Santa Fe Let" , Font.PLAIN, 13));

        requsetName = new JLabel();
        requsetName.setOpaque(false);
        requsetName.setPreferredSize(new Dimension(200, 50));
        requsetName.setText(name);
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
        this.setToolTipText(this.getMethod() + " - " + this.getName());
        this.add(buttonPanel);

        requestPopup = new JPopupMenu();
        requestPopup.setPreferredSize(new Dimension(150, 50));

        JMenuItem delete = new JMenuItem("Delete");

        delete.addActionListener(e -> {
            requestList.remove(this);
            requestList.revalidate();
            requestList.repaint();

            try {

                FileOutputStream out = new FileOutputStream(currentDir + "\\data\\request_list");
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

                if(e.getButton() == MouseEvent.BUTTON3) {

                    Component component = (Component)e.getSource();
                    requestPopup.show(RequestPanel.this, component.getX(), component.getY() + component.getHeight());

                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                if(theme == MainFrame.LIGHT_THEME) {

                    RequestPanel.this.setBackground(new Color(222, 222, 225));
                    RequestPanel.this.buttonPanel.setBackground(new Color(222, 222, 225));
                    RequestPanel.this.coverPanel.setBackground(new Color(222, 222, 225));

                } else if(theme == MainFrame.DARK_THEME) {

                    RequestPanel.this.setBackground(new Color(54, 55, 52));
                    RequestPanel.this.buttonPanel.setBackground(new Color(54, 55, 52));
                    RequestPanel.this.coverPanel.setBackground(new Color(54, 55, 52));

                }



            }

            @Override
            public void mouseExited(MouseEvent e) {

                if(!RequestPanel.this.isSelected()) {

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
        });

    }


    public void addAction(int theme) {

        if(theme == MainFrame.LIGHT_THEME) {

            buttonPanel.setBackground(new Color(234, 234, 235));

            coverPanel.setBackground(new Color(234, 234, 235));

            this.setBackground(new Color(235, 235, 235));

        } else if(theme == MainFrame.DARK_THEME) {

            buttonPanel.setBackground(new Color(46, 47, 43));

            coverPanel.setBackground(new Color(46, 47, 43));

            this.setBackground(new Color(46, 47, 43));

        }

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(e.getButton() == MouseEvent.BUTTON3) {

                    Component component = (Component)e.getSource();
                    requestPopup.show(RequestPanel.this, component.getX(), component.getY() + component.getHeight());

                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                if(theme == MainFrame.LIGHT_THEME) {

                    RequestPanel.this.setBackground(new Color(222, 222, 225));
                    RequestPanel.this.buttonPanel.setBackground(new Color(222, 222, 225));
                    RequestPanel.this.coverPanel.setBackground(new Color(222, 222, 225));

                } else if(theme == MainFrame.DARK_THEME) {

                    RequestPanel.this.setBackground(new Color(54, 55, 52));
                    RequestPanel.this.buttonPanel.setBackground(new Color(54, 55, 52));
                    RequestPanel.this.coverPanel.setBackground(new Color(54, 55, 52));

                }



            }

            @Override
            public void mouseExited(MouseEvent e) {

                if(!RequestPanel.this.isSelected()) {

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
        });

        this.revalidate();
        this.repaint();

    }


    public String getMethod() {
        return method;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
