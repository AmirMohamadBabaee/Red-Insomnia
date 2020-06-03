package RedInsomnia.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * RedInsomnia.gui.RightPanel
 *
 * This class create right panel for RedInsomnia
 *
 * @author Amir01
 * @version
 *
 * @see MainFrame
 * @see JPanel
 */
public class RightPanel extends JPanel {

    private static final long serialVersionUID = 6529685098267757690L;

    private MainFrame mainFrame;
    private String openMenu = "\uD83D\uDF83";
    private List<List<Color>> themes;
    private int theme;
    private String currentDir;

    private String statusCodeStr = "";
    private String delayTimeStr = "";
    private String responseSizeStr = "";
    private String rawResponseStr = "Your Expected Response Body Will Show Here!";
    private String imagePathStr = "";
    private Map<String, String> headerMap = new LinkedHashMap<>();

    private JButton requestStatus;
    private JButton delayTime;
    private JButton fileSize;
    private JPanel visualPanel;
    private JEditorPane rawPanel;

    private List<JPanel> headerField;
    private GridBagConstraints constraints;
    private JPanel headerCoverPanel;
    private JPanel initialHeaderPanel;
    private JScrollPane headerPanelScroll;





    /**
     * Constructor of RedInsomnia.gui.RightPanel Class in RedInsomnia
     *
     * @param mainFrame main frame of program
     */
    public RightPanel(MainFrame mainFrame) {

        super();

        this.themes = mainFrame.getThemes();
        this.theme = mainFrame.getTheme();
        this.currentDir = mainFrame.getCurrentDir();

        this.mainFrame = mainFrame;

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(450, 580));
        this.setBorder(BorderFactory.createLineBorder(themes.get(theme).get(1), 1));

        JPanel responseStatusPanel = new JPanel();
        responseStatusPanel.setBackground(Color.white);
        responseStatusPanel.setPreferredSize(new Dimension(550, 65));
        this.add(responseStatusPanel, BorderLayout.NORTH);
        responseStatusPanel.setLayout(new BorderLayout());

        JPanel responseDetailPanel = new JPanel();
        responseDetailPanel.setBackground(Color.white);
        responseStatusPanel.add(responseDetailPanel, BorderLayout.CENTER);



        // north of right panel in RedInsomnia

        BoxLayout boxLayout = new BoxLayout(responseDetailPanel, BoxLayout.X_AXIS);
        responseDetailPanel.setLayout(boxLayout);

        // response status button
        requestStatus = new JButton(this.statusCodeStr);
        getRequestStatus().setBackground(new Color(117, 186, 36));
        getRequestStatus().setForeground(Color.white);
        getRequestStatus().setFont(new Font("Santa Fe Let", Font.PLAIN, 15));
        getRequestStatus().setPreferredSize(new Dimension(50, 30));
        getRequestStatus().setContentAreaFilled(false);
        getRequestStatus().setOpaque(true);

        // delay time of request
        delayTime = new JButton(this.delayTimeStr);
        getDelayTime().setBackground(new Color(224, 224, 224));
        getDelayTime().setForeground(new Color(116, 116, 116));
        getDelayTime().setPreferredSize(new Dimension(50, 30));
        getDelayTime().setFont(new Font("Santa Fe Let", Font.PLAIN, 15));
        getDelayTime().setContentAreaFilled(false);
        getDelayTime().setOpaque(true);

        // size of response file
        fileSize = new JButton(this.responseSizeStr);
        getFileSize().setBackground(new Color(224, 224, 224));
        getFileSize().setForeground(new Color(116, 116, 116));
        getFileSize().setPreferredSize(new Dimension(50, 30));
        getFileSize().setFont(new Font("Santa Fe Let", Font.PLAIN, 15));
        getFileSize().setContentAreaFilled(false);
        getFileSize().setOpaque(true);


