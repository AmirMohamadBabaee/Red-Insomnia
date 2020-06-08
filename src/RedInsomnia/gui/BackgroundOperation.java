package RedInsomnia.gui;

import RedInsomnia.sync.RequestSetter;

import javax.swing.*;

/**
 * Background Operation
 *
 * This class extend SwingWorker class to do operation
 * in background.
 */
public class BackgroundOperation extends SwingWorker<Void, Void> {

    private RequestSetter requestSetter;

    /**
     * Constructor of BackgroundOperation
     *
     * @param requestSetter request setter object
     */
    public BackgroundOperation(RequestSetter requestSetter) {

        this.requestSetter = requestSetter;

    }

    @Override
    protected Void doInBackground() throws Exception {

        requestSetter.getResponseSetter().getRightPanel().getRequestStatus().setText("Loading...");
        requestSetter.getResponseSetter().getRightPanel().getDelayTime().setText("Loading...");
        requestSetter.getResponseSetter().getRightPanel().getFileSize().setText("Loading...");
        requestSetter.callStartConnection();
        return null;

    }

    @Override
    protected void done() {

        requestSetter.getResponseSetter().updateRightPanel();

    }
}
