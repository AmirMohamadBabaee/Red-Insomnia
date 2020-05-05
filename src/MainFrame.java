
import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;
import sun.plugin.services.PlatformService;
import sun.swing.FilePane;
import sun.text.normalizer.Trie;

import javax.imageio.ImageIO;
import javax.naming.spi.DirectoryManager;
import javax.smartcardio.Card;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInput;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.DomainCombiner;
import java.sql.SQLOutput;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

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
    private List<Color> lightColor;
    private List<Color> darkColor;

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
        setContentPane(mainPanel(theme));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        lightColor = new ArrayList<>();
        darkColor = new ArrayList<>();

    }


    private JPanel mainPanel(int theme) {

        JPanel mainPanel = new JPanel();




        // left part of RedInsomnia (part 1)


        JPanel leftPanel = leftPanel(theme);


        // Central part of RedInsomnia (part 2)


        JPanel centerPanel = centerPanel(theme);



        // right part of RedInsomnia (part3)


        JPanel rightPanel = rightPanel(theme);



        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        mainPanel.setLocation(0, 0);

        return mainPanel;

    }


    private JPanel leftPanel(int theme) {

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(250, 580));
        leftPanel.setBorder(BorderFactory.createLineBorder(new Color(71, 72, 69), 1));

        JButton insomniaPart = new JButton("Insomnia           " + openMenu);
        insomniaPart.setFont(new Font("Santa Fe LET", Font.PLAIN, 25));
        insomniaPart.setBackground(new Color(255, 10, 50));
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
                plusButton.setBackground(new Color(54, 55, 52));
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
                plusButton.setBackground(new Color(46, 47, 43));
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


    private JPanel centerPanel(int theme) {

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



        JPanel requestSettingPanel = new JPanel(new BorderLayout());
        requestSettingPanel.setBackground(new Color(40,41,37));
        centerPanel.add(requestSettingPanel, BorderLayout.CENTER);


        JPanel mainSettingPanel = new JPanel(new CardLayout());
        mainSettingPanel.setBackground(new Color(40, 41, 37));
        mainSettingPanel.setBorder(BorderFactory.createLineBorder(new Color(71, 72, 69), 1));

        JPanel formData = new JPanel(new CardLayout());
        formData.setBackground(new Color(40, 41, 37));

        JPanel formPanel = new JPanel();
        JPanel jsonPanel = new JPanel();
        JPanel binaryFilePanel = new JPanel();

        formData.add(formPanel, "form panel");
        formData.add(jsonPanel, "json");
        formData.add(binaryFilePanel, "binary file");

        CardLayout formDataCardLayout = (CardLayout) formData.getLayout();
        formDataCardLayout.show(formData, "form panel");


        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(40, 41, 37));

        mainSettingPanel.add(formData , "form data");
        mainSettingPanel.add(header, "header");

        CardLayout cardLayout = (CardLayout) mainSettingPanel.getLayout();

        cardLayout.show(mainSettingPanel, "formData");

        requestSettingPanel.add(mainSettingPanel, BorderLayout.CENTER);

        /*JPanel header = new JPanel();
        header.setBackground(new Color(40, 41, 37));

        JPanel formData = new JPanel();
        formData.setBackground(new Color(40, 41, 37));

        JTabbedPane headerTab = new JTabbedPane();
        headerTab.setBackground(new Color(40, 41, 37));
        headerTab.addTab("<html><body style =\"background-color:red;\"><h2>  Form Data  </h2></body></html>"
                , formData);
        headerTab.addTab("<html><body style =\"background-color:#282925;\"><h2>  Header  </h2></body></html>"
                , header);

        requestSettingPanel.add(headerTab, BorderLayout.CENTER);*/

        // this part is related to popup menu of form data button

        JPopupMenu dataForm = new JPopupMenu();
        dataForm.setPreferredSize(new Dimension(200, 150));

        JMenuItem formDataItem = new JMenuItem("Form Data");
        JMenuItem JSONItem = new JMenuItem("JSON");
        JMenuItem binaryFile = new JMenuItem("Binary File");

        // Todo : add json panel for json menu item

        dataForm.add(formDataItem);
        dataForm.add(JSONItem);
        dataForm.add(binaryFile);

        // end of popup menu

        JPanel headerTab = new JPanel(new GridLayout(1, 2));
        headerTab.setBackground(new Color(40, 41, 37));
        headerTab.setBorder(BorderFactory.createLineBorder(new Color(71, 72,69)));

        List<JButton> headers = new ArrayList<>();

        for(int i=0 ; i<2; i++) {

            headers.add(new JButton());
            if(i == 0) {

                headers.get(i).setBackground(new Color(71, 72, 69));
                headers.get(i).setForeground(Color.white);

            } else {

                headers.get(i).setBackground(new Color(40, 41, 37));
                headers.get(i).setForeground(new Color(153, 153, 153));

            }
            headers.get(i).setFont(new Font("Santa Fe Let", Font.PLAIN, 20));
            headers.get(i).setPreferredSize(new Dimension(200, 50));
            headers.get(i).setContentAreaFilled(false);
            headers.get(i).setOpaque(true);
            headers.get(i).setBorder(null);
            final int index = i;
            headers.get(i).addMouseListener(new MouseAdapter() {


                @Override
                public void mouseClicked(MouseEvent e) {

                    if(e.getButton() == MouseEvent.BUTTON1) {

                        if(index == 0) { // form data part

                            cardLayout.show(mainSettingPanel, "form data");
                            changeHeaderButtonsColor(headers, index, 2);

                        } else if(index == 1) { // header part

                            cardLayout.show(mainSettingPanel, "header");
                            changeHeaderButtonsColor(headers, index, 2);

                        }

                    } else if(e.getButton() == MouseEvent.BUTTON3) {

                        if(index == 0) {

                            Component component = (Component) e.getSource();
                            dataForm.show(headers.get(0), component.getX(), component.getY() + component.getHeight());

                        }

                    }

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                    changeHeaderButtonsColor(headers, index, 0);

                }

                @Override
                public void mouseExited(MouseEvent e) {

                    if(index == 0) { // form data part

                        for(Component com : mainSettingPanel.getComponents()) {

                            if(com.isVisible() && com != formData) {

                                changeHeaderButtonsColor(headers, index, 1);

                            }

                        }

                    } else if(index == 1) { // header part

                        for(Component com : mainSettingPanel.getComponents()) {

                            if(com.isVisible() && com != header) {

                                changeHeaderButtonsColor(headers, index, 1);

                            }

                        }

                    }
                }
            });

        }

        headers.get(0).setText("Form Data   " + openMenu);
        headers.get(1).setText("Header");

        formDataItem.addActionListener(e -> {
            formDataCardLayout.show(formData, "form panel");
            headers.get(0).setText("Form Data   "+ openMenu);
        });
        JSONItem.addActionListener(e -> {
            formDataCardLayout.show(formData, "json");
            headers.get(0).setText("JSON     " + openMenu);
        });
        binaryFile.addActionListener(e -> {
            formDataCardLayout.show(formData, "binary file");
            headers.get(0).setText("Binary File  " + openMenu);
        });

        for (JButton button : headers) {
            headerTab.add(button);
        }

        requestSettingPanel.add(headerTab, BorderLayout.NORTH);


        // this part related to header part of center panel

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(40, 41, 37));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        List<JPanel> headerField = new ArrayList<>();

        headerField.add(headerFieldCreator(theme,headerField, headerPanel, false));
        addHeaderFieldPanel(headerPanel, headerField);

        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));


        JScrollPane scrolledHeaderPanel = new JScrollPane(headerPanel);
        scrolledHeaderPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrolledHeaderPanel.setBackground(new Color(40, 41, 37));

        header.add(scrolledHeaderPanel, BorderLayout.CENTER);

        // end of changes to header part of central panel

        // this part related to form data part of central panel

        formPanel.setBackground(new Color(40, 41, 37));
        formPanel.setLayout(new BorderLayout());


        JPanel coverFormPanel = new JPanel();
        coverFormPanel.setBackground(new Color(40, 41, 37));
        coverFormPanel.setLayout(new BoxLayout(coverFormPanel, BoxLayout.Y_AXIS));


        JScrollPane coverFormPanelScroll = new JScrollPane(coverFormPanel);
        coverFormPanelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        coverFormPanelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        formPanel.add(coverFormPanelScroll, BorderLayout.CENTER);


        List<JPanel> formDataField = new ArrayList<>();
        formDataField.add(headerFieldCreator(theme, formDataField, coverFormPanel, true));
        addHeaderFieldPanel(coverFormPanel, formDataField);

        // end of changes to form data part of central panel

        // this part related to binary file panel in central panel

        binaryFilePanel.setBackground(new Color(40, 41, 37));
        binaryFilePanel.setLayout(new BoxLayout(binaryFilePanel, BoxLayout.Y_AXIS));

        JLabel selectedFile = new JLabel("SELECTED FILE");
        selectedFile.setFont(new Font("Santa Fe Let", Font.PLAIN, 18));
        selectedFile.setForeground(new Color(91, 92, 90));
        selectedFile.setAlignmentX(Component.LEFT_ALIGNMENT);


        JTextArea filePathArea = new JTextArea("No file selected");
        filePathArea.setBackground(new Color(40, 41, 37));
        filePathArea.setForeground(new Color(91, 92, 90));
        filePathArea.setFont(new Font("Santa Fe Let", Font.PLAIN, 18));
        filePathArea.setPreferredSize(new Dimension(300, 50));
        filePathArea.setEditable(false);
        filePathArea.setLineWrap(true);
        filePathArea.setBorder(BorderFactory.createDashedBorder(new Color(100, 100, 100)));

        JScrollPane filePathScrollArea = new JScrollPane(filePathArea);
        filePathScrollArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        filePathScrollArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        filePathScrollArea.setMaximumSize(new Dimension(2000, 100));


        JButton resetFile = new JButton("Reset File");

        resetFile.setBackground(new Color(40, 41, 37));
        resetFile.setForeground(new Color(71, 72, 69));
        resetFile.setBorder(null);
        resetFile.setEnabled(false);
        resetFile.setFont(new Font("Santa Fe Let", Font.PLAIN, 15));
        resetFile.setPreferredSize(new Dimension(150 , 50));
        resetFile.setContentAreaFilled(false);
        resetFile.setOpaque(true);
        resetFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                filePathArea.setText("No file selected");
                filePathArea.setForeground(new Color(71, 72, 69));
                resetFile.setBackground(new Color(40, 41, 37));
                resetFile.setForeground(new Color(91, 92, 90));
                resetFile.setEnabled(false);

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                if(resetFile.isEnabled()) {
                    resetFile.setBackground(new Color(71, 72, 69));
                }

            }

            @Override
            public void mouseExited(MouseEvent e) {

                resetFile.setBackground(new Color(40, 41, 37));

            }
        });



        JButton chooseButton = new JButton("Choose File");
        chooseButton.setBackground(new Color(40, 41, 37));
        chooseButton.setForeground(Color.white);
        chooseButton.setFont(new Font("Santa Fe Let", Font.PLAIN, 15));
        chooseButton.setPreferredSize(new Dimension(150, 50));
        chooseButton.setBorder(BorderFactory.createLineBorder(new Color(91, 92, 90), 1));
        chooseButton.setContentAreaFilled(false);
        chooseButton.setOpaque(true);
        chooseButton.setBounds(100, 75, 150, 50);

        final JFrame mainFrame = this;
        chooseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                JFileChooser fileChooser = new JFileChooser();
                int i = fileChooser.showOpenDialog(mainFrame);
                if(i == JFileChooser.APPROVE_OPTION) {

                    File file = fileChooser.getSelectedFile();
                    filePathArea.setText(file.getAbsolutePath());
                    filePathArea.append(" (" + convertBytetoMegaByte(file.length()) + ")");
                    filePathArea.setForeground(Color.white);
                    resetFile.setForeground(Color.white);
                    resetFile.setEnabled(true);

                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                chooseButton.setBackground(new Color(71, 72, 69));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                chooseButton.setBackground(new Color(40, 41, 37));
            }
        });


        JPanel buttonBinaryPanel = new JPanel(new FlowLayout());
        buttonBinaryPanel.setBackground(new Color(40, 41, 37));
        buttonBinaryPanel.add(resetFile);
        buttonBinaryPanel.add(chooseButton);


        binaryFilePanel.add(Box.createRigidArea(new Dimension(0, 15)));
        binaryFilePanel.add(selectedFile);
        binaryFilePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        binaryFilePanel.add(filePathScrollArea);
        binaryFilePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        binaryFilePanel.add(buttonBinaryPanel);



        // end of changes to binary file panel in central panel

        return centerPanel;

    }



    private JPanel rightPanel(int theme) {

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(450, 580));
        rightPanel.setBorder(BorderFactory.createLineBorder(new Color(71, 72, 69), 1));

        JPanel responseStatusPanel = new JPanel();
        responseStatusPanel.setBackground(Color.white);
        responseStatusPanel.setPreferredSize(new Dimension(550, 65));
        rightPanel.add(responseStatusPanel, BorderLayout.NORTH);


        // north of right panel in RedInsomnia

        BoxLayout boxLayout = new BoxLayout(responseStatusPanel, BoxLayout.X_AXIS);
        responseStatusPanel.setLayout(boxLayout);

        // response status button
        JButton requestStatus = new JButton("200 OK");
        requestStatus.setBackground(new Color(117, 186, 36));
        requestStatus.setForeground(Color.white);
        requestStatus.setFont(new Font("Santa Fe Let", Font.PLAIN, 15));
        requestStatus.setPreferredSize(new Dimension(50, 30));
        requestStatus.setContentAreaFilled(false);
        requestStatus.setOpaque(true);

        // delay time of request
        JButton delayTime = new JButton("6.13 s");
        delayTime.setBackground(new Color(224, 224, 224));
        delayTime.setForeground(new Color(116, 116, 116));
        delayTime.setPreferredSize(new Dimension(50, 30));
        delayTime.setFont(new Font("Santa Fe Let", Font.PLAIN, 15));
        delayTime.setContentAreaFilled(false);
        delayTime.setOpaque(true);

        // size of response file
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

        // end of response status part

        // this part related to center of response part

        JPanel responsePanel = new JPanel(new BorderLayout());
        responsePanel.setBackground(new Color(40,41,37));
        rightPanel.add(responsePanel, BorderLayout.CENTER);


        // header panel for header button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(40, 41, 37));

        // response data for raw and other types of file showing
        JPanel responseData = new JPanel();
        responseData.setBackground(new Color(40, 41, 37));

        JPanel responseCenterPanel = new JPanel(new CardLayout());
        responseCenterPanel.setBackground(new Color(40, 41, 37));
        responseCenterPanel.add(responseData, "response data");
        responseCenterPanel.add(headerPanel, "header");

        responsePanel.add(responseCenterPanel, BorderLayout.CENTER);

        CardLayout cardLayout = (CardLayout) responseCenterPanel.getLayout();



        JPanel headerTab = new JPanel(new GridLayout(1, 2));
        headerTab.setBackground(new Color(40, 41, 37));
        headerTab.setBorder(BorderFactory.createLineBorder(new Color(71, 72,69)));

        List<JButton> headers = new ArrayList<>();

        for(int i=0 ; i<2; i++) {

            headers.add(new JButton());
            if(i == 0) {

                headers.get(i).setBackground(new Color(71, 72, 69));
                headers.get(i).setForeground(Color.white);

            } else {

                headers.get(i).setBackground(new Color(40, 41, 37));
                headers.get(i).setForeground(new Color(153, 153, 153));

            }
            headers.get(i).setFont(new Font("Santa Fe Let", Font.PLAIN, 16));
            headers.get(i).setPreferredSize(new Dimension(200, 50));
            headers.get(i).setContentAreaFilled(false);
            headers.get(i).setOpaque(true);
            headers.get(i).setBorder(null);
            final int index = i;
            headers.get(i).addMouseListener(new MouseAdapter() {


                @Override
                public void mouseClicked(MouseEvent e) {

                    if(e.getButton() == MouseEvent.BUTTON1) {

                        if(index == 0) { // response data part

                            cardLayout.show(responseCenterPanel, "response data");
                            changeHeaderButtonsColor(headers, index, 2);

                        } else if(index == 1) { // header part

                            cardLayout.show(responseCenterPanel, "header");
                            changeHeaderButtonsColor(headers, index, 2);

                        }

                    } /*else if(e.getButton() == MouseEvent.BUTTON3) {

                        if(index == 0) {

                            Component component = (Component) e.getSource();
                            dataForm.show(headers.get(0), component.getX(), component.getY() + component.getHeight());

                        }

                    }*/

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                    changeHeaderButtonsColor(headers, index, 0);

                }

                @Override
                public void mouseExited(MouseEvent e) {

                    if(index == 0) { // form data part

                        for(Component com : responseCenterPanel.getComponents()) {

                            if(com.isVisible() && com != responseData) {

                                changeHeaderButtonsColor(headers, index, 1);

                            }

                        }

                    } else if(index == 1) { // header part

                        for(Component com : responseCenterPanel.getComponents()) {

                            if(com.isVisible() && com != headerPanel) {

                                changeHeaderButtonsColor(headers, index, 1);

                            }

                        }

                    }
                }
            });

        }

        headers.get(0).setText("Response Data   " + openMenu);
        headers.get(1).setText("Header");

        for (JButton button : headers) {
            headerTab.add(button);
        }

        responsePanel.add(headerTab, BorderLayout.NORTH);



        JPanel headerCoverPanel = new JPanel();
        headerCoverPanel.setBackground(new Color(40, 41, 37));
