package RedInsomnia.gui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * MessageBean
 *
 * This class implement listener of property change listener.
 * This class copied from www.tutorialspoint.com
 *
 * @see <a href="https://www.tutorialspoint.com/how-to-implement-propertychangelistener-using-lambda-expression-in-java">tutorialspoint.com</a>
 */
public class MessageBean {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private String value;

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String newValue) {
        String oldValue = value;
        value = newValue;
        support.firePropertyChange("value", oldValue, newValue);
    }
}