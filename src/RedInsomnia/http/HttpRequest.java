package RedInsomnia.http;

import RedInsomnia.sync.ResponseSetter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * HttpRequest
 *
 * This class receive necessary data and make a connection
 * to specified URL. also this class to establish this
 * connection use HttpURLConnection. since this class
 * can't support PATCH method, I have to use Reflection
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
    private File uploadedFile;
    private byte[] imageBytes;
    private long delayTime;
    private ResponseSetter responseSetter;


    /**
     * Constructor of HttpRequest class
     *
     * @param url expected url
     * @param method expected method
     */
    public HttpRequest(String url, String method) {

        try {

            requestEnable = setUrl(url);
            setMethod(method);
            setHttpHeader(new HashMap<>());
            setHttpData(new HashMap<>());
            setJsonStr("");

        }catch(MalformedURLException er) {
            er.printStackTrace();
            setRequestEnable(false);
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

            this.url = new URL("http://" + url);
            return true;

        }

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
     * getter of uploaded file
     *
     * @return uploaded file
     */
    public File getUploadedFile() {
        return uploadedFile;
    }

    /**
     * setter of uploaded file
     *
     * @param uploadedFile uploaded file
     */
    public void setUploadedFile(File uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    /**
     * getter of image bytes list
     *
     * @return list of image bytes
     */
    public byte[] getImageBytes() {
        return imageBytes;
    }

    /**
     * getter of delay time of this http request
     *
     * @return delay time of http request in (ms) in long
     * format.
     */
    public long getDelayTime() {
        return delayTime;
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
     * @see <a href="https://www.baeldung.com/java-http-request">Baeldung.com</a>
     */
    private static String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException{

        StringBuilder result = new StringBuilder();

        // this is form url encoded form of request body
        /*for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }*/

        // this is multipart form of request body
        for (Map.Entry<String, String> entry : params.entrySet()) {

            result.append("--" + System.currentTimeMillis() + "-X-REDINSOMNIA-BOUNDARY\r\n");
            result.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n\r\n");
            result.append(entry.getValue() + "\r\n");

        }
        result.append("--" + System.currentTimeMillis() + "-X-REDINSOMNIA-BOUNDARY\r\n");


        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

    /**
     * this method establish a connection on expected URL
     */
    public synchronized void establishConnection() {

        allowMethods("PATCH");

        try {

            connection = (HttpURLConnection) this.url.openConnection();

            connection.setRequestMethod(method);

            if(!jsonStr.isEmpty() && httpData.isEmpty() && uploadedFile == null) {

                connection.setRequestProperty("Content-Type", "application/json; utf-8");
                connection.setRequestProperty("Accept", "application/json");

            } else if(jsonStr.isEmpty() && !httpData.isEmpty() && uploadedFile == null) {

                connection.setRequestProperty("Content-Type", "multipart/form-data; utf-8");

            } else if(jsonStr.isEmpty() && httpData.isEmpty() && uploadedFile != null) {

                connection.setRequestProperty("Content-Type", "application/octet-stream");

            }

            if(!httpHeader.isEmpty()) {

                for (Map.Entry<String, String> entry : httpHeader.entrySet()) {

                    connection.setRequestProperty(entry.getKey(), entry.getValue());

                }

            }

            System.out.println("Headers : ");
            if(!getHttpHeader().isEmpty()) {

                for (Map.Entry<String, List<String>> entry : connection.getRequestProperties().entrySet()) {

                    System.out.println(entry.getKey() + " : " + entry.getValue());
                    System.out.println();

                }

            } else {

                System.out.println("It's Empty!!!");
                System.out.println();

            }

            if(isFollowRedirect()) {
                connection.setInstanceFollowRedirects(true);
            }

            if(!method.equals("GET")) {

                connection.setDoOutput(true);

                if(uploadedFile != null && httpData.isEmpty() && jsonStr.isEmpty()) {

                    try(BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(connection.getOutputStream())) {

                        BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(uploadedFile));
                        int byteInt;
                        while ((byteInt = fileInputStream.read()) != -1) {

                            bufferedOutputStream.write(byteInt);
                            bufferedOutputStream.flush();

                        }
                        System.out.println("file uploaded!!!");

                    }

                } else if(uploadedFile != null){

                    System.out.println("jurl : You can't add form data file and binary file simultaneously!!!");
                    this.setRequestEnable(false);
                    return;

                }

            }

            if(!method.equals("GET") && !method.equals("DELETE")) { // these two method has not any body!!!

                try(DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {

                    if(!getHttpData().isEmpty() && jsonStr.isEmpty() && uploadedFile == null) {

                        out.writeBytes(getParamsString(getHttpData()));
                        out.flush();

                    } else if(!jsonStr.isEmpty() && getHttpData().isEmpty() && uploadedFile == null) {

                        out.writeBytes(jsonStr);
                        out.flush();

                    }

                }

            }

            Instant firstTime = Instant.now();

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

                status = connection.getResponseCode();

                System.out.println("Redirect to This URL : " + newUrl);

            }

            Instant secondTime = Instant.now();

            Duration interval = Duration.between(firstTime, secondTime);

            this.delayTime = interval.getNano()/1_000_000;

            System.out.println("Delay Time : " + delayTime + " ms");
            System.out.println();

            // determine inputStream of this
            InputStream in = null;

            if(status > 399) {

                in = connection.getErrorStream();

            } else {

                in = connection.getInputStream();

            }

            responseBody = "";

            boolean isSuccessful = new File("./cache/").mkdirs();

            if(connection.getContentType() != null && connection.getContentType().contains("image")) {


                DataInputStream input = new DataInputStream(new BufferedInputStream(in));

                /*
                 * if content-length header had set in server, we use that as
                 * file size else we initialize a default value. this default
                 * value is 1 000 000 bytes or 1 MB.
                 */
                if(connection.getContentLength() == -1) {

                    imageBytes = new byte[1_000_000];

                } else {

                    imageBytes = new byte[connection.getContentLength()];

                }

                input.read(imageBytes);

                input.close();

                String fileName = "./cache/I" + System.currentTimeMillis();

                if(connection.getContentType().contains("png")) {
                    fileName += ".png";
                } else if(connection.getContentType().contains("bmp")) {
                    fileName += ".bmp";
                } else if(connection.getContentType().contains("gif")) {
                    fileName += ".gif";
                } else if(connection.getContentType().contains("vnd.microsoft.icon")) {
                    fileName += ".ico";
                } else if(connection.getContentType().contains("jpeg")) {
                    fileName += ".jpg";
                } else if(connection.getContentType().contains("svg+xml")) {
                    fileName += ".svg";
                } else if(connection.getContentType().contains("tiff")) {
                    fileName += ".tif";
                } else if(connection.getContentType().contains("webp")) {
                    fileName += ".webp";
                }
                DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(fileName))));

                out.write(imageBytes);
                out.flush();

                if(responseSetter != null) {
                    responseSetter.setImagePath(fileName);
                    responseSetter.setResponseSize(new File(fileName).length());
                }

            } else {

                assert in != null;
                BufferedReader buffer = new BufferedReader(new InputStreamReader(in));

                String line = null;
                StringBuilder stringBuilder = new StringBuilder();

                while ((line = buffer.readLine()) != null) {

                    stringBuilder.append(line);
                    stringBuilder.append("\n");

                }

                responseBody = stringBuilder.toString();

            }


            System.out.println("Response Message : \n");

            if(!responseBody.isEmpty()) {



                if(JsonUtility.isJSONValid(responseBody)) {

                    String beautyString = JsonUtility.beautifyJson(responseBody);

                    System.out.println(beautyString);
                    System.out.println();

                    String fileName = "./cache/J" + System.currentTimeMillis() + ".json";

                    DataOutputStream output = new DataOutputStream(new FileOutputStream(new File(fileName)));

                    output.writeBytes(beautyString);
                    output.flush();

                    output.close();

                    if(responseSetter != null) {
                        responseSetter.setResponseBody(beautyString);
                        responseSetter.setResponseSize(new File(fileName).length());
                    }

                } else if(connection.getContentType() != null && connection.getHeaderField("Content-Type").contains("text/html")) {

                    Document doc = Jsoup.parseBodyFragment(responseBody);
                    doc.outputSettings().indentAmount(4);

                    String beautyHtml = doc.html();

                    System.out.println(beautyHtml);
                    System.out.println();

                    String fileName = "./cache/H" + System.currentTimeMillis() + ".html";

                    DataOutputStream output = new DataOutputStream(new FileOutputStream(new File(fileName)));

                    output.writeBytes(beautyHtml);
                    output.flush();

                    output.close();

                    if(responseSetter != null) {
                        responseSetter.setResponseBody(beautyHtml);
                        responseSetter.setResponseSize(new File(fileName).length());
                    }

                } else {

                    System.out.println(responseBody);
                    System.out.println();

                    String fileName = "./cache/T" + System.currentTimeMillis() + ".txt";

                    DataOutputStream output = new DataOutputStream(new FileOutputStream(new File(fileName)));

                    output.writeBytes(responseBody);
                    output.flush();

                    output.close();

                    if(responseSetter != null) {
                        responseSetter.setResponseBody(responseBody);
                        responseSetter.setResponseSize(new File(fileName).length());
                    }

                }

            } else {

                if(connection.getContentType() != null && connection.getContentType().contains("image")) {

                    try(BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream()))) {

                        String line = "";
                        StringBuilder stringBuilder = new StringBuilder();

                        while ((line = bf.readLine()) != null) {

                            stringBuilder.append(line);
                            stringBuilder.append("\n");

                        }

                        System.out.println(stringBuilder);
                        System.out.println();

                        if(responseSetter != null) {
                            responseSetter.setResponseBody(stringBuilder.toString());
                        }

                    }

                } else {

                    System.out.println("There is no Response!!!");
                    System.out.println();

                    if(responseSetter != null) {
                        responseSetter.setResponseBody("There is no Response!!!");
                    }

                }

            }

            if(responseSetter != null) {
                responseSetter.setHeaderMap(connection.getHeaderFields());
                responseSetter.setDelayTime(getDelayTime());
                responseSetter.setStatusCode(connection.getResponseCode());
                responseSetter.setStatusMessage(connection.getResponseMessage());
            }


            if(isShowResponseHeader()) {

                System.out.println(connection.getHeaderFields());
                for (Map.Entry<String, List<String>> entry : connection.getHeaderFields().entrySet()) {
                    System.out.println((entry.getKey() != null ? entry.getKey() + " : " : "") + entry.getValue());
                }
                System.out.println("request method : " + connection.getRequestMethod());
                System.out.println("using proxy : " + connection.usingProxy());
                System.out.println();

            }

            if(responseSetter != null) {
                responseSetter.updateRightPanel();
            }

            connection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            if(responseSetter != null) {
                responseSetter.launchMalformedUrlError(e.getMessage());
            }
        } catch(UnknownHostException e) {
            e.printStackTrace();
            if(responseSetter != null) {
                responseSetter.launchUnknownHostError(e.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
            if(responseSetter != null) {
                responseSetter.launchIOError(e.getMessage());
            }
        }

    }


    /**
     * This method copied from stackoverflow
     * this method use reflection tools to add expected REST API
     * method to supported method array of HttpUrlConnection
     * <a href="https://stackoverflow.com/questions/25163131/httpurlconnection-invalid-http-method-patch">StackOverFlow</a>
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

    /**
     * override toString method to show object in string form
     *
     * @return string form of this object
     */
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