//        headerCoverPanel.setPreferredSize(new Dimension(200, 400));
        headerCoverPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        JScrollPane headerPanelScroll = new JScrollPane(headerCoverPanel);
        headerPanelScroll.setBackground(new Color(40, 41, 37));
        headerPanelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        headerPanelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        headerPanel.add(headerPanelScroll, BorderLayout.CENTER);

        JPanel initialHeaderPanel = new JPanel();
        initialHeaderPanel.setBackground(new Color(40, 41, 37));
        initialHeaderPanel.setLayout(new BoxLayout(initialHeaderPanel, BoxLayout.X_AXIS));
        initialHeaderPanel.setPreferredSize(new Dimension(500, 50));

        List<JLabel> initialLabels = new ArrayList<>();

        for(int i=0 ; i<2 ; i++) {

            initialLabels.add(new JLabel());
            initialLabels.get(i).setForeground(Color.white);
            initialLabels.get(i).setFont(new Font("Times New Roman", Font.PLAIN, 14));
            initialLabels.get(i).setPreferredSize(new Dimension(100, 50));


        }

        initialLabels.get(0).setText("NAME");
        initialLabels.get(1).setText("VALUE");

        initialHeaderPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        initialHeaderPanel.add(initialLabels.get(0));
        initialHeaderPanel.add(Box.createRigidArea(new Dimension(120, 0)));
        initialHeaderPanel.add(initialLabels.get(1));
        initialHeaderPanel.add(Box.createRigidArea(new Dimension(180, 0)));


        String name = "header 1";
        String value = "this is value of header 1";

        List<JPanel> headerField = new ArrayList<>();
        responseHeaderField(theme, headerField, name, value);
        responseHeaderField(theme, headerField, "ldsfjslkdjfslkajfajslkfjsdjlkfjslkjafjlksjajfjasjjasljfsdlkjf",
                "jsldfjlksjlafjsjfjadjgjsdajf sdaljfkldjalkfdslfnvjdsfladjzzxslkfjvlkjfljasjvfanflasdjj");
        responseHeaderField(theme, headerField, "ldsfjslkdjfslkajfajslkfjsdjlkfjslkjafjlksjajfjasjjasljfsdlkjf",
                "jsldfjlksjlafjsadasrwejfjadjgjsdajf sdaljfkldjalkfasddslfnvjdsfladjslkfjvlkjfljasjvfanflasdjj");
        responseHeaderField(theme, headerField, "ldsfjslkdjfslsdaskajfajslkfjsdjlkfjslkjafjlksjajfjasjjasljfsdlkjf",
                "jsldfjlksjlafjsjfjadjgjsdajf sdaljfkldjalkfdslfnvjdsfladjslkfjvlkjfljasjvfanflasdjj");
        responseHeaderField(theme, headerField, "ldsfjslkdjfslkajfajslkfsdafsfjsdjlkfjslkjafjlksjajfjasjjasljfsdlkjf",
                "jsldfjlksjlafjsjfjadjgjsdajf sdaljfkldjalkfdslfnvjdzzzzzzsfladjslkfjvlkjfljasjvfanflasdjj");
        responseHeaderField(theme, headerField, "ldsfjslkdjfslkajfajslkfsdafsfjsdjlkfjslkjafjlksjajfjasjjasljfsdlkjf",
                "jsldfjlksjlafjsjfjadjgjsdajf sdaljfkldjalkfdslfnvjdzzzzzzsfladjslkfjvlkjfljasjvfanflasdjj");
        responseHeaderField(theme, headerField, "ldsfjslkdjfslkajfajslkfsdafsfjsdjlkfjslkjafjlksjajfjasjjasljfsdlkjf",
                "jsldfjlksjlafjsjfjadjgjsdajf sdaljfkldjalkfdslfnvjdzzzzzzsfladjslkfjvlkjfljasjvfanflasdjj");
        responseHeaderField(theme, headerField, "ldsfjslkdjfslkajfajslkfsdafsfjsdjlkfjslkjafjlksjajfjasjjasljfsdlkjf",
                "jsldfjlksjlafjsjfjadjgjsdajf sdaljfkldjalkfdslfnvjdzzzzzzsfladjslkfjvlkjfljasjvfanflasdjj");
        responseHeaderField(theme, headerField, "ldsfjslkdjfslkajfajslkfsdafsfjsdjlkfjslkjafjlksjajfjasjjasljfsdlkjf",
                "jsldfjlksjlafjsjfjadjgjsdajf sdaljfkldjalkfdslfnvjdzzzzzzsfladjslkfjvlkjfljasjvfanflasdjj");
        responseHeaderField(theme, headerField, "ldsfjslkdjfslkajfajslkfsdafsfjsdjlkfjslkjafjlksjajfjasjjasljfsdlkjf",
                "jsldfjlksjlafjsjfjadjgjsdajf sdaljfkldjalkfdslfnvjdzzzzzzsfladjslkfjvlkjfljasjvfanflasdjj");
        responseHeaderField(theme, headerField, "ldsfjslkdjfslkajfajslkfsdafsfjsdjlkfjslkjafjlksjajfjasjjasljfsdlkjf",
                "jsldfjlksjlafjsjfjadjgjsdajf sdaljfkldjalkfdslfnvjdzzzzzzsfladjslkfjvlkjfljasjvfanflasdjj");

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(5, 0, 0, 0);
//        headerCoverPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        headerCoverPanel.add(initialHeaderPanel, constraints);
//        headerCoverPanel.add(Box.createRigidArea(new Dimension(0, 10)));


        System.out.println(headerField.size());
        int number = 1;
        for (JPanel panel : headerField) {

            constraints.fill = GridBagConstraints.HORIZONTAL;

            constraints.gridx = 0;
            constraints.gridy = number;




            if(number >= headerField.size()) {

                constraints.weightx = 1;
                constraints.weighty = 1;
                constraints.anchor = GridBagConstraints.PAGE_START;

            }
            headerCoverPanel.add(panel, constraints);
            if(number % 2 == 0) {

                panel.setBackground(new Color(40, 41, 37));

            } else {
                panel.setBackground(new Color(49, 50, 46));
            }
            number++;

        }






        // end of center of response part




        return rightPanel;

    }




    /**
     * this method change color when an event happen and change color
     * of buttons related to specific state.
     *
     * @param headers list of buttons
     * @param index current index of list
     * @param state determine type of operation
     */
    private void changeHeaderButtonsColor(List<JButton> headers,int index, int state) {

        if(state == 0) { // selected form

            headers.get(index).setBackground(new Color(71, 72, 69));
            headers.get(index).setForeground(Color.white);
            headers.get(index).setBorder(BorderFactory.createLineBorder(new Color(91, 92, 90)));

        } else if(state == 1) { // nonselected form

            headers.get(index).setBackground(new Color(40, 41, 37));
            headers.get(index).setForeground(new Color(71, 72, 69));
            headers.get(index).setBorder(null);

        } else if(state == 2) { // nonselect other buttons

            for(int i=0 ; i<headers.size() ; i++) {

                if(i != index) {

                    changeHeaderButtonsColor(headers, i, 1);

                }

            }

        }

    }


    /**
     * this method create header field in header tab for request body part
     *
     * @param theme theme of these frame
     * @param headerField list of header fields
     * @param headerPanel panel of header fields
     * @return prepared header field panel
     */
    private JPanel headerFieldCreator(int theme, List<JPanel> headerField, JPanel headerPanel, boolean isFormData) {

        JPanel field = new JPanel();
        if(theme == LIGHT_THEME) {
            // TODO: after completing dark theme
        } else if(theme == DARK_THEME) {
            field.setBackground(new Color(40, 41, 37));
        }
        field.setLayout(new BoxLayout(field, BoxLayout.X_AXIS));
        field.setPreferredSize(new Dimension(400, 50));
        field.setMaximumSize(new Dimension(2000, 50));
        field.add(Box.createRigidArea(new Dimension(20, 0)));



        JPopupMenu settingPopup = new JPopupMenu();
        settingPopup.setPreferredSize(new Dimension(200, 40));
        JMenuItem deleteAllItem = new JMenuItem("Delete All Items");
        deleteAllItem.addActionListener(e -> {
            if(headerField.size() > 1) {

                for(int i = 1 ; i < headerField.size() ; i++) {

                    headerPanel.remove(headerField.get(i));

                }

                headerField.subList(1, headerField.size()).clear();

                System.out.println(headerField.size());

                addHeaderFieldPanel(headerPanel, headerField);
                headerPanel.revalidate();
                headerPanel.repaint();

            }
        });
        settingPopup.add(deleteAllItem);


        JButton settingButton = new JButton();
        settingButton.setBackground(new Color(40, 41, 37));
        settingButton.setContentAreaFilled(false);
        settingButton.setOpaque(true);

        try {

            File settingImage = new File(currentDir + "\\setting_icon_normal.png");
            if(settingImage.exists()) {

                ImageIcon settingIcon = new ImageIcon(currentDir + "\\setting_icon_normal.png");
                settingButton.setIcon(settingIcon);

            } else {
                System.out.println("setting icon normal doesn't found!!!");
            }

        } catch (Exception err) {
            err.printStackTrace();
        }

        settingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(e.getButton() == MouseEvent.BUTTON1) {

                    if(headerField.contains(field) && headerField.get(headerField.size()-1) == field) {

                        Component button = (Component)e.getSource();
                        settingPopup.show(settingButton, button.getX(), button.getY()+button.getHeight());

                    }

                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                try {

                    File settingImage = new File(currentDir + "\\setting_icon_white.png");
                    if(settingImage.exists()) {

                        ImageIcon settingIcon = new ImageIcon(currentDir + "\\setting_icon_white.png");
                        settingButton.setIcon(settingIcon);

                    } else {
                        System.out.println("setting icon white doesn't found!!!");
                    }

                } catch (Exception err) {
                    err.printStackTrace();
                }

            }

            @Override
            public void mouseExited(MouseEvent e) {

                try {

                    File settingImage = new File(currentDir + "\\setting_icon_normal.png");
                    if(settingImage.exists()) {

                        ImageIcon settingIcon = new ImageIcon(currentDir + "\\setting_icon_normal.png");
                        settingButton.setIcon(settingIcon);

                    } else {
                        System.out.println("setting icon normal doesn't found!!!");
                    }

                } catch (Exception err) {
                    err.printStackTrace();
                }

            }
        });



        JTextField headerTextField = new JTextField();

        if(theme == LIGHT_THEME) {
            // TODO: after completing dark theme
        } else if(theme == DARK_THEME) {
            headerTextField.setBackground(new Color(40, 41, 37));
        }

        headerTextField.setForeground(new Color(91, 92, 90));
        headerTextField.setFont(new Font("Santa Fe Let", Font.PLAIN, 15));
        headerTextField.setPreferredSize(new Dimension(250, 30));
        headerTextField.setMaximumSize(new Dimension(2000, 35));
        headerTextField.setBorder(BorderFactory.createLineBorder(new Color(71, 72, 69), 1));

        if(isFormData) {

            if(headerField.isEmpty()) {
                headerTextField.setText("name");
            } else {
                headerTextField.setText("New name");
            }

        } else {

            if(headerField.isEmpty()) {
                headerTextField.setText("header");
            } else {
                headerTextField.setText("New header");
            }

        }

        String initialName = headerTextField.getText();

        headerTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {

                if(headerTextField.getText().equals("header") || headerTextField.getText().equals("New header")
                || headerTextField.getText().equals("name") || headerTextField.getText().equals("New name")) {

                    headerTextField.setText("");

                }
                if(headerField.contains(field)) {
                    if(headerField.get(headerField.size()-1) == field) {

                        System.out.println("last field found!!!");
                        headerField.add(headerFieldCreator(theme, headerField, headerPanel, isFormData));
                        addHeaderFieldPanel(headerPanel, headerField);
                        headerPanel.revalidate();
                        headerPanel.repaint();

                    }
                }

            }

            @Override
            public void focusLost(FocusEvent e) {

                if(headerTextField.getText().equals("")) {

                    headerTextField.setText(initialName);

                }

            }
        });

        headerTextField.setAlignmentY(Component.CENTER_ALIGNMENT);

        JPanel valueCardLayout = new JPanel(new CardLayout());
        valueCardLayout.setBackground(new Color(40, 41, 37));
        valueCardLayout.setMaximumSize(new Dimension(2000, 35));
        valueCardLayout.setAlignmentY(Component.CENTER_ALIGNMENT);


        JTextField valueTextField = new JTextField();

        if(theme == LIGHT_THEME) {
            // TODO: after completing dark theme
        } else if(theme == DARK_THEME) {
            valueTextField.setBackground(new Color(40, 41, 37));
        }

        valueTextField.setForeground(new Color(91, 92, 90));
        valueTextField.setFont(new Font("Santa Fe Let", Font.PLAIN, 15));
        valueTextField.setPreferredSize(new Dimension(200, 30));
        valueTextField.setMaximumSize(new Dimension(2000, 35));
        valueTextField.setBorder(BorderFactory.createLineBorder(new Color(71, 72, 69), 1));


        if(headerField.isEmpty()) {
            valueTextField.setText("value");
        } else {
            valueTextField.setText("new value");
        }

        String initialName1 = valueTextField.getText();

        valueTextField.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {

                if(valueTextField.getText().equals("value") || valueTextField.getText().equals("new value")) {

                    valueTextField.setText("");

                }
                if(headerField.contains(field)) {
                    if(headerField.get(headerField.size()-1) == field) {

                        System.out.println("last field found!!!");
                        headerField.add(headerFieldCreator(theme, headerField, headerPanel, isFormData));
                        addHeaderFieldPanel(headerPanel, headerField);
                        headerPanel.revalidate();
                        headerPanel.repaint();

                    }
                }

            }

            @Override
            public void focusLost(FocusEvent e) {

                if(valueTextField.getText().equals("")) {

                    valueTextField.setText(initialName1);

                }

            }
        });

        valueTextField.setAlignmentY(Component.CENTER_ALIGNMENT);

        JButton editButton = new JButton("\u270E Click to Edit");
        editButton.setBackground(new Color(40, 41, 37));
        editButton.setForeground(Color.white);
        editButton.setFont(new Font("Santa Fe Let", Font.PLAIN, 18));
        editButton.setBorder(BorderFactory.createLineBorder(new Color(71, 72, 69), 1));
        editButton.setPreferredSize(new Dimension(200, 30));
        editButton.setContentAreaFilled(false);
        editButton.setOpaque(true);
        JFrame mainFrame = this;
        editButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(e.getButton() == MouseEvent.BUTTON1) {

                    // Todo : handling concurrent problems
                    new EditorFrame("RedInsomnia-Editor Panel", theme, mainFrame);

                }

            }
        });


        valueCardLayout.add(valueTextField, "textField");
        valueCardLayout.add(editButton, "button");

        CardLayout cardLayout = (CardLayout) valueCardLayout.getLayout();
        cardLayout.show(valueCardLayout, "textField");


        JCheckBox headerState = new JCheckBox();
        headerState.setAlignmentY(Component.CENTER_ALIGNMENT);
        headerState.setBackground(new Color(40, 41, 37));
        headerState.setForeground(new Color(71, 72, 69));
        headerState.addItemListener(e -> {
            if(e.getItemSelectable() == headerState && headerState.isSelected()) {

                headerTextField.setForeground(new Color(60, 60, 60));
                headerTextField.setBorder(BorderFactory.createDashedBorder(new Color(60, 60, 60)));
                valueTextField.setForeground(new Color(60, 60, 60));
                valueTextField.setBorder(BorderFactory.createDashedBorder(new Color(60, 60, 60)));
                editButton.setForeground(new Color(60, 60, 60));
                editButton.setBorder(BorderFactory.createDashedBorder(new Color(60, 60, 60)));

            } else if(!headerState.isSelected()){

                headerTextField.setForeground(new Color(91, 92, 90));
                headerTextField.setBorder(BorderFactory.createLineBorder(new Color(71, 72, 69)));
                valueTextField.setForeground(new Color(91, 92, 90));
                valueTextField.setBorder(BorderFactory.createLineBorder(new Color(71, 72, 69)));
                editButton.setForeground(Color.white);
                editButton.setBorder(BorderFactory.createLineBorder(new Color(71, 72, 69)));

            }
        });

        JButton trashButton = new JButton();
        trashButton.setBackground(new Color(40, 41, 37));
        trashButton.setContentAreaFilled(false);
        trashButton.setOpaque(true);
        try{
            File trashImage = new File(currentDir + "\\trash_icon_normal.png");
            if(trashImage.exists()) {

                ImageIcon trashIcon = new ImageIcon(currentDir + "\\trash_icon_normal.png");
                trashButton.setIcon(trashIcon);

            } else {
                System.out.println("trash image doesn't exist!!!");
            }
        } catch (Exception err) {
            err.printStackTrace();
        }

        trashButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(e.getButton() == MouseEvent.BUTTON1) {

                    if(headerField.indexOf(field) != 0) {

                        headerPanel.remove(field);
                        headerField.remove(field);
                        addHeaderFieldPanel(headerPanel, headerField);
                        revalidate();
                        repaint();

                    }

                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                try{
                    File trashImage = new File(currentDir + "\\trash_icon_white.png");
                    if(trashImage.exists()) {

                        ImageIcon trashIcon = new ImageIcon(currentDir + "\\trash_icon_white.png");
                        trashButton.setIcon(trashIcon);

                    } else {
                        System.out.println("trash image doesn't exist!!!");
                    }
                } catch (Exception err) {
                    err.printStackTrace();
                }

            }

            @Override
            public void mouseExited(MouseEvent e) {

                try{
                    File trashImage = new File(currentDir + "\\trash_icon_normal.png");
                    if(trashImage.exists()) {

                        ImageIcon trashIcon = new ImageIcon(currentDir + "\\trash_icon_normal.png");
                        trashButton.setIcon(trashIcon);

                    } else {
                        System.out.println("trash image doesn't exist!!!");
                    }
                } catch (Exception err) {
                    err.printStackTrace();
                }

            }
        });

        JButton textType = null;
        if(isFormData) {

            JPopupMenu textTypePopup = new JPopupMenu();
            textTypePopup.setPreferredSize(new Dimension(200, 75));

            JMenuItem text = new JMenuItem("Text");
            JMenuItem multiLineText = new JMenuItem("Text (Multi-line)");

            text.addActionListener(e -> cardLayout.show(valueCardLayout, "textField"));
            multiLineText.addActionListener(e -> cardLayout.show(valueCardLayout, "button"));

            textTypePopup.add(text);
            textTypePopup.add(multiLineText);

            textType = new JButton(openMenu);
            textType.setBackground(new Color(40, 41, 37));
            textType.setForeground(new Color(71, 72, 69));
            textType.setFont(new Font("Santa Fe Let", Font.PLAIN, 18));
            textType.setContentAreaFilled(false);
            textType.setOpaque(true);
            final JButton finalTextType = textType;
            textType.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    if(e.getButton() == MouseEvent.BUTTON1) {

                        Component button = (Component)e.getSource();
                        textTypePopup.show(finalTextType, -150, button.getY()+button.getHeight());

                    }

                }
            });

        }



        field.add(settingButton);
        field.add(Box.createRigidArea(new Dimension(5, 0)));
        field.add(headerTextField);
        field.add(Box.createRigidArea(new Dimension(10, 0)));
        field.add(valueCardLayout);
        if(textType != null) {

            field.add(Box.createRigidArea(new Dimension(5, 0)));
            field.add(textType);

        }
        field.add(Box.createRigidArea(new Dimension(10, 0)));
        field.add(headerState);
        field.add(Box.createRigidArea(new Dimension(5, 0)));
        field.add(trashButton);
        field.add(Box.createRigidArea(new Dimension(5, 0)));

        return field;
    }




    private Color setSpecificColor(int theme, int type) {

        // define light color for LIGHT_THEME

        // end of light color

        // define dark color for DARK_THEME

        darkColor.add(new Color(71, 72, 69)); // color of left panel
        darkColor.add(new Color(105, 94, 184)); // color of insomnia button in left panel

        // end of dark color

        if(theme == LIGHT_THEME) {

        } else if(theme == DARK_THEME) {

        }

        return Color.white;
    }

    private void addHeaderFieldPanel(JPanel headerPanel, List<JPanel> headerField) {

        System.out.println(headerField.size());
        for (JPanel panel : headerField) {

            headerPanel.add(panel);

        }

    }


    /**
     * this code taken from stackoverflow
     * actually this method convert long byte size of a file to bigger scale
     *
     * @param bytes file size
     * @return string of file size
     *
     * @see <a href="https://stackoverflow.com/questions/3758606/how-to-convert-byte-size-into-human-readable-format-in-java">StackOverFlow</a>
     */
    private String convertBytetoMegaByte(long bytes) {

        if (-1000 < bytes && bytes < 1000) {
            return bytes + " B";
        }
        CharacterIterator ci = new StringCharacterIterator("kMGTPE");
        while (bytes <= -999_950 || bytes >= 999_950) {
            bytes /= 1000;
            ci.next();
        }
        return String.format("%.1f %cB", bytes / 1000.0, ci.current());

    }



    private void responseHeaderField(int theme, List<JPanel> headerField, String name, String value) {

        JPanel headerFieldPanel = new JPanel();
        headerFieldPanel.setBackground(new Color(40, 41, 37));
        headerFieldPanel.setLayout(new BoxLayout(headerFieldPanel, BoxLayout.X_AXIS));

        List<JTextArea> labels = new ArrayList<>();

        for(int i=0 ; i<2 ; i++) {

            labels.add(new JTextArea());
            labels.get(i).setLineWrap(true);
            labels.get(i).setForeground(Color.white);
            labels.get(i).setFont(new Font("Santa Fe Let", Font.PLAIN, 13));
//            labels.get(i).setPreferredSize(new Dimension(80, 50));
            labels.get(i).setMaximumSize(new Dimension(200, Integer.MAX_VALUE));
            labels.get(i).setEditable(false);
            labels.get(i).setOpaque(false);
            labels.get(i).setAlignmentY(Component.CENTER_ALIGNMENT);

        }

        labels.get(0).setText(name);
        labels.get(1).setText(value);

        headerFieldPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        headerFieldPanel.add(labels.get(0));
        headerFieldPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        headerFieldPanel.add(labels.get(1));
        headerFieldPanel.add(Box.createRigidArea(new Dimension(10, 0)));


        headerField.add(headerFieldPanel);

    }


}
