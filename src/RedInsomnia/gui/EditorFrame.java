package RedInsomnia.gui;

import RedInsomnia.http.HttpRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * RedInsomnia.gui.EditorFrame
 *
 * this frame develop to show when editButton in RedInsomnia.gui.MainFrame pressed
 * and launch a new frame that have a JEditorPane for adding multi-
 * line value in request body.
 *
 * @author Amir01
 * @version
 *
 * @see JFrame
 * @see MainFrame
 */

public class EditorFrame extends JFrame {

    private JEditorPane editorPane;
    private JButton doneButton;
    private String context;
    private CenterPanel centerPanel;
    private HttpRequest httpRequest;
    private MessageBean bean;
    private JButton editorButton;
    static Map<JButton, String> contextsList = new LinkedHashMap<>();

    /**
     * Constructor of RedInsomnia.gui.EditorFrame Class in RedInsomnia
     *
     * @param title title of this Frame
     * @param theme theme of this Frame
     * @param mainFrame main frame of this program
     */
    public EditorFrame(String title, int theme, JFrame mainFrame, JButton editorButton) {

        super();

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        SwingUtilities.updateComponentTreeUI(this);

        setTitle(title);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(mainFrame);
        Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
        setIconImage(icon);
        setLayout(null);
        setVisible(true);

        if(contextsList.containsKey(editorButton)) {

            context = contextsList.get(editorButton);

        } else {

            context = "";

        }

        this.editorButton = editorButton;


        editorPane = new JEditorPane();
        editorPane.setText(context);
        editorPane.setFont(new Font("Santa Fe Let", Font.PLAIN, 18));
        editorPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if(e.getKeyChar() == '\n' && e.getModifiers() == KeyEvent.CTRL_MASK) {

                    doneAction();

                }

            }
        });

        TextLineNumber tln = new TextLineNumber(editorPane);
        tln.setBorderGap(0);
        tln.setDigitAlignment(TextLineNumber.CENTER);

        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setRowHeaderView(tln);
        scrollPane.setBounds(10, 10, 780, 400);
        add(scrollPane);

        doneButton = new JButton("Done");
        Color currentColor = doneButton.getBackground();
        doneButton.setForeground(Color.black);
        doneButton.setPreferredSize(new Dimension(75, 50));
        doneButton.setFont(new Font("Santa Fe Let", Font.PLAIN, 25));
        doneButton.setBorder(null);
        doneButton.setContentAreaFilled(false);
        doneButton.setOpaque(true);
        doneButton.setBounds(700, 415, 90, 45);

        this.revalidate();
        this.repaint();

        doneButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(e.getButton() == MouseEvent.BUTTON1) {

                    doneAction();

                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                doneButton.setBackground(new Color(200, 200, 200));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                doneButton.setBackground(currentColor);
            }
        });

        add(doneButton);

        this.centerPanel = centerPanel;
        this.bean = bean;

    }

    /**
     * getter of context field
     *
     * @return context of this frame
     */
    public String getContext() {
        return context;
    }

    /**
     * setter of context field
     *
     * @param context context of this frame
     */
    public void setContext(String context) {
        this.context = context;
    }

    /**
     * getter of http request
     *
     * @return http request
     */
    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    /**
     * setter of http request in this class
     *
     * @param httpRequest http request of this request
     */
    public void setHttpRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    /**
     * Implementation of MouseClicked Action in Done button
     */
    private void doneAction() {

        context = editorPane.getText();
        contextsList.putIfAbsent(editorButton, context);
        setVisible(false);
        dispose();

    }
}
