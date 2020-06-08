package RedInsomnia.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


/**
 * RedInsomnia.gui.NewRequestFrame
 *
 * This frame is for when you want to create
 * a new http request.
 *
 * @author Amir01
 * @version
 *
 * @see JFrame
 * @see MainFrame
 */
public class NewRequestFrame extends JFrame {

    private JLabel nameLabel;
    private JTextField requestName;
    private JButton requestMethod;
    private JButton createButton;
    private JPopupMenu methodMenu;
    private String currentDir;

    private String name;
    private String method = "GET";

    /**
     * Constructor of RedInsomnia.gui.NewRequestFrame class in RedInsomnia
     *
     * @param mainFrame main frame of program
     * @param requestList list of requestPanel
     * @param currentDir path of project directory
     */
    public NewRequestFrame(MainFrame mainFrame, JPanel requestList, String currentDir) {

        super();

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        SwingUtilities.updateComponentTreeUI(this);

        this.currentDir = currentDir;

        setTitle("New Request");
        setSize(new Dimension(900, 300));
        setResizable(false);
        setLocationRelativeTo(mainFrame);
        Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
        setIconImage(icon);
        setLayout(null);
        setBackground(new Color(234, 234, 235));


        nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("Santa Fe Let", Font.PLAIN, 20));
        nameLabel.setForeground(Color.black);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameLabel.setBounds(5, 40, 100, 50);

        getContentPane().add(nameLabel);

        requestName = new JTextField("My Request");
        requestName.setBackground(new Color(234, 234, 235));
        requestName.setForeground(Color.black);
        requestName.setFont(new Font("Santa Fe Let", Font.PLAIN, 18));
        requestName.setPreferredSize(new Dimension(750, 50));
        requestName.setMaximumSize(new Dimension(750, 50));
        requestName.setBorder(BorderFactory.createDashedBorder(new Color(91, 92, 90)));
        requestName.setBounds(5, 90, 780, 50);

        requestMethod = new JButton("GET  ");
        requestMethod.setBackground(new Color(234, 234, 235));
        requestMethod.setForeground(new Color(0, 255, 0));
        requestMethod.setFont(new Font("Santa Fe Let", Font.PLAIN, 17));
        requestMethod.setPreferredSize(new Dimension(75, 50));
        requestMethod.setBorder(BorderFactory.createDashedBorder(new Color(91, 92, 90)));
        requestMethod.setContentAreaFilled(false);
        requestMethod.setOpaque(true);
        requestMethod.setBounds(790, 90, 100, 50);
        requestMethod.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {

                requestMethod.setBackground(new Color(153, 153, 153));

            }

