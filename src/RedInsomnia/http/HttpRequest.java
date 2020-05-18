package RedInsomnia.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

    private URL url;
    private String method;
    private HttpURLConnection connection;
    private Map<String, String> httpHeader;


    public HttpRequest(String url, String method) {

        try {

            setUrl(new URL(url));
            setMethod(method);
            setHttpHeader(new HashMap<>());

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
    public void setUrl(URL url) {
        this.url = url;
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
        this.method = method;

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
     * setter of connection of this class
     *
     * @param httpHeader expected connection
     */
    public void setHttpHeader(Map<String, String> httpHeader) {
        this.httpHeader = httpHeader;
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
