package RedInsomnia.http;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class HttpRequest {

    private boolean requestEnable;
    private URL url;
    private String method = "GET";
    private HttpURLConnection connection;
    private Map<String, String> httpHeader;
    private Map<String, String> httpData;
    private String jsonStr;
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

        try {

            connection = (HttpURLConnection) this.url.openConnection();

            connection.setRequestMethod(method);

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
            connection.setConnectTimeout(10000);
            // Maximum time for reading
            connection.setReadTimeout(10000);

            // response code of this request
            int status = connection.getResponseCode();

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

            System.out.println();

            System.out.println(stringBuffer.toString());
            System.out.println();
            System.out.println("response code : " + connection.getResponseCode());
            for (Map.Entry<String, List<String>> entry : connection.getHeaderFields().entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
            System.out.println("request method : " + connection.getRequestMethod());
            System.out.println("message : " + connection.getResponseMessage());
            System.out.println(connection.usingProxy());

            connection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