            @Override
            public void mouseExited(MouseEvent e) {

                requestMethod.setBackground(new Color(234, 234, 235));

            }
        });

        methodMenu = new JPopupMenu();
        methodMenu.setPreferredSize(new Dimension(200, 300));

        JMenuItem get = new JMenuItem("GET");
        JMenuItem post = new JMenuItem("POST");
        JMenuItem put = new JMenuItem("PUT");
        JMenuItem patch = new JMenuItem("PATCH");
        JMenuItem delete = new JMenuItem("DELETE");

        get.addActionListener(e -> {
            method = "GET";
            requestMethod.setText(method);
        });
        post.addActionListener(e -> {
            method = "POST";
            requestMethod.setText(method);
        });
        put.addActionListener(e -> {
            method = "PUT";
            requestMethod.setText(method);
        });
        patch.addActionListener(e -> {
            method = "PATCH";
            requestMethod.setText(method);
        });
        delete.addActionListener(e -> {
            method = "DELETE";
            requestMethod.setText(method);
        });

        methodMenu.add(get);
        methodMenu.add(post);
        methodMenu.add(put);
        methodMenu.add(patch);
        methodMenu.add(delete);

        requestMethod.addActionListener(e -> methodMenu.show(requestMethod, -100, requestMethod.getHeight()));

        createButton = new JButton("Create");
        requestMethod.setBackground(new Color(234, 234, 235));
        createButton.setForeground(new Color(91, 92, 90));
        createButton.setFont(new Font("Santa Fe Let", Font.PLAIN, 18));
        createButton.setPreferredSize(new Dimension(100, 50));
        createButton.setContentAreaFilled(false);
        createButton.setOpaque(true);
        createButton.setBounds(790, 210, 100, 50);
        createButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {

                createButton.setBackground(new Color(153, 153, 153));

            }

            @Override
            public void mouseExited(MouseEvent e) {

                createButton.setBackground(new Color(234, 234, 235));

            }
        });
        createButton.addActionListener(e -> {
            dispose();
            name = requestName.getText();
            RequestPanel requestPanel = new RequestPanel(mainFrame, method, name, requestList);
            requestList.add(requestPanel);
            RequestPack pack = new RequestPack(requestPanel, mainFrame);
            requestPanel.setSelected(true);
            requestPanel.restartColor(0);

            for (Component component : requestList.getComponents()) {

                if(component instanceof RequestPanel && ((RequestPanel)component) != requestPanel) {

                    ((RequestPanel)component).setSelected(false);
                    ((RequestPanel) component).restartColor(1);

                }

            }

            requestPanel.setSelected(true);

            mainFrame.getBean().setValue(method);

            mainFrame.getContentPane().removeAll();
            mainFrame.getContentPane().add(mainFrame.getLeftPanel(), BorderLayout.WEST);
            mainFrame.getContentPane().add(pack.getCenterPanel(), BorderLayout.CENTER);
            mainFrame.getContentPane().add(pack.getRightPanel(), BorderLayout.EAST);
            mainFrame.getContentPane().revalidate();
            mainFrame.getContentPane().repaint();

            requestPanel.addActionListener(e1 -> {

                requestPanel.setSelected(true);
                mainFrame.getContentPane().removeAll();
                mainFrame.getContentPane().add(mainFrame.getLeftPanel(), BorderLayout.WEST);
                mainFrame.getContentPane().add(pack.getCenterPanel(), BorderLayout.CENTER);
                mainFrame.getContentPane().add(pack.getRightPanel(), BorderLayout.EAST);
                mainFrame.getContentPane().revalidate();
                mainFrame.getContentPane().repaint();

                for (Component component : requestList.getComponents()) {

                    if(component instanceof RequestPanel && ((RequestPanel)component) != requestPanel) {

                        ((RequestPanel)component).setSelected(false);
                        ((RequestPanel) component).restartColor(1);
                        component.setVisible(true);

                    }

                }

            });

            requestList.revalidate();
            requestList.repaint();

            try(FileOutputStream out = new FileOutputStream(currentDir + "\\data\\request_list")) {

                ObjectOutputStream outputStream = new ObjectOutputStream(out);
                for (Component component : requestList.getComponents()) {

                    if(component instanceof JButton) {

                        outputStream.writeObject(component);

                    }

                }

            } catch (IOException err) {
                err.printStackTrace();
            }

        });

        add(requestName);
        add(requestMethod);
        add(createButton);


        setVisible(true);

    }

    /**
     * Constructor of NewRequestFrame
     *
     * @param mainFrame main frame of red insomnia
     * @param requestList request list of saved request
     * @param centerPanel center panel of initial page
     * @param rightPanel right panel of initial page
     */
    public NewRequestFrame(MainFrame mainFrame, JPanel requestList, CenterPanel centerPanel, RightPanel rightPanel) {

        super();

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        SwingUtilities.updateComponentTreeUI(this);

        this.currentDir = mainFrame.getCurrentDir();

        setTitle("New Request");
        setSize(new Dimension(900, 300));
        setResizable(false);
        setLocationRelativeTo(mainFrame);
        Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
        setIconImage(icon);
        setLayout(null);
        setBackground(new Color(234, 234, 235));


        nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("Santa Fe Let", Font.PLAIN, 20));
        nameLabel.setForeground(Color.black);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameLabel.setBounds(5, 40, 100, 50);

        getContentPane().add(nameLabel);

        requestName = new JTextField("My Request");
        requestName.setBackground(new Color(234, 234, 235));
        requestName.setForeground(Color.black);
        requestName.setFont(new Font("Santa Fe Let", Font.PLAIN, 18));
        requestName.setPreferredSize(new Dimension(750, 50));
        requestName.setMaximumSize(new Dimension(750, 50));
        requestName.setBorder(BorderFactory.createDashedBorder(new Color(91, 92, 90)));
        requestName.setBounds(5, 90, 780, 50);

        requestMethod = new JButton("GET  ");
        requestMethod.setBackground(new Color(234, 234, 235));
        requestMethod.setForeground(new Color(0, 255, 0));
        requestMethod.setFont(new Font("Santa Fe Let", Font.PLAIN, 17));
        requestMethod.setPreferredSize(new Dimension(75, 50));
        requestMethod.setBorder(BorderFactory.createDashedBorder(new Color(91, 92, 90)));
        requestMethod.setContentAreaFilled(false);
        requestMethod.setOpaque(true);
        requestMethod.setBounds(790, 90, 100, 50);
        requestMethod.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {

                requestMethod.setBackground(new Color(153, 153, 153));

            }

            @Override
            public void mouseExited(MouseEvent e) {

                requestMethod.setBackground(new Color(234, 234, 235));

            }
        });

        methodMenu = new JPopupMenu();
        methodMenu.setPreferredSize(new Dimension(200, 300));

        JMenuItem get = new JMenuItem("GET");
        JMenuItem post = new JMenuItem("POST");
        JMenuItem put = new JMenuItem("PUT");
        JMenuItem patch = new JMenuItem("PATCH");
        JMenuItem delete = new JMenuItem("DELETE");

        get.addActionListener(e -> {
            method = "GET";
            requestMethod.setText(method);
        });
        post.addActionListener(e -> {
            method = "POST";
            requestMethod.setText(method);
        });
        put.addActionListener(e -> {
            method = "PUT";
            requestMethod.setText(method);
        });
        patch.addActionListener(e -> {
            method = "PATCH";
            requestMethod.setText(method);
        });
        delete.addActionListener(e -> {
            method = "DELETE";
            requestMethod.setText(method);
        });

        methodMenu.add(get);
        methodMenu.add(post);
        methodMenu.add(put);
        methodMenu.add(patch);
        methodMenu.add(delete);

        requestMethod.addActionListener(e -> methodMenu.show(requestMethod, -100, requestMethod.getHeight()));

        createButton = new JButton("Create");
        requestMethod.setBackground(new Color(234, 234, 235));
        createButton.setForeground(new Color(91, 92, 90));
        createButton.setFont(new Font("Santa Fe Let", Font.PLAIN, 18));
        createButton.setPreferredSize(new Dimension(100, 50));
        createButton.setContentAreaFilled(false);
        createButton.setOpaque(true);
        createButton.setBounds(790, 210, 100, 50);
        createButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {

                createButton.setBackground(new Color(153, 153, 153));

            }

            @Override
            public void mouseExited(MouseEvent e) {

                createButton.setBackground(new Color(234, 234, 235));

            }
        });
        createButton.addActionListener(e -> {
            dispose();
            name = requestName.getText();
            RequestPanel requestPanel = new RequestPanel(mainFrame, method, name, requestList);
            requestList.add(requestPanel);
            RequestPack pack = new RequestPack(requestPanel, centerPanel, rightPanel);
            requestPanel.setSelected(true);
            requestPanel.restartColor(0);

            for (Component component : requestList.getComponents()) {

                if(component instanceof RequestPanel && ((RequestPanel)component) != requestPanel) {

                    ((RequestPanel)component).setSelected(false);
                    ((RequestPanel) component).restartColor(1);

                }

            }

            requestPanel.setSelected(true);

            mainFrame.getBean().setValue(method);

            mainFrame.getContentPane().removeAll();
            mainFrame.getContentPane().add(mainFrame.getLeftPanel(), BorderLayout.WEST);
            mainFrame.getContentPane().add(pack.getCenterPanel(), BorderLayout.CENTER);
            mainFrame.getContentPane().add(pack.getRightPanel(), BorderLayout.EAST);
            mainFrame.getContentPane().revalidate();
            mainFrame.getContentPane().repaint();


            requestPanel.addActionListener(e1 -> {

                requestPanel.setSelected(true);
                mainFrame.getContentPane().removeAll();
                mainFrame.getContentPane().add(mainFrame.getLeftPanel(), BorderLayout.WEST);
                mainFrame.getContentPane().add(pack.getCenterPanel(), BorderLayout.CENTER);
                mainFrame.getContentPane().add(pack.getRightPanel(), BorderLayout.EAST);
                mainFrame.getContentPane().revalidate();
                mainFrame.getContentPane().repaint();

                for (Component component : requestList.getComponents()) {

                    if(component instanceof RequestPanel && ((RequestPanel)component) != requestPanel) {

                        ((RequestPanel)component).setSelected(false);
                        ((RequestPanel) component).restartColor(1);
                        component.setVisible(true);

                    }

                }

            });

            requestList.revalidate();
            requestList.repaint();

            try(FileOutputStream out = new FileOutputStream(currentDir + "\\data\\request_list")) {

                ObjectOutputStream outputStream = new ObjectOutputStream(out);
                for (Component component : requestList.getComponents()) {

                    if(component instanceof JButton) {

                        outputStream.writeObject(component);

                    }

                }

            } catch (IOException err) {
                err.printStackTrace();
            }

        });

        add(requestName);
        add(requestMethod);
        add(createButton);


        setVisible(true);

    }

}
