import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * EditorFrame
 *
 * this frame develop to show when editButton in MainFrame pressed
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

    public static final int LIGHT_THEME = 0;
    public static final int DARK_THEME = 1;
    private JEditorPane editorPane;
    private JButton doneButton;
    private String context;

    public EditorFrame(String title, int theme, JFrame mainFrame) {

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

        editorPane = new JEditorPane();
        editorPane.setFont(new Font("Santa Fe Let", Font.PLAIN, 18));
        editorPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if(e.getKeyChar() == '\n' && e.getModifiers() == KeyEvent.CTRL_MASK) {

                    doneAction();

                }

            }
        });

        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(10, 10, 780, 400);
        add(scrollPane);

        doneButton = new JButton("Done");
        Color currentColor = doneButton.getBackground();
        doneButton.setPreferredSize(new Dimension(75, 50));
        doneButton.setFont(new Font("Santa Fe Let", Font.PLAIN, 25));
        doneButton.setBorder(null);
        doneButton.setContentAreaFilled(false);
        doneButton.setOpaque(true);
        doneButton.setBounds(700, 415, 90, 45);

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



    }


    private void doneAction() {

        context = editorPane.getText();
        System.out.println(context);
        setVisible(false);
        dispose();
        System.out.println("hello");

    }

}
