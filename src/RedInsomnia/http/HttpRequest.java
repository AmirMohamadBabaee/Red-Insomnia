package RedInsomnia.http;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * HttpRequest
 *
 * This class receive necessary data and make a connection
 * to specified URL. also this class to establish this
 * connection use HttpURLConnection. since this class
 * can't support PATCH method, I have to use other classes
 *
 * @author Amir01
 * @version
 *
 * @see java.net.HttpURLConnection;
 */
public class HttpRequest implements Serializable{

    private static final long serialVersionUID = 1733656072692649962L;
    private boolean requestEnable;
    private URL url;
    private String method = "GET";
    private HttpURLConnection connection;
    private Map<String, String> httpHeader;
    private Map<String, String> httpData;
    private String jsonStr;
    private boolean followRedirect;
    private boolean showResponseHeader;
    private String[] validMethod = new String[]{
            "GET", "POST", "PUT", "PATCH", "DELETE"
    };
    private String responseBody;


    public HttpRequest(String url, String method) {

        try {

            requestEnable = setUrl(url);
            setMethod(method);
            setHttpHeader(new HashMap<>());
            setHttpData(new HashMap<>());
            setJsonStr("");

        }catch(MalformedURLException er) {
            er.printStackTrace();
        }


    }


    /**
     * getter of expected url
     *
     * @return expected url
     */
    public URL getUrl() {
        return url;
    }

    /**
     * setter of expected url
     *
     * @param url expected url
     */
    public boolean setUrl(String url) throws MalformedURLException {

        if(url.startsWith("http://") || url.startsWith("https://")) {

            this.url = new URL(url);
            return true;

        } else {

            System.out.println("jurl : Your entered url should start with \"https://\" or \"http://\" protocol name");

        }

        return false;

    }

    /**
     * getter of request method
     *
     * @return request method
     */
    public String getMethod() {
        return method;
    }

    /**
     * setter of request method
     *
     * @param method request method
     */
    public void setMethod(String method) {

        method = method.toUpperCase();

        for (String s : validMethod) {

            if(s.equals(method)) {
                this.method = s;
            }

        }

    }

    /**
     * getter of connection of this class
     *
     * @return expected connection
     */
    public HttpURLConnection getConnection() {
        return connection;
    }

    /**
     * getter of http request headers
     *
     * @return header's map
     */
    public Map<String, String> getHttpHeader() {
        return httpHeader;
    }

    /**
     * setter of http request headers
     *
     * @param httpHeader header's map
     */
    public void setHttpHeader(Map<String, String> httpHeader) {
        this.httpHeader = httpHeader;
    }

    /**
     * getter of http request data
     *
     * @return request data Map
     */
    public Map<String, String> getHttpData() {
        return httpData;
    }

    /**
     * setter of http request data
     *
     * @param httpData request data Map
     */
    public void setHttpData(Map<String, String> httpData) {
        this.httpData = httpData;
    }

    /**
     * getter of response body of this http request
     *
     * @return response body
     */
    public String getResponseBody() {
        return responseBody;
    }

    /**
     * this method return state of this http request to show
     * to other classes that is enable or is not.
     *
     * @return state of this http request
     */
    public boolean isRequestEnable() {
        return requestEnable;
    }

    /**
     * setter of requset enable field
     *
     * @param requestEnable necessary change to request enable field
     */
    public void setRequestEnable(boolean requestEnable) {
        this.requestEnable = requestEnable;
    }

    /**
     * getter of jsonStr field
     *
     * @return string of json content
     */
    public String getJsonStr() {
        return jsonStr;
    }

    /**
     * setter of jsonStr field
     *
     * @param jsonStr string of json content
     */
    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    /**
     * getter of follow redirect
     *
     * @return state of follow redirect
     */
    public boolean isFollowRedirect() {
        return followRedirect;
    }

    /**
     * setter of follow redirect
     *
     * @param followRedirect state of follow redirect
     */
    public void setFollowRedirect(boolean followRedirect) {
        this.followRedirect = followRedirect;
    }

    /**
     * getter of show response header field
     *
     * @return state of showing response header field
     */
    public boolean isShowResponseHeader() {
        return showResponseHeader;
    }

    /**
     * setter of show response header field
     *
     * @param showResponseHeader state of showing response header field
     */
    public void setShowResponseHeader(boolean showResponseHeader) {
        this.showResponseHeader = showResponseHeader;
    }

