package RedInsomnia.sync;

import RedInsomnia.http.CommandFunction;

import java.util.Map;

/**
 * RequestSetter
 *
 * Goal of this class is synchronize RedInsomnia input for
 * use in jurl functioning
 *
 * @author Amir01
 * @version
 */
public class RequestSetter {

    private String url = "";
    private String method = "";
    private String header = "";
    private String formData = "";
    private String jsonData = "";
    private String filePath = "";
    private boolean followRedirect;
    private CommandFunction commandFunction;
    private ResponseSetter responseSetter;

    /**
     * Default Constructor of RequestSetter
     */
    public RequestSetter() {
        commandFunction = new CommandFunction();
    }

    /**
     * This Constructor, set url and method of http request
     *
     * @param url expected url
     * @param method expected method
     */
    public RequestSetter(String url, String method) {

        this();
        setUrl(url);
        setMethod(method);

    }

    /**
     * This Constructor, set url, method and headers of http request
     *
     * @param url expected url
     * @param method expected method
     * @param header expected header
     */
    public RequestSetter(String url, String method, String header) {

        this(url, method);
        setHeader(header);

    }


    /**
     * setter of url
     *
     * @param url expected url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * setter of method
     *
     * @param method expected method
     */
    public void setMethod(String method) {
        if(!method.isEmpty()) {
            this.method = method.substring(0, method.length()-3).trim();
            System.out.println("Request setter method : " + this.method);
        }
    }

    /**
     * setter of headers
     *
     * @param header string of headers
     */
    public void setHeader(String header) {
        if(!header.isEmpty()) {
            this.header = header;
        }
    }

    /**
     * setter of headers
     *
     * @param header map of headers
     */
    public void setHeader(Map<String, String> header) {

        if(!header.isEmpty()) {
            this.header = convertMapToString(header, ":", ";");
        }

    }

    /**
     * setter of form data
     *
     * @param formData string of form data
     */
    public void setFormData(String formData) {
        if(!formData.isEmpty()) {
            this.formData = formData;
        }
    }

    /**
     * setter of form data
     *
     * @param formData map of form data
     */
    public void setFormData(Map<String, String> formData) {

        if(!formData.isEmpty()) {
            this.formData = convertMapToString(formData, "=", "&");
        }

    }

    /**
     * setter of json data
     *
     * @param jsonData string of json data
     */
    public void setJsonData(String jsonData) {
        if(!jsonData.isEmpty()) {
            this.jsonData = jsonData;
        }
    }

    /**
     * setter of file path
     *
     * @param filePath string of file path
     */
    public void setFilePath(String filePath) {
        if(!filePath.isEmpty()) {
            int sizeIndex = filePath.indexOf("(");
            if(sizeIndex >= 0) {

                this.filePath = filePath.substring(0, sizeIndex);

            }
        }
    }

    /**
     * setter of follow redirect
     */
    public void setFollowRedirect() {
        this.followRedirect = true;
    }

    /**
     * getter of response setter object
     *
     * @return response setter object
     */
    public ResponseSetter getResponseSetter() {
        return responseSetter;
    }

    /**
     * setter of response setter object
     *
     * @param responseSetter response setter object
     */
    public void setResponseSetter(ResponseSetter responseSetter) {
        this.responseSetter = responseSetter;
    }


    public void callStartConnection() {

        requestSetting();
        commandFunction.startConnection();

    }


    private void requestSetting() {

        commandFunction.setResponseSetter(getResponseSetter());

        if(!url.isEmpty()) {
            commandFunction.jurlOperation(url);
            commandFunction.showResponseHeaderOperation();
        }

        if(!method.isEmpty()) {
            commandFunction.methodOperation(method);
        }

        if(!header.isEmpty()) {
            commandFunction.headersOperation(header);
        }

        if(!formData.isEmpty()) {
            commandFunction.dataOperation(formData);
        }

        assert jsonData != null;
        if(!jsonData.isEmpty()) {
            commandFunction.jsonOperation(jsonData);
        }

        if(followRedirect) {
            commandFunction.followRedirectOperation();
        }

        if(!filePath.isEmpty()) {
            commandFunction.uploadOperation(filePath);
        }

    }

    /**
     * this method take a map as an argument and convert to
     * specific string related to delimiter and pairDelimiter
     * arguments. in this method each user determine which
     * delimiter can be used.
     *
     * @param map map of String key-value
     * @param delimiter first delimiter
     * @param pairDelimiter second delimiter
     * @return prepared string
     */
    private String convertMapToString(Map<String, String> map, String delimiter, String pairDelimiter) {

        StringBuilder result = new StringBuilder();

        if(!map.isEmpty()) {

            for (Map.Entry<String, String> entry : map.entrySet()) {

                result.append(entry.getKey() + delimiter + entry.getValue() + pairDelimiter);

            }

        }

        return result.toString().substring(0, result.length() - 1);
    }
}
