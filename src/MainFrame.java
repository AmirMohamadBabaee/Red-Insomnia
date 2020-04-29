import com.sun.deploy.panel.JavaPanel;
import com.sun.jmx.mbeanserver.JmxMBeanServer;

import javax.imageio.ImageIO;
import javax.naming.spi.DirectoryManager;
import javax.swing.*;
import javax.swing.text.DefaultTextUI;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.SQLOutput;

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
    private ImageIcon icon;
    private String openMenu = "\uD83D\uDF83";
    private String currentDir;

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

        currentDir = System.getProperty("user.dir");
        System.out.println(currentDir);
        icon = new ImageIcon(currentDir+"\\newIcon.png");
        setIconImage(icon.getImage());

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




        // left part of RedInsomnia (part 1)


        JPanel leftPanel = leftPanel();


        // Central part of RedInsomnia (part 2)






        // right part of RedInsomnia (part3)

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(450, 580));
        rightPanel.setBorder(BorderFactory.createLineBorder(new Color(71, 72, 69), 1));

        JPanel responseStatusPanel = new JPanel();
        responseStatusPanel.setBackground(Color.white);
        responseStatusPanel.setPreferredSize(new Dimension(550, 65));
        rightPanel.add(responseStatusPanel, BorderLayout.NORTH);

        BoxLayout boxLayout = new BoxLayout(responseStatusPanel, BoxLayout.X_AXIS);
        responseStatusPanel.setLayout(boxLayout);

        JButton requestStatus = new JButton("200 OK");
        requestStatus.setBackground(new Color(117, 186, 36));
        requestStatus.setForeground(Color.white);
        requestStatus.setFont(new Font("Santa Fe Let", Font.PLAIN, 15));
        requestStatus.setPreferredSize(new Dimension(50, 30));
        requestStatus.setContentAreaFilled(false);
        requestStatus.setOpaque(true);

        JButton delayTime = new JButton("6.13 s");
        delayTime.setBackground(new Color(224, 224, 224));
        delayTime.setForeground(new Color(116, 116, 116));
        delayTime.setPreferredSize(new Dimension(50, 30));
        delayTime.setFont(new Font("Santa Fe Let", Font.PLAIN, 15));
        delayTime.setContentAreaFilled(false);
        delayTime.setOpaque(true);

        JButton fileSize = new JButton("147");
        fileSize.setBackground(new Color(224, 224, 224));
        fileSize.setForeground(new Color(116, 116, 116));
        fileSize.setPreferredSize(new Dimension(50, 30));
        fileSize.setFont(new Font("Santa Fe Let", Font.PLAIN, 15));
        fileSize.setContentAreaFilled(false);
        fileSize.setOpaque(true);

        responseStatusPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        responseStatusPanel.add(requestStatus);
        responseStatusPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        responseStatusPanel.add(delayTime);
        responseStatusPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        responseStatusPanel.add(fileSize);



        JPanel responsePanel = new JPanel();
        responsePanel.setBackground(new Color(40,41,37));
        rightPanel.add(responsePanel, BorderLayout.CENTER);





        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        mainPanel.setLocation(0, 0);

        return mainPanel;

    }


    private JPanel leftPanel() {

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(250, 580));
        leftPanel.setBorder(BorderFactory.createLineBorder(new Color(71, 72, 69), 1));

        JButton insomniaPart = new JButton("Insomnia           " + openMenu);
        insomniaPart.setFont(new Font("Santa Fe LET", Font.PLAIN, 25));
        insomniaPart.setBackground(new Color(105, 94, 184));
        insomniaPart.setForeground(Color.white);
        insomniaPart.setPreferredSize(new Dimension(250, 65));
        insomniaPart.setContentAreaFilled(false);
        insomniaPart.setOpaque(true);
        leftPanel.add(insomniaPart, BorderLayout.NORTH);

        JPanel requestPanel = new JPanel(new BorderLayout());
        requestPanel.setBackground(new Color(46, 47, 43));
        leftPanel.add(requestPanel, BorderLayout.CENTER);

        JPanel topRequestPanel = new JPanel();
        topRequestPanel.setPreferredSize(new Dimension(250, 50));
        topRequestPanel.setBackground(new Color(46, 47, 43));
        topRequestPanel.setLayout(new BoxLayout(topRequestPanel, BoxLayout.X_AXIS));

        JTextField requestFilter = new JTextField("Filter");
        requestFilter.setBackground(new Color(46, 47, 43));
        requestFilter.setForeground(new Color(71, 72, 69));
        requestFilter.setFont(new Font("Santa Fe Let", Font.PLAIN, 14));
        requestFilter.setPreferredSize(new Dimension(150 , 25));
        requestFilter.setMaximumSize(new Dimension(200, 30));
        requestFilter.setAlignmentY(Component.CENTER_ALIGNMENT);
        requestFilter.setBorder(BorderFactory.createLineBorder(new Color(71, 72, 69), 1));


        JButton plusButton = new JButton();
        plusButton.setBackground(new Color(46, 47, 43));
        plusButton.setForeground(Color.white);
        plusButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        plusButton.setPreferredSize(new Dimension(25, 25));
        plusButton.setContentAreaFilled(false);
        plusButton.setOpaque(true);
        File imageCheck = new File(currentDir + "\\plus-button.png");
        if(imageCheck.exists()) {

            try{

                ImageIcon buttonImage = new ImageIcon(currentDir + "\\plus-button.png");
                plusButton.setIcon(new ImageIcon(buttonImage.getImage()));

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            System.out.println("plus button image not found!!!");
            plusButton.setText("+ " + openMenu);

        }

        JPopupMenu plusPopupMenu = new JPopupMenu();
        plusPopupMenu.setPreferredSize(new Dimension(250, 100));
        JMenuItem newRequest = new JMenuItem("New Request");
        newRequest.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
        JMenuItem newFolder = new JMenuItem("New Folder");
        newFolder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK));

        plusPopupMenu.add(newRequest);
        plusPopupMenu.add(newFolder);

        /*newRequest.addActionListener();
        newFolder.addActionListener();*/

        plusButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(e.getButton() == MouseEvent.BUTTON1) {

                    Component button = (Component)e.getSource();
                    plusPopupMenu.show(plusButton, button.getX() - 200, button.getY() + button.getHeight());

                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                plusButton.setBackground(new Color(54, 55, 52));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                plusButton.setBackground(new Color(46, 47, 43));
            }
        });

        topRequestPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        topRequestPanel.add(requestFilter);
        topRequestPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        topRequestPanel.add(plusButton);
        topRequestPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        requestPanel.add(topRequestPanel, BorderLayout.NORTH);


        // list of requests

        JPanel requestList = new JPanel();
        requestList.setBackground(new Color(46, 47, 43));
        requestList.setLayout(new BoxLayout(requestList, BoxLayout.Y_AXIS));


        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL("http://www.google.com/").openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DefaultMutableTreeNode d = new DefaultMutableTreeNode(httpURLConnection);
        DefaultMutableTreeNode new_folder = new DefaultMutableTreeNode("New Folder");
        JTree tree = new JTree(new_folder);
        new_folder.add(d);
        tree.setBackground(new Color(46, 47, 43));
        tree.setCellRenderer(new RequestCellRendered("My request"));
        requestPanel.add(tree);

        return leftPanel;

    }


    private JPanel centerPanel() {

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setPreferredSize(new Dimension(550, 580));
        centerPanel.setBorder(BorderFactory.createLineBorder(new Color(71, 72, 69), 1));

        JPanel urlPanel = new JPanel(new BorderLayout(5, 0));
        urlPanel.setBackground(Color.white);
        urlPanel.setPreferredSize(new Dimension(550, 65));
        centerPanel.add(urlPanel, BorderLayout.NORTH);

        JButton httpMethodButton = new JButton(" GET       " + openMenu);
        httpMethodButton.setBackground(Color.white);
        httpMethodButton.setForeground(new Color(119, 119, 119));
        httpMethodButton.setFont(new Font("Santa Fe Let", Font.PLAIN, 15));
        httpMethodButton.setBorder(null);
        httpMethodButton.setPreferredSize(new Dimension(110, 65));
        httpMethodButton.setContentAreaFilled(false);
        httpMethodButton.setOpaque(true);
        urlPanel.add(httpMethodButton, BorderLayout.WEST);


        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setPreferredSize(new Dimension(200, 200));
        JMenuItem get = new JMenuItem("GET");
        JMenuItem post = new JMenuItem("POST");
        JMenuItem put = new JMenuItem("PUT");
        JMenuItem patch = new JMenuItem("PATCH");
        JMenuItem delete = new JMenuItem("DELETE");

        popupMenu.add(get);
        popupMenu.add(post);
        popupMenu.add(put);
        popupMenu.add(patch);
        popupMenu.add(delete);

        get.addActionListener(e -> httpMethodButton.setText(" GET       " + openMenu));
        post.addActionListener(e -> httpMethodButton.setText(" POST     " + openMenu));
        put.addActionListener(e -> httpMethodButton.setText(" PUT       " + openMenu));
        patch.addActionListener(e -> httpMethodButton.setText(" PATCH    " + openMenu));
        delete.addActionListener(e -> httpMethodButton.setText(" DELETE   " + openMenu));

        httpMethodButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(e.getButton() == MouseEvent.BUTTON1) {

                    Component button = (Component)e.getSource();
                    popupMenu.show(httpMethodButton, button.getX(), button.getY()+button.getHeight());

                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                httpMethodButton.setBackground(new Color(242, 242, 242));

            }

            @Override
            public void mouseExited(MouseEvent e) {

                httpMethodButton.setBackground(Color.white);

            }
        });


        JTextField urlTextField = new JTextField("changelog.insomnia.rest/changelog.json");
        urlTextField.setBackground(Color.white);
        urlTextField.setForeground(new Color(119, 119, 119));
        urlTextField.setFont(new Font("Santa Fe Let", Font.PLAIN, 15));
        urlTextField.setBorder(null);
        urlPanel.add(urlTextField, BorderLayout.CENTER);


        JButton sendButton = new JButton("Send");
        sendButton.setBackground(Color.white);
        sendButton.setForeground(new Color(119, 119, 119));
        sendButton.setFont(new Font("Santa Fe Let", Font.PLAIN, 16));
        sendButton.setBorder(null);
        sendButton.setPreferredSize(new Dimension(80, 65));
        sendButton.setContentAreaFilled(false);
        sendButton.setOpaque(true);
        urlPanel.add(sendButton, BorderLayout.EAST);
        sendButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {

                sendButton.setBackground(new Color(242, 242, 242));

            }

            @Override
            public void mouseExited(MouseEvent e) {

                sendButton.setBackground(Color.white);

            }
        });



        JPanel requestSettingPanel = new JPanel();
        requestSettingPanel.setBackground(new Color(40,41,37));
        centerPanel.add(requestSettingPanel, BorderLayout.CENTER);

    }


}