    /**
     * add a new http header to map of this http request
     *
     * @param key name of this header
     * @param value value of this header
     */
    public void addHttpHeader(String key, String value) {

        httpHeader.put(key, value);

    }


    /**
     * this method copid from www.baeldung.com
     *
     * @param params map of data
     * @return perpared string to be transfered
     * @throws UnsupportedEncodingException
     *
     * @see <a href="https://www.baeldung.com/java-http-request">Baeldung.com</>
     */
    private static String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException{

        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

    /**
     * this method establish a connection on expected URL
     */
    public void establishConnection() {

        allowMethods("PATCH");

        try {

            connection = (HttpURLConnection) this.url.openConnection();

            connection.setRequestMethod(method);

            if(isFollowRedirect()) {
                connection.setInstanceFollowRedirects(true);
            }

            for (Map.Entry<String, String> entry : httpHeader.entrySet()) {

                connection.setRequestProperty(entry.getKey(), entry.getValue());

            }

            for (Map.Entry<String, List<String>> entry : connection.getRequestProperties().entrySet()) {

                System.out.println(entry.getKey() + " : " + entry.getValue());

            }

            if(!method.equals("GET")) {

                connection.setDoOutput(true);

                try(DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {

                    if(!getHttpData().isEmpty()) {

                        out.writeBytes(getParamsString(getHttpData()));
                        out.flush();

                    } else if(!jsonStr.isEmpty()) {

                        out.writeBytes(jsonStr);
                        out.flush();

                    }

                }

            }

            // maximum time for connection
            connection.setConnectTimeout(5000);
            // Maximum time for reading
            connection.setReadTimeout(5000);

            // response code of this request
            int status = connection.getResponseCode();

            if(isFollowRedirect()
                && (status == HttpURLConnection.HTTP_MOVED_TEMP
                    || status == HttpURLConnection.HTTP_MOVED_PERM
                        || status == HttpURLConnection.HTTP_SEE_OTHER)) {

                String newUrl = connection.getHeaderField("Location");

                connection = (HttpURLConnection) new URL(newUrl).openConnection();

                for (Map.Entry<String, String> entry : httpHeader.entrySet()) {

                    connection.setRequestProperty(entry.getKey(), entry.getValue());

                }

                System.out.println("Redirect to This URL : " + newUrl);

            }

            // determine inputStream of this
            InputStream in = null;

            if(status > 299) {

                in = connection.getErrorStream();

            } else {

                in = connection.getInputStream();

            }

            BufferedReader buffer = new BufferedReader(new InputStreamReader(in));

            String line = null;
            StringBuffer stringBuffer = new StringBuffer();

            while ((line = buffer.readLine()) != null) {

                stringBuffer.append(line);
                stringBuffer.append("\n");

            }

            responseBody = stringBuffer.toString();

            System.out.println("Response Message : \n");
            System.out.println(stringBuffer.toString());
            System.out.println();

            if(isShowResponseHeader()) {

                for (Map.Entry<String, List<String>> entry : connection.getHeaderFields().entrySet()) {
                    System.out.println((entry.getKey() != null ? entry.getKey() + " : " : "") + entry.getValue());
                }
                System.out.println("request method : " + connection.getRequestMethod());
                System.out.println("using proxy : " + connection.usingProxy());
                System.out.println();

            }

            connection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * This method copied from stackoverflow
     * this method use reflection tools to add expected REST API
     * method to supported method array of HttpUrlConnection
     *
     * @param methods expected Http Methods
     */
    private void allowMethods(String... methods) {
        try {
            Field methodsField = HttpURLConnection.class.getDeclaredField("methods");

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

            methodsField.setAccessible(true);

            String[] oldMethods = (String[]) methodsField.get(null);
            Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
            methodsSet.addAll(Arrays.asList(methods));
            String[] newMethods = methodsSet.toArray(new String[0]);

            methodsField.set(null/*static field*/, newMethods);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String toString() {

        String headersStr = "";

        for (Map.Entry<String, String> entry : getHttpHeader().entrySet()) {

            headersStr += "(" + entry.getKey() + " : " + entry.getValue() + ") ";

        }

        return "URL: " + url.toString() + " | "
                + "Method: " + getMethod() + " | "
                + "Headers: " + headersStr;
    }
}
