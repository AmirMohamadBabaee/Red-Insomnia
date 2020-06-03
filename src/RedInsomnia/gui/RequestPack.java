package RedInsomnia.gui;

/**
 * RedInsomnia.gui.RequestPack
 *
 * This class define a pack of request panel and
 * its center and right panel
 *
 * @author Amir01
 * @version
 *
 * @see MainFrame
 */
public class RequestPack {

    private RequestPanel requestPanel;
    private CenterPanel centerPanel;
    private RightPanel rightPanel;


    /**
     * Constructor of Request pack class
     *
     * @param requestPanel requestPanel object
     * @param mainFrame main Frame of RedInsomnia
     */
    public RequestPack(RequestPanel requestPanel, MainFrame mainFrame) {

        this.requestPanel = requestPanel;
        this.centerPanel = new CenterPanel(mainFrame);
        this.rightPanel = new RightPanel(mainFrame);
        this.centerPanel.getResponseSetter().setRightPanel(this.rightPanel);

    }

    /**
     * Constructor of RequestPack
     *
     * @param requestPanel request panel object
     * @param centerPanel expected center panel
     * @param rightPanel expected right panel
     */
    public RequestPack(RequestPanel requestPanel, CenterPanel centerPanel, RightPanel rightPanel) {

        this.requestPanel = requestPanel;
        this.centerPanel = centerPanel;
        this.rightPanel = rightPanel;

    }

    /**
     * getter of requestPanel
     *
     * @return request panel of this pack object
     */
    public RequestPanel getRequestPanel() {
        return requestPanel;
    }

    /**
     * getter of centerPanel
     *
     * @return center panel of this request panel
     */
    public CenterPanel getCenterPanel() {
        return centerPanel;
    }

    /**
     * getter of rightPanel
     *
     * @return right panel of this request panel
     */
    public RightPanel getRightPanel() {
        return rightPanel;
    }
}
