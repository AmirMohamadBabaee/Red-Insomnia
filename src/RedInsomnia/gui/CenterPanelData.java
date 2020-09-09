package RedInsomnia.gui;

import java.io.*;
import java.util.Map;

/**
 * CenterPanelData
 *
 * This class has data of Center panel which use in initialize
 * center panel of saved requests.
 *
 * @author Amir01
 * @version
 */
public class CenterPanelData implements Serializable {

    private RequestPanel requestPanel;
    private String url;
    private String method;
    private Map<String, String> headers;
    private Map<String, String> formData;
    private String json;
    private String filePath;
    private String fileName;


    public CenterPanelData() {
        fileName = System.currentTimeMillis() + "";
    }


    /**
     * getter of Request panel object
     *
     * @return request panel object
     */
    public RequestPanel getRequestPanel() {
        return requestPanel;
    }

    /**
     * setter of Request panel object
     *
     * @param requestPanel request panel object
     */
    public void setRequestPanel(RequestPanel requestPanel) {
        this.requestPanel = requestPanel;
    }

    /**
     * getter of url
     *
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * setter of url
     *
     * @param url url string
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * getter of method
     *
     * @return method
     */
    public String getMethod() {
        return method;
    }

    /**
     * setter of method
     *
     * @param method method string
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * getter of headers
     *
     * @return map of headers
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * setter of headers
     *
     * @param headers headers map
     */
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    /**
     * getter of form data
     *
     * @return map of form data
     */
    public Map<String, String> getFormData() {
        return formData;
    }

    /**
     * setter of form data
     *
     * @param formData form data's map
     */
    public void setFormData(Map<String, String> formData) {
        this.formData = formData;
    }

    /**
     * getter of json string
     *
     * @return json string
     */
    public String getJson() {
        return json;
    }

    /**
     * setter of json string
     *
     * @param json json string
     */
    public void setJson(String json) {
        this.json = json;
    }

    /**
     * getter of file path
     *
     * @return file path
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * setter of file path
     *
     * @param filePath file path
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void writeCenterPanelData() {

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./data/center_panel_data/" + fileName + ".cpdata"))) {

            oos.writeObject(this.requestPanel);
            oos.writeObject(this);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
