import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;
import java.net.HttpURLConnection;

public class RequestCellRendered implements TreeCellRenderer {


    private JPanel mainRender;
    private JPanel rendered;
    private JLabel httpMethod;
    private JLabel requestName;
    private DefaultTreeCellRenderer defaultRenderer = new DefaultTreeCellRenderer();
    private Color backgroundSelectionColor;
    private Color backgroundNonSelectionColor;


    public RequestCellRendered(String requestName) {

        mainRender = new JPanel();
        mainRender.setBackground(new Color(46, 47, 43));
        mainRender.setPreferredSize(new Dimension(250 , 40));
        mainRender.setLayout(new BoxLayout(mainRender, BoxLayout.Y_AXIS));
        mainRender.add(Box.createRigidArea(new Dimension(0, 10)));

        rendered = new JPanel();
        rendered.setBackground(new Color(46, 47, 43));
        rendered.setPreferredSize(new Dimension(250, 40));
        rendered.setLayout(new BoxLayout(rendered, BoxLayout.X_AXIS));

        httpMethod = new JLabel(" ");
        httpMethod.setForeground(new Color(71, 72, 69));
        httpMethod.setFont(new Font("Santa Fe Let", Font.PLAIN, 15));
        rendered.add(Box.createRigidArea(new Dimension(10, 0)));
        rendered.add(httpMethod);

        this.requestName = new JLabel(requestName);
        this.requestName.setForeground(new Color(71, 72, 69));
        this.requestName.setFont(new Font("Santa Fe Let", Font.PLAIN, 15));
        rendered.add(Box.createRigidArea(new Dimension(10, 0)));
        rendered.add(this.requestName);

        rendered.setBorder(null);

        backgroundSelectionColor = new Color(71, 72, 69);
        backgroundNonSelectionColor = new Color(46, 47, 43);




    }


    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        Component returnValue = null;
        if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
            Object userObject = ((DefaultMutableTreeNode) value)
                    .getUserObject();
            if (userObject instanceof HttpURLConnection) {
                HttpURLConnection h = (HttpURLConnection) userObject;
                httpMethod.setText(h.getRequestMethod());
                if (selected) {
                    rendered.setBackground(backgroundSelectionColor);
                    for (Component component : rendered.getComponents()) {
                        component.setForeground(Color.white);
                    }
                } else {
                    rendered.setBackground(backgroundNonSelectionColor);
                    for (Component component : rendered.getComponents()) {
                        component.setForeground(new Color(81, 82, 79));
                    }
                }
                rendered.setEnabled(tree.isEnabled());
                returnValue = rendered;
            }
        }
        if (returnValue == null) {
            returnValue = defaultRenderer.getTreeCellRendererComponent(tree,
                    value, selected, expanded, leaf, row, hasFocus);
        }
        return returnValue;

    }
}
