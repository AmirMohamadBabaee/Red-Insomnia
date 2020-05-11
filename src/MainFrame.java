import javax.imageio.ImageIO;
import javax.management.JMException;
import javax.naming.spi.DirectoryManager;
import javax.smartcardio.Card;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.DomainCombiner;
import java.security.Key;
import java.sql.SQLOutput;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

/**
 * MainFrame
 *
 * This class is main Frame of insomnia app
 *
 * @author Amir01
 * @version
 *
 * @see JFrame
 */
public class MainFrame extends JFrame {

    // Field

    public static final int LIGHT_THEME = 0;
    public static final int DARK_THEME = 1;
    private ImageIcon icon;
    private String openMenu = "\uD83D\uDF83";
    private String currentDir;
    private List<List<Color>> themes;
    private List<Color> lightColor;
    private List<Color> darkColor;
    private int theme;
    private boolean followDirect;
    private boolean closeOperation;
    public Component[] mainComponents;

    // Constructor

    public MainFrame(String title, int theme) {

        super();


        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        SwingUtilities.updateComponentTreeUI(this);

        currentDir = System.getProperty("user.dir");
        System.out.println(currentDir);
        icon = new ImageIcon(currentDir+"\\newIcon.png");
        setIconImage(icon.getImage());


        this.theme = theme;

        themes = new ArrayList<>();

        lightColor = new ArrayList<>();
        darkColor = new ArrayList<>();

        themes.add(lightColor);
        themes.add(darkColor);

        // light colors

        lightColor.add(new Color(255, 10, 50));
        lightColor.add(new Color(209, 212, 215));
        lightColor.add(new Color(234, 234, 235));
        lightColor.add(new Color(237, 237, 239));
        lightColor.add(new Color(119, 119, 119));
        lightColor.add(new Color(242, 242, 242));
        lightColor.add(new Color(250, 250, 250));
        lightColor.add(new Color(153, 153, 153));
        lightColor.add(new Color(222, 222, 225));
        lightColor.add(new Color(237, 237, 239));
        lightColor.add(new Color(225, 225, 225));
        lightColor.add(Color.black);


        // dark Colors

        darkColor.add(new Color(255, 10, 50));
        darkColor.add(new Color(71, 72, 69));
        darkColor.add(new Color(46, 47, 43));
        darkColor.add(new Color(54, 55, 52));
        darkColor.add(new Color(119, 119, 119));
        darkColor.add(new Color(242, 242, 242));
        darkColor.add(new Color(40,41,37));
        darkColor.add(new Color(153, 153, 153));
        darkColor.add(new Color(91, 92, 90));
        darkColor.add(new Color(49, 50, 46));
        darkColor.add(new Color(60, 60, 60));
        darkColor.add(Color.white);



        initFrame();


        setTitle(title);
        setSize(1500, 650);
        setLocationRelativeTo(null);
        setLayout(null);
        setContentPane(mainPanel());
        if(closeOperation) {
            setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        } else {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }


        JMenuBar menuBar = new JMenuBar();

        JMenu application = new JMenu("Application");
        JMenu view = new JMenu("View");
        JMenu help = new JMenu("Help");

        application.setMnemonic(KeyEvent.VK_A);
        view.setMnemonic(KeyEvent.VK_V);
        help.setMnemonic(KeyEvent.VK_H);

        menuBar.add(application);
        menuBar.add(view);
        menuBar.add(help);

        // Application menu

        JMenuItem options = new JMenuItem("Options", KeyEvent.VK_O);
        JMenuItem exit = new JMenuItem("Exit", KeyEvent.VK_Q);

        JFrame mainFrame = this;
        options.addActionListener(e -> new OptionController(theme, mainFrame, currentDir));
        exit.addActionListener(e -> {
            int dialogResult = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to exit RedInsomnia?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
            if(dialogResult == JOptionPane.YES_OPTION) {
                mainFrame.dispose();
                System.exit(0);
            }
        });

        options.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));

        application.add(options);
        application.add(exit);

        // view menu

        JMenuItem toggleFullScreen = new JMenuItem("Toggle Full Screen");
        JMenuItem toggleSideBar = new JMenuItem("Toggle Sidebar");

        toggleFullScreen.addActionListener(e -> {
            try {
                if(mainFrame.getExtendedState() == JFrame.NORMAL) {
                    mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                } else if(mainFrame.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                    mainFrame.setExtendedState(JFrame.NORMAL);
                }
            } catch(Exception err) {
                err.printStackTrace();
            }
        });
        toggleSideBar.addActionListener(e -> {
            for (Component component : mainFrame.getContentPane().getComponents()) {
                try {

                    if(component.getName().equals("left panel")) {
                        if(component.isVisible()) {
                            component.setVisible(false);
                        } else {
                            component.setVisible(true);
                        }
                    }

                } catch(NullPointerException err) {
                    err.printStackTrace();
                }
            }
        });

        toggleFullScreen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_MASK));
        toggleSideBar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));

        toggleFullScreen.setMnemonic(KeyEvent.VK_F);
        toggleSideBar.setMnemonic(KeyEvent.VK_S);

        view.add(toggleFullScreen);
        view.add(toggleSideBar);

        // help menu

        JMenuItem about = new JMenuItem("About");
        JMenuItem helpItem = new JMenuItem("Help");

        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_MASK));
        helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_MASK));

        about.setMnemonic(KeyEvent.VK_B);
        helpItem.setMnemonic(KeyEvent.VK_H);

        help.add(about);
        help.add(helpItem);



        setJMenuBar(menuBar);

        setVisible(true);
    }

    private void initFrame() {

        try(FileInputStream in = new FileInputStream(currentDir + "\\data\\options")) {

            BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
            DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);

            int followDirectInt = dataInputStream.readByte();
            int closeOperationInt = dataInputStream.readByte();
            int lightThemeInt = dataInputStream.readByte();
            int darkThemeInt = dataInputStream.readByte();

            if(followDirectInt == 0) {
                followDirect = false;
            } else if(followDirectInt == 1) {
                followDirect = true;
            }

            if(closeOperationInt == 0) {
                closeOperation = false;
            } else if(closeOperationInt == 1) {
                closeOperation = true;
            }

            if(lightThemeInt == 1) {
                this.theme = LIGHT_THEME;
            }

            if(darkThemeInt == 1) {
                this.theme = DARK_THEME;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public boolean isFollowDirect() {
        return followDirect;
    }

    public void setFollowDirect(boolean followDirect) {
        this.followDirect = followDirect;
    }

    public boolean isCloseOperation() {
        return closeOperation;
    }

    public void setCloseOperation(boolean closeOperation) {
        this.closeOperation = closeOperation;
    }

    /**
     * getter of theme of this frame
     *
     * @return theme of this frame
     */
    public int getTheme() {
        return theme;
    }

    /**
     * setter of theme of this frame
     *
     * @param theme expected theme
     */
    public void setTheme(int theme) {
        this.theme = theme;
    }

    public List<List<Color>> getThemes() {
        return themes;
    }

    public String getCurrentDir() {
        return currentDir;
    }

    private JPanel mainPanel() {

        JPanel mainPanel = new JPanel();




        // left part of RedInsomnia (part 1)


        JPanel leftPanel = leftPanel();



        // Central part of RedInsomnia (part 2)


        JPanel centerPanel = new CenterPanel(this).getCenterPanel();



        // right part of RedInsomnia (part3)


        JPanel rightPanel = new RightPanel(this).getRightPanel();



        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        mainComponents = new Component[3];
        mainComponents[0] = leftPanel;
        mainComponents[1] = centerPanel;
        mainComponents[2] = rightPanel;

        mainPanel.setLocation(0, 0);

        return mainPanel;

    }


    private JPanel leftPanel() {

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setName("left panel");
        leftPanel.setPreferredSize(new Dimension(250, 580));
        leftPanel.setBorder(BorderFactory.createLineBorder(themes.get(theme).get(1), 1));

        JButton insomniaPart = new JButton("Insomnia           " + openMenu);
        insomniaPart.setFont(new Font("Santa Fe LET", Font.PLAIN, 25));
        insomniaPart.setBackground(themes.get(theme).get(0));
        insomniaPart.setForeground(themes.get(theme).get(11));
        insomniaPart.setPreferredSize(new Dimension(250, 65));
        insomniaPart.setContentAreaFilled(false);
        insomniaPart.setOpaque(true);
        leftPanel.add(insomniaPart, BorderLayout.NORTH);

        JPanel requestPanel = new JPanel(new BorderLayout());
        requestPanel.setBackground(themes.get(theme).get(2));
        leftPanel.add(requestPanel, BorderLayout.CENTER);

        JPanel topRequestPanel = new JPanel();
        topRequestPanel.setPreferredSize(new Dimension(250, 50));
        topRequestPanel.setBackground(themes.get(theme).get(2));
        topRequestPanel.setLayout(new BoxLayout(topRequestPanel, BoxLayout.X_AXIS));

        JTextField requestFilter = new JTextField("Filter");
        requestFilter.setBackground(themes.get(theme).get(2));
        requestFilter.setForeground(themes.get(theme).get(1));
        requestFilter.setFont(new Font("Santa Fe Let", Font.PLAIN, 14));
        requestFilter.setPreferredSize(new Dimension(150 , 25));
        requestFilter.setMaximumSize(new Dimension(200, 30));
        requestFilter.setAlignmentY(Component.CENTER_ALIGNMENT);
        requestFilter.setBorder(BorderFactory.createLineBorder(themes.get(theme).get(1), 1));


        JButton plusButton = new JButton();
        plusButton.setBackground(themes.get(theme).get(2));
        plusButton.setForeground(themes.get(theme).get(11));
        plusButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        plusButton.setPreferredSize(new Dimension(25, 25));
        plusButton.setContentAreaFilled(false);
        plusButton.setOpaque(true);
        File imageCheck = new File(currentDir + "\\plus_icon_normal.png");
        if(imageCheck.exists()) {

            try{

                ImageIcon buttonImage = new ImageIcon(currentDir + "\\plus_icon_normal.png");
                plusButton.setIcon(buttonImage);

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
                plusButton.setBackground(themes.get(theme).get(3));
                File imageCheck = new File(currentDir + "\\plus_icon_white.png");
                if(imageCheck.exists()) {

                    try{

                        ImageIcon buttonImage = new ImageIcon(currentDir + "\\plus_icon_white.png");
                        plusButton.setIcon(buttonImage);

                    } catch (Exception err) {
                        err.printStackTrace();
                    }

                } else {

                    System.out.println("plus icon white image not found!!!");
                    plusButton.setText("+ " + openMenu);

                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                plusButton.setBackground(themes.get(theme).get(2));
                File imageCheck = new File(currentDir + "\\plus_icon_normal.png");
                if(imageCheck.exists()) {

                    try{

                        ImageIcon buttonImage = new ImageIcon(currentDir + "\\plus_icon_normal.png");
                        plusButton.setIcon(buttonImage);

                    } catch (Exception err) {
                        err.printStackTrace();
                    }

                } else {

                    System.out.println("plus icon normal image not found!!!");
                    plusButton.setText("+ " + openMenu);

                }
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
        requestList.setBackground(themes.get(theme).get(2));
        requestList.setLayout(new BoxLayout(requestList, BoxLayout.Y_AXIS));


        for (RequestPanel object : readObjects()) {

            requestList.add(new RequestPanel(theme, object.getMethod(), object.getName(), requestList, currentDir));

        }

        newRequest.addActionListener(e -> new NewRequestFrame(MainFrame.this, requestList, currentDir));


        requestPanel.add(requestList, BorderLayout.CENTER);

        return leftPanel;

    }


    /**
     * this method copied from stackoverflow
     *
     * @return list of readed objects
     */
    public ArrayList<RequestPanel> readObjects(){
        ArrayList<RequestPanel> al = new ArrayList<>();
        boolean cont = true;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(currentDir +"\\data\\request_list"));
            while(cont){
                RequestPanel obj=null;
                try {
                    obj = (RequestPanel) ois.readObject();

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if(obj != null)
                    al.add(obj);
                else
                    cont = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return al;
    }


}