        // save request
        JButton saveButton = new JButton("Save");
        saveButton.setBackground(Color.white);
        saveButton.setForeground(new Color(116, 116, 116));
        saveButton.setPreferredSize(new Dimension(100, 65));
        saveButton.setFont(new Font("Santa Fe Let", Font.PLAIN, 15));
        saveButton.setContentAreaFilled(false);
        saveButton.setOpaque(true);
        MessageBean bean = mainFrame.getSaveBean();
        saveButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                bean.setValue("true");
            }

            @Override
            public void mouseEntered(MouseEvent e) {

                saveButton.setBackground(themes.get(theme).get(5));

            }

            @Override
            public void mouseExited(MouseEvent e) {

                saveButton.setBackground(Color.white);

            }
        });

        responseStatusPanel.add(saveButton, BorderLayout.EAST);


        responseDetailPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        responseDetailPanel.add(getRequestStatus());
        responseDetailPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        responseDetailPanel.add(getDelayTime());
        responseDetailPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        responseDetailPanel.add(getFileSize());

        // end of response status part

        // this part related to center of response part

        JPanel responsePanel = new JPanel(new BorderLayout());
        responsePanel.setBackground(themes.get(theme).get(6));
        this.add(responsePanel, BorderLayout.CENTER);


        // header panel for header button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(themes.get(theme).get(6));

        // response data for raw and other types of file showing
        JPanel responseData = new JPanel(new CardLayout());
        responseData.setBackground(themes.get(theme).get(6));


        rawPanel = new JEditorPane();
        getRawPanel().setBackground(themes.get(theme).get(6));
        getRawPanel().setForeground(themes.get(theme).get(11));
        getRawPanel().setFont(new Font("Santa Fe Let", Font.PLAIN, 15));
        getRawPanel().setText(rawResponseStr);
        getRawPanel().setEnabled(false);


        TextLineNumber tln = new TextLineNumber(getRawPanel());
        tln.setBorderGap(0);
        tln.setDigitAlignment(TextLineNumber.CENTER);


        JScrollPane rawScroll = new JScrollPane(getRawPanel());
        rawScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        rawScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        rawScroll.setRowHeaderView(tln);
        rawScroll.setBorder(null);


        responseData.add(rawScroll, "Raw");

        ((CardLayout)responseData.getLayout()).show(responseData, "Raw");


        visualPanel = new JPanel();
        visualPanel.setBackground(themes.get(theme).get(6));
        visualPanel.setLayout(new BorderLayout());
