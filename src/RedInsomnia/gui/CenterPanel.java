package RedInsomnia.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;

/**
 * RedInsomnia.gui.CenterPanel
 *
 * This class create center panel for RedInsomnia
 *
 * @author Amir01
 * @version
 *
 * @see MainFrame
 * @see JPanel
 */
public class CenterPanel extends JPanel{

    private static final long serialVersionUID = 6529685098267757690L;

    private MainFrame mainFrame;
    private String openMenu = "\uD83D\uDF83";
    private List<List<Color>> themes;
    private int theme;
    private String currentDir;


    /**
     * Constructor of RedInsomnia.gui.CenterPanel class
     *
     * @param mainFrame mainFrame of this panel
     */
    public CenterPanel(MainFrame mainFrame) {

        super();

        this.themes = mainFrame.getThemes();
        this.theme = mainFrame.getTheme();
        this.currentDir = mainFrame.getCurrentDir();

        this.mainFrame = mainFrame;

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(550, 580));
        this.setBorder(BorderFactory.createLineBorder(themes.get(theme).get(1), 1));

        JPanel urlPanel = new JPanel(new BorderLayout(5, 0));
        urlPanel.setBackground(Color.white);
        urlPanel.setPreferredSize(new Dimension(550, 65));
        this.add(urlPanel, BorderLayout.NORTH);

        JButton httpMethodButton = new JButton(" GET       " + openMenu);
        httpMethodButton.setBackground(Color.white);
        httpMethodButton.setForeground(themes.get(theme).get(4));
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

