package RedInsomnia.sync;

import RedInsomnia.gui.RightPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResponseSetter {

    private String responseBody;
    private String imagePath;
    private String statusMessage;
    private int statusCode;
    private long responseSize;
    private long delayTime;
    private RightPanel rightPanel;
    private Map<String, String> headerMap;

    /**
     * getter of response body
     *
     * @return response body of http request
     */
    public String getResponseBody() {
        return responseBody;
    }

    /**
     * setter of response body
     *
     * @param responseBody response body of http request
     */
    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    /**
     * getter of image path
     *
     * @return image path
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * setter of image path
     *
     * @param imagePath image path of response
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * getter of status code
     *
     * @return status code of request
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * setter of status code
     *
     * @param statusCode status code of request
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * getter of delay time
     *
     * @return delay time
     */
    public long getDelayTime() {
        return delayTime;
    }

    /**
     * setter of delay time
     *
     * @param delayTime delay time of request
     */
    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime;
    }

    /**
     * getter of response size
     *
     * @return response size of request
     */
    public long getResponseSize() {
        return responseSize;
    }

    /**
     * setter of response size
     *
     * @param responseSize response size of request
     */
    public void setResponseSize(long responseSize) {
        this.responseSize = responseSize;
    }

    /**
     * getter of status message
     *
     * @return status message
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * setter of status message
     *
     * @param statusMessage status message of request
     */
    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    /**
     * getter of right panel object
     *
     * @return right panel object
     */
    public RightPanel getRightPanel() {
        return rightPanel;
    }

    /**
     * getter of header map
     *
     * @return map of headers
     */
    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    /**
     * setter of header map
     *
     * @param headerMap map of headers
     */
    public void setHeaderMap(Map<String, List<String>> headerMap) {

        Map<String, String> map = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> entry : headerMap.entrySet()) {

            map.put(entry.getKey(), entry.getValue().toString().substring(1, entry.getValue().toString().length()-1));

        }

        this.headerMap = map;

    }

    /**
     * setter of right panel object
     *
     * @param rightPanel right panel object
     */
    public void setRightPanel(RightPanel rightPanel) {
        this.rightPanel = rightPanel;
    }

    /**
     * setter of malformed url exception
     *
     * @param malformedUrlError exception message
     */
    public void launchMalformedUrlError(String malformedUrlError) {

        JOptionPane.showMessageDialog(rightPanel, "Your Entered URL : " + malformedUrlError, "Malformed URL Error", JOptionPane.ERROR_MESSAGE);

    }

    /**
     * setter of i/o exception
     *
     * @param IOError exception message
     */
    public void launchIOError(String IOError) {

        JOptionPane.showMessageDialog(rightPanel, IOError, "I/O Error", JOptionPane.ERROR_MESSAGE);

    }

    public void launchUnknownHostError(String unknownHostError) {

        JOptionPane.showMessageDialog(rightPanel, "Your Entered URL : " + unknownHostError, "Unknown Host Error", JOptionPane.ERROR_MESSAGE);

    }


    public void updateRightPanel() {

        rightPanel.getRequestStatus().setText(getStatusCode() + " " + getStatusMessage());
        rightPanel.getRequestStatus().setToolTipText(getStatusCode() + " " + getStatusMessage());
        if(getStatusCode() > 499) {
            rightPanel.getRequestStatus().setBackground(new Color(249, 31, 60));
        } else if(getStatusCode() > 399) {
            rightPanel.getRequestStatus().setBackground(new Color(252, 140, 29));
        } else if(getStatusCode() > 299) {
            rightPanel.getRequestStatus().setBackground(new Color(92, 118, 214));
        } else if(getStatusCode() > 199){
            rightPanel.getRequestStatus().setBackground(new Color(204, 204, 0));
        } else {
            rightPanel.getRequestStatus().setBackground(Color.RED);
        }

        rightPanel.getDelayTime().setText(getDelayTime() + " ms");
        rightPanel.getDelayTime().setToolTipText(getDelayTime() + " ms");

        rightPanel.getFileSize().setText(convertBytetoMegaByte(getResponseSize()));
        rightPanel.getFileSize().setToolTipText(convertBytetoMegaByte(getResponseSize()));

        rightPanel.getRawPanel().setText(getResponseBody());

        // response headers
        rightPanel.setHeaderField(new ArrayList<>());
//        rightPanel.setConstraints(new GridBagConstraints());
        for (Map.Entry<String, String> entry : getHeaderMap().entrySet()) {

            if(entry.getKey() != null) {

                rightPanel.responseHeaderField(rightPanel.getHeaderField(), entry.getKey(), entry.getValue());

            }

        }
        rightPanel.headerArrangement();

        rightPanel.getVisualPanel().removeAll();

        // visual setting
        BufferedImage responseImage = null;
        JLabel pic ;
        try {

            if(getImagePath() != null) {

                File image = new File(getImagePath());
                if(image.isFile() && image.canRead()) {

                    responseImage = ImageIO.read(image);
                    pic = new JLabel(new ImageIcon(responseImage));
                    rightPanel.getVisualPanel().add(pic, BorderLayout.CENTER);

                }

            } else if(responseBody.trim().toLowerCase().startsWith("<html>")
                && responseBody.trim().toLowerCase().endsWith("</html>")) {

                pic = new JLabel(responseBody);
                rightPanel.getVisualPanel().add(pic, BorderLayout.CENTER);

            } else {

                pic = new JLabel();
                rightPanel.getVisualPanel().add(pic, BorderLayout.CENTER);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("update right panel");

        for (Component component : rightPanel.getComponents()) {
            component.revalidate();
            component.repaint();
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