//        visualPanel.setBackground(new Color(41, 42, 37));
        BufferedImage responseImage = null;
        JLabel pic ;
        try {

            File image = new File(imagePathStr);
            if(image.isFile() && image.canRead()) {

                responseImage = ImageIO.read(image);
                pic = new JLabel(new ImageIcon(responseImage));
                getVisualPanel().add(pic, BorderLayout.CENTER);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        JScrollPane visualScroll = new JScrollPane(getVisualPanel());
        visualScroll.setBorder(null);

        responseData.add(visualScroll, "Visual");


        JPopupMenu responseTypePopup = new JPopupMenu();
        responseTypePopup.setPreferredSize(new Dimension(200, 100));

        JMenuItem rawForm = new JMenuItem("Raw");
        JMenuItem visualForm = new JMenuItem("Visual");

        responseTypePopup.add(rawForm);
        responseTypePopup.add(visualForm);

        JPanel responseCenterPanel = new JPanel(new CardLayout());
        responseCenterPanel.setBackground(themes.get(theme).get(6));
        responseCenterPanel.add(responseData, "response data");
        responseCenterPanel.add(headerPanel, "header");

        responsePanel.add(responseCenterPanel, BorderLayout.CENTER);

        CardLayout cardLayout = (CardLayout) responseCenterPanel.getLayout();



        JPanel headerTab = new JPanel(new GridLayout(1, 2));
        headerTab.setBackground(themes.get(theme).get(6));
        headerTab.setBorder(BorderFactory.createLineBorder(themes.get(theme).get(1)));

        List<JButton> headers = new ArrayList<>();

        for(int i=0 ; i<2; i++) {

            headers.add(new JButton());
            if(i == 0) {

                headers.get(i).setBackground(themes.get(theme).get(1));
                headers.get(i).setForeground(themes.get(theme).get(11));

            } else {

                headers.get(i).setBackground(themes.get(theme).get(6));
                headers.get(i).setForeground(themes.get(theme).get(7));

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

                    } else if(e.getButton() == MouseEvent.BUTTON3) {

                        if(index == 0) {

                            Component component = (Component) e.getSource();
                            responseTypePopup.show(headers.get(0), component.getX(), component.getY() + component.getHeight());

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

        rawForm.addActionListener(e -> {
            ((CardLayout)responseData.getLayout()).show(responseData, "Raw");
            headers.get(0).setText("Raw         " + openMenu);
        });
        visualForm.addActionListener(e -> {
            ((CardLayout)responseData.getLayout()).show(responseData, "Visual");
            headers.get(0).setText("Visual      " + openMenu);
        });

        headers.get(0).setText("Raw         " + openMenu);
        headers.get(1).setText("Header");

        for (JButton button : headers) {
            headerTab.add(button);
        }

        responsePanel.add(headerTab, BorderLayout.NORTH);



        headerCoverPanel = new JPanel();
        headerCoverPanel.setBackground(themes.get(theme).get(6));
        headerCoverPanel.setLayout(new GridBagLayout());

        setConstraints(new GridBagConstraints());

        headerPanelScroll = new JScrollPane(headerCoverPanel);
        headerPanelScroll.setBackground(themes.get(theme).get(6));
        headerPanelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        headerPanelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        headerPanelScroll.setBorder(null);

        headerPanel.add(headerPanelScroll, BorderLayout.CENTER);

        initialHeaderPanel = new JPanel();
        initialHeaderPanel.setBackground(themes.get(theme).get(6));
        initialHeaderPanel.setLayout(new BoxLayout(initialHeaderPanel, BoxLayout.X_AXIS));
        initialHeaderPanel.setPreferredSize(new Dimension(500, 50));

        List<JLabel> initialLabels = new ArrayList<>();

        for(int i=0 ; i<2 ; i++) {

            initialLabels.add(new JLabel());
            initialLabels.get(i).setForeground(themes.get(theme).get(11));
            initialLabels.get(i).setFont(new Font("Times New Roman", Font.PLAIN, 14));
            initialLabels.get(i).setPreferredSize(new Dimension(100, 50));


        }

        initialLabels.get(0).setText("NAME");
        initialLabels.get(1).setText("VALUE");

        initialHeaderPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        initialHeaderPanel.add(initialLabels.get(0));
        initialHeaderPanel.add(Box.createRigidArea(new Dimension(200, 0)));
        initialHeaderPanel.add(initialLabels.get(1));
        initialHeaderPanel.add(Box.createRigidArea(new Dimension(180, 0)));


        setHeaderField(new ArrayList<>());
        for (Map.Entry<String, String> entry : getHeaderMap().entrySet()) {

            responseHeaderField(headerField, entry.getKey(), entry.getValue());

        }
//        headerCoverPanel.add(Box.createRigidArea(new Dimension(0, 40)));

//        headerCoverPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        headerArrangement();


        JButton copyButton = new JButton("Copy to Clipboard");
        copyButton.setBackground(themes.get(theme).get(6));
        copyButton.setForeground(themes.get(theme).get(11));
        copyButton.setFont(new Font("Santa Fe Let", Font.PLAIN, 20));
        copyButton.setPreferredSize(new Dimension(200, 40));
        copyButton.setBorder(BorderFactory.createLineBorder(themes.get(theme).get(1)));
        copyButton.setContentAreaFilled(false);
        copyButton.setOpaque(true);
        copyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(e.getButton() == MouseEvent.BUTTON1) {

                    String headerContents = "";

                    for (JPanel panel : headerField) {

                        int counter = 0;

                        for (Component component : panel.getComponents()) {

                            if(component instanceof JTextArea && counter == 0) {

                                headerContents += ((JTextArea) component).getText() + ": ";
                                counter++;

                            }else if(component instanceof JTextArea && counter == 1) {

                                headerContents += ((JTextArea) component).getText() + "\n";
                                counter++;

                            }

                        }

                    }

                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(new StringSelection(headerContents), null);

                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                copyButton.setBackground(themes.get(theme).get(1));

            }

            @Override
            public void mouseExited(MouseEvent e) {

                copyButton.setBackground(themes.get(theme).get(6));

            }
        });


        headerPanel.add(copyButton, BorderLayout.SOUTH);

        // end of center of response part



    }

    /**
     * this method create header field of this panel
     *
     * @param headerField list of response header field
     * @param name name of this header field
     * @param value value of this header field
     */
    public void responseHeaderField(List<JPanel> headerField, String name, String value) {

        JPanel headerFieldPanel = new JPanel();
        headerFieldPanel.setBackground(themes.get(theme).get(6));
        headerFieldPanel.setLayout(new BoxLayout(headerFieldPanel, BoxLayout.X_AXIS));

        List<JTextArea> labels = new ArrayList<>();

        for(int i=0 ; i<2 ; i++) {

            labels.add(new JTextArea());
            labels.get(i).setLineWrap(true);
            labels.get(i).setForeground(themes.get(theme).get(11));
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
        headerFieldPanel.add(Box.createRigidArea(new Dimension(75, 0)));
        headerFieldPanel.add(labels.get(1));
        headerFieldPanel.add(Box.createRigidArea(new Dimension(10, 0)));


        headerField.add(headerFieldPanel);

        this.revalidate();
        this.repaint();
        mainFrame.revalidate();
        mainFrame.repaint();
        System.out.println(name + " : " + value);

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
     * getter of status code of request
     *
     * @return status code
     */
    public String getStatusCodeStr() {
        return statusCodeStr;
    }

    /**
     * setter of status code of request
     *
     * @param statusCodeStr status code
     */
    public void setStatusCodeStr(String statusCodeStr) {
        this.statusCodeStr = statusCodeStr;
    }

    /**
     * getter of request delay time
     *
     * @return delay time
     */
    public String getDelayTimeStr() {
        return delayTimeStr;
    }

    /**
     * setter of request delay time
     *
     * @param delayTimeStr delay time
     */
    public void setDelayTimeStr(String delayTimeStr) {
        this.delayTimeStr = delayTimeStr;
    }

    /**
     * getter of response size
     *
     * @return response size
     */
    public String getResponseSizeStr() {
        return responseSizeStr;
    }

    /**
     * setter of response size
     *
     * @param responseSizeStr response size
     */
    public void setResponseSizeStr(String responseSizeStr) {
        this.responseSizeStr = responseSizeStr;
    }

    /**
     * getter of raw form of response body
     *
     * @return response body
     */
    public String getRawResponseStr() {
        return rawResponseStr;
    }

    /**
     * setter of raw form of response body
     *
     * @param rawResponseStr response body
     */
    public void setRawResponseStr(String rawResponseStr) {
        this.rawResponseStr = rawResponseStr;
    }

    /**
     * getter of response image path
     *
     * @return response image path
     */
    public String getImagePathStr() {
        return imagePathStr;
    }

    /**
     * setter of response image path
     *
     * @param imagePathStr response image path
     */
    public void setImagePathStr(String imagePathStr) {
        this.imagePathStr = imagePathStr;
    }

    /**
     * getter of header map of key-value form of
     * response header
     *
     * @return map of response header
     */
    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    /**
     * setter of header map of key-value form of
     * response header
     * @param headerMap map of response header
     */
    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public JButton getRequestStatus() {
        return requestStatus;
    }

    public JButton getDelayTime() {
        return delayTime;
    }

    public JButton getFileSize() {
        return fileSize;
    }

    public JPanel getVisualPanel() {
        return visualPanel;
    }

    public JEditorPane getRawPanel() {
        return rawPanel;
    }

    public List<JPanel> getHeaderField() {
        return headerField;
    }

    public void headerArrangement() {

        headerCoverPanel = new JPanel();
        headerCoverPanel.setBackground(themes.get(theme).get(6));
        headerCoverPanel.setLayout(new GridBagLayout());

        setConstraints(new GridBagConstraints());

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(5, 0, 0, 0);

        headerCoverPanel.add(initialHeaderPanel, constraints);

        headerPanelScroll.setViewportView(headerCoverPanel);

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

                panel.setBackground(themes.get(theme).get(6));

            } else {
                panel.setBackground(themes.get(theme).get(9));
            }
            number++;

        }

    }

    public void setHeaderField(List<JPanel> headerField) {
        this.headerField = headerField;
    }

    public void setConstraints(GridBagConstraints constraints) {
        this.constraints = constraints;
    }
}