                httpMethodButton.setBackground(themes.get(theme).get(5));

            }

            @Override
            public void mouseExited(MouseEvent e) {

                httpMethodButton.setBackground(Color.white);

            }
        });


        JTextField urlTextField = new JTextField("changelog.insomnia.rest/changelog.json");
        urlTextField.setBackground(Color.white);
        urlTextField.setForeground(themes.get(theme).get(4));
        urlTextField.setFont(new Font("Santa Fe Let", Font.PLAIN, 15));
        urlTextField.setBorder(null);
        urlPanel.add(urlTextField, BorderLayout.CENTER);


        JButton sendButton = new JButton("Send");
        sendButton.setBackground(Color.white);
        sendButton.setForeground(themes.get(theme).get(4));
        sendButton.setFont(new Font("Santa Fe Let", Font.PLAIN, 16));
        sendButton.setBorder(null);
        sendButton.setPreferredSize(new Dimension(80, 65));
        sendButton.setContentAreaFilled(false);
        sendButton.setOpaque(true);
        urlPanel.add(sendButton, BorderLayout.EAST);
        sendButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {

                sendButton.setBackground(themes.get(theme).get(5));

            }

            @Override
            public void mouseExited(MouseEvent e) {

                sendButton.setBackground(Color.white);

            }
        });



        JPanel requestSettingPanel = new JPanel(new BorderLayout());
        requestSettingPanel.setBackground(themes.get(theme).get(6));
        this.add(requestSettingPanel, BorderLayout.CENTER);


        JPanel mainSettingPanel = new JPanel(new CardLayout());
        mainSettingPanel.setBackground(themes.get(theme).get(6));
        mainSettingPanel.setBorder(BorderFactory.createLineBorder(themes.get(theme).get(1), 1));

        JPanel formData = new JPanel(new CardLayout());
        formData.setBackground(themes.get(theme).get(6));

        JPanel formPanel = new JPanel();
        JPanel jsonPanel = new JPanel();
        JPanel binaryFilePanel = new JPanel();

        formData.add(formPanel, "form panel");
        formData.add(jsonPanel, "json");
        formData.add(binaryFilePanel, "binary file");

        CardLayout formDataCardLayout = (CardLayout) formData.getLayout();
        formDataCardLayout.show(formData, "form panel");


        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(themes.get(theme).get(6));

        mainSettingPanel.add(formData , "form data");
        mainSettingPanel.add(header, "header");

        CardLayout cardLayout = (CardLayout) mainSettingPanel.getLayout();

        cardLayout.show(mainSettingPanel, "formData");

        requestSettingPanel.add(mainSettingPanel, BorderLayout.CENTER);

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
        headerTab.setBackground(themes.get(theme).get(6));
        headerTab.setBorder(BorderFactory.createLineBorder(themes.get(theme).get(1)));

        java.util.List<JButton> headers = new ArrayList<>();

        for(int i=0 ; i<2; i++) {

            headers.add(new JButton());
            if(i == 0) {

                headers.get(i).setBackground(themes.get(theme).get(1));
                headers.get(i).setForeground(themes.get(theme).get(11));

            } else {

                headers.get(i).setBackground(themes.get(theme).get(6));
                headers.get(i).setForeground(themes.get(theme).get(7));

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
        headerPanel.setBackground(themes.get(theme).get(6));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        java.util.List<JPanel> headerField = new ArrayList<>();

        headerField.add(headerFieldCreator(headerField, headerPanel, false));
        addHeaderFieldPanel(headerPanel, headerField);

        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));


        JScrollPane scrolledHeaderPanel = new JScrollPane(headerPanel);
        scrolledHeaderPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrolledHeaderPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrolledHeaderPanel.setBackground(themes.get(theme).get(6));
        scrolledHeaderPanel.setBorder(null);

        header.add(scrolledHeaderPanel, BorderLayout.CENTER);

        // end of changes to header part of central panel

        // this part related to form data part of central panel

        formPanel.setBackground(themes.get(theme).get(6));
        formPanel.setLayout(new BorderLayout());


        JPanel coverFormPanel = new JPanel();
        coverFormPanel.setBackground(themes.get(theme).get(6));
        coverFormPanel.setLayout(new BoxLayout(coverFormPanel, BoxLayout.Y_AXIS));


        JScrollPane coverFormPanelScroll = new JScrollPane(coverFormPanel);
        coverFormPanelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        coverFormPanelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        coverFormPanelScroll.setBorder(null);

        formPanel.add(coverFormPanelScroll, BorderLayout.CENTER);


        List<JPanel> formDataField = new ArrayList<>();
        formDataField.add(headerFieldCreator(formDataField, coverFormPanel, true));
        addHeaderFieldPanel(coverFormPanel, formDataField);

        // end of changes to form data part of central panel

        // this part related to binary file panel in central panel

        binaryFilePanel.setBackground(themes.get(theme).get(6));
        binaryFilePanel.setLayout(new BoxLayout(binaryFilePanel, BoxLayout.Y_AXIS));

        JLabel selectedFile = new JLabel("SELECTED FILE");
        selectedFile.setFont(new Font("Santa Fe Let", Font.PLAIN, 18));
        selectedFile.setForeground(themes.get(theme).get(8));
        selectedFile.setAlignmentX(Component.LEFT_ALIGNMENT);


        JTextArea filePathArea = new JTextArea("No file selected");
        filePathArea.setBackground(themes.get(theme).get(6));
        filePathArea.setForeground(themes.get(theme).get(8));
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

        resetFile.setBackground(themes.get(theme).get(6));
        resetFile.setForeground(themes.get(theme).get(8));
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
                filePathArea.setForeground(themes.get(theme).get(1));
                resetFile.setBackground(themes.get(theme).get(6));
                resetFile.setForeground(themes.get(theme).get(8));
                resetFile.setEnabled(false);

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                if(resetFile.isEnabled()) {
                    resetFile.setBackground(themes.get(theme).get(1));
                    resetFile.setForeground(themes.get(theme).get(11));
                }

            }

            @Override
            public void mouseExited(MouseEvent e) {

                resetFile.setBackground(themes.get(theme).get(6));
                resetFile.setForeground(themes.get(theme).get(8));

            }
        });



        JButton chooseButton = new JButton("Choose File");
        chooseButton.setBackground(themes.get(theme).get(6));
        chooseButton.setForeground(themes.get(theme).get(11));
        chooseButton.setFont(new Font("Santa Fe Let", Font.PLAIN, 15));
        chooseButton.setPreferredSize(new Dimension(150, 50));
        chooseButton.setBorder(BorderFactory.createLineBorder(themes.get(theme).get(8), 1));
        chooseButton.setContentAreaFilled(false);
        chooseButton.setOpaque(true);
        chooseButton.setBounds(100, 75, 150, 50);

        chooseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                JFileChooser fileChooser = new JFileChooser();
                int i = fileChooser.showOpenDialog(mainFrame);
                if(i == JFileChooser.APPROVE_OPTION) {

                    File file = fileChooser.getSelectedFile();
                    filePathArea.setText(file.getAbsolutePath());
                    filePathArea.append(" (" + convertBytetoMegaByte(file.length()) + ")");
                    filePathArea.setForeground(themes.get(theme).get(11));
                    resetFile.setForeground(themes.get(theme).get(11));
                    resetFile.setEnabled(true);

                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                chooseButton.setBackground(themes.get(theme).get(1));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                chooseButton.setBackground(themes.get(theme).get(6));
            }
        });


        JPanel buttonBinaryPanel = new JPanel(new FlowLayout());
        buttonBinaryPanel.setBackground(themes.get(theme).get(6));
        buttonBinaryPanel.add(resetFile);
        buttonBinaryPanel.add(chooseButton);


        binaryFilePanel.add(Box.createRigidArea(new Dimension(0, 15)));
        binaryFilePanel.add(selectedFile);
        binaryFilePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        binaryFilePanel.add(filePathScrollArea);
        binaryFilePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        binaryFilePanel.add(buttonBinaryPanel);


        // end of changes to binary file panel in central panel

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

            headers.get(index).setBackground(themes.get(theme).get(1));
            headers.get(index).setForeground(themes.get(theme).get(11));
            headers.get(index).setBorder(BorderFactory.createLineBorder(themes.get(theme).get(8)));

        } else if(state == 1) { // nonselected form

            headers.get(index).setBackground(themes.get(theme).get(6));
            headers.get(index).setForeground(themes.get(theme).get(1));
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
     * @param headerField list of header fields
     * @param headerPanel panel of header fields
     * @return prepared header field panel
     */
    private JPanel headerFieldCreator(List<JPanel> headerField, JPanel headerPanel, boolean isFormData) {

        JPanel field = new JPanel();
        field.setBackground(themes.get(theme).get(6));
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
        settingButton.setBackground(themes.get(theme).get(6));
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

        if(theme == MainFrame.LIGHT_THEME) {
            // TODO: after completing dark theme
        } else if(theme == MainFrame.DARK_THEME) {
            headerTextField.setBackground(themes.get(theme).get(6));
        }

        headerTextField.setForeground(themes.get(theme).get(8));
        headerTextField.setFont(new Font("Santa Fe Let", Font.PLAIN, 15));
        headerTextField.setPreferredSize(new Dimension(250, 30));
        headerTextField.setMaximumSize(new Dimension(2000, 35));
        headerTextField.setBorder(BorderFactory.createLineBorder(themes.get(theme).get(1), 1));

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
                        headerField.add(headerFieldCreator(headerField, headerPanel, isFormData));
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
        valueCardLayout.setBackground(themes.get(theme).get(6));
        valueCardLayout.setMaximumSize(new Dimension(2000, 35));
        valueCardLayout.setAlignmentY(Component.CENTER_ALIGNMENT);


        JTextField valueTextField = new JTextField();

        if(theme == MainFrame.LIGHT_THEME) {
            // TODO: after completing dark theme
        } else if(theme == MainFrame.DARK_THEME) {
            valueTextField.setBackground(themes.get(theme).get(6));
        }

        valueTextField.setForeground(themes.get(theme).get(8));
        valueTextField.setFont(new Font("Santa Fe Let", Font.PLAIN, 15));
        valueTextField.setPreferredSize(new Dimension(200, 30));
        valueTextField.setMaximumSize(new Dimension(2000, 35));
        valueTextField.setBorder(BorderFactory.createLineBorder(themes.get(theme).get(1), 1));


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
                        headerField.add(headerFieldCreator(headerField, headerPanel, isFormData));
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
        editButton.setBackground(themes.get(theme).get(6));
        editButton.setForeground(themes.get(theme).get(11));
        editButton.setFont(new Font("Santa Fe Let", Font.PLAIN, 18));
        editButton.setBorder(BorderFactory.createLineBorder(themes.get(theme).get(1), 1));
        editButton.setPreferredSize(new Dimension(200, 30));
        editButton.setContentAreaFilled(false);
        editButton.setOpaque(true);
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
        headerState.setBackground(themes.get(theme).get(6));
        headerState.setForeground(themes.get(theme).get(1));
        headerState.addItemListener(e -> {
            if(e.getItemSelectable() == headerState && headerState.isSelected()) {

                headerTextField.setForeground(themes.get(theme).get(10));
                headerTextField.setBorder(BorderFactory.createDashedBorder(themes.get(theme).get(10)));
                valueTextField.setForeground(themes.get(theme).get(10));
                valueTextField.setBorder(BorderFactory.createDashedBorder(themes.get(theme).get(10)));
                editButton.setForeground(themes.get(theme).get(10));
                editButton.setBorder(BorderFactory.createDashedBorder(themes.get(theme).get(10)));

            } else if(!headerState.isSelected()){

                headerTextField.setForeground(themes.get(theme).get(8));
                headerTextField.setBorder(BorderFactory.createLineBorder(themes.get(theme).get(1)));
                valueTextField.setForeground(themes.get(theme).get(8));
                valueTextField.setBorder(BorderFactory.createLineBorder(themes.get(theme).get(1)));
                editButton.setForeground(themes.get(theme).get(11));
                editButton.setBorder(BorderFactory.createLineBorder(themes.get(theme).get(1)));

            }
        });

        JButton trashButton = new JButton();
        trashButton.setBackground(themes.get(theme).get(6));
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
                        mainFrame.revalidate();
                        mainFrame.repaint();

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
            textType.setBackground(themes.get(theme).get(6));
            textType.setForeground(themes.get(theme).get(1));
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

}
