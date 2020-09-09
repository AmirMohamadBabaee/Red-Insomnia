package RedInsomnia.http;

import RedInsomnia.sync.ResponseSetter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CommandFunction
 *
 * This class define operating method for each command
 *
 * @author Amir01
 * @version
 */
public class CommandFunction {

    private final String RESPONSE_DIR = "./response/";
    private final String REQUEST_DIR = "./request/";
    private HttpRequest httpRequest;
    private String fileName = "";
    private boolean outputCalled;
    private boolean saveCalled;
    private List<HttpRequest> savedRequestList;
    private ResponseSetter responseSetter;


    /**
     * this method called to create a new HttpRequest with default
     * configuration.
     *
     * @param url url entered after jurl command
     */
    public void jurlOperation(String url) {

        httpRequest = new HttpRequest(url, "GET");

    }

    /**
     * this method called to change http method of current request
     *
     * @param method expected http method
     */
    public void methodOperation(String method) {

        httpRequest.setMethod(method);

    }

    /**
     * this method called to set http header map of current request
     *
     * @param head string of headers
     */
    public void headersOperation(String head) {

        if(!head.isEmpty()) {
            httpRequest.setHttpHeader(splitHeaders(head));
        }

    }

    /**
     * this method save response body in response directory
     *
     * @param outputName entered file name
     */
    public void outputOperation(String outputName) {

        boolean isSuccessful = new File(RESPONSE_DIR).mkdirs();
        System.out.println("Created response Directory : " + isSuccessful);

        if(outputName.equals("")) {

            fileName = "output_[" + System.currentTimeMillis() + "].txt";

        } else {

            if(!new File(RESPONSE_DIR + outputName).exists()) {

                fileName = outputName;

            } else {

                System.out.println("There is file with \"" + outputName + "\" name!");
                return;

            }

        }

        outputCalled = true;

    }

    /**
     * this method set form data map of current request
     *
     * @param data string of form data
     */
    public void dataOperation(String data) {

        if(!data.isEmpty()) {
            httpRequest.setHttpData(splitDatas(data));
        }

    }

    /**
     * this method check an entered json string is valid and
     * set it for reqeust body in current request
     *
     * @param jsonStr entered json string
     */
    public void jsonOperation(String jsonStr) {

        if(JsonUtility.isJSONValid(jsonStr)) {

            httpRequest.setJsonStr(JsonUtility.beautifyJson(jsonStr));

        } else {

            System.err.println("jurl : Your entered String is not valid JSON!!!");

        }

    }

    /**
     * this method print list of saved request in request directory
     */
    public void listOperation() {

        if(showSavedRequest()) {

            for(int i = 0 ; i < savedRequestList.size() ; i++) {

                System.out.println("[ " + (i+1) + " ] " + savedRequestList.get(i).toString());

            }

            if(savedRequestList.isEmpty()) {

                System.err.println("There is no saved http request to show!!!");

            }

        }

        System.exit(0);

    }

    /**
     * this method take expected requests indexes and
     * and establish connection of them in entered order
     *
     * @param args indexes of requests
     */
    public void fireOperation(String args) {

        if(showSavedRequest()) {

            List<Integer> index = identifyRequestIndex(args);

            for (Integer i : index) {

                httpRequest = savedRequestList.get(i);
                if(httpRequest.isRequestEnable()) {
                    System.out.println("Fire Request: " + httpRequest.toString());
                    httpRequest.establishConnection();
                }

            }

        }

        System.exit(0);

    }

    /**
     * this method save a request in request directory
     */
    public void saveOperation() {

        boolean isSuccessful = new File(REQUEST_DIR).mkdirs();
        System.out.println("Created request Directory : " + isSuccessful);

        saveCalled = true;

    }

    /**
     * this method show help message of jurl program
     */
    public void helpOperation() {

        String jurl = "\n" +
                "                     /$$   /$$ /$$$$$$$  /$$      \n" +
                "                    | $$  | $$| $$__  $$| $$      \n" +
                "                 /$$| $$  | $$| $$  \\ $$| $$      \n" +
                "                |__/| $$  | $$| $$$$$$$/| $$      \n" +
                "                 /$$| $$  | $$| $$__  $$| $$      \n" +
                "                | $$| $$  | $$| $$  \\ $$| $$      \n" +
                "                | $$|  $$$$$$/| $$  | $$| $$$$$$$$\n" +
                "                | $$ \\______/ |__/  |__/|________/\n" +
                "           /$$  | $$                              \n" +
                "          |  $$$$$$/                              \n" +
                "           \\______/                               \n" +
                "\n";

        jurl += "NAME\n" +
                "\tjurl - command line tool to make HTTP request\n" +
                "SYNOPSIS\n" +
                "\tjurl [ url ] [ options ]\n" +
                "DESCRIPTION\n" +
                "\tjurl is a command line tool to make HTTP request and transfer data\n" +
                "\tbetween client and server. this tool, only support HTTP protocol.\n" +
                "\tthis tool, is a project to simulate environment such as curl command\n" +
                "\tline tool. but this version is so weaker than curl. so if you need \n" +
                "\tmore powerful tool, curl is the best choice.\n" +
                "URL\n" +
                "\tin this tool your entered url should start with http:// or https:// \n" +
                "\tto be valid in jurl. also, your entered url should be exactly after \n" +
                "\tjurl command, in other states, it's possible to make a error message\n" +
                "\tor disorder in tool. example of valid URL :\n\n" +
                "\t    http://example.com\n" +
                "\t    https://example.com\n\n" +
                "OPTIONS\n" +
                "\tOptions start with one or two dashes. Many of the options require\n" +
                "\tadditional value next to them.\n" +
                "\tThe short \"single-dash\" form of the options, -d for example, \n" +
                "\tmay be used with or without a space between it and its value, \n" +
                "\ta space is a recommended separator. The long \"double-dash\" \n" +
                "\tform, -d, --data for example, requires a space between it and its \n" +
                "\tvalue. \n\n" +
                "\t-M, --method < method name>\n" +
                "\t\t(HTTP) Specifies a custom request method to use when \n" +
                "\t\tcommunicating with the HTTP server. The specified request \n" +
                "\t\tmethod will be used instead of the method otherwise used \n" +
                "\t\t(which defaults to GET). Read the HTTP 1.1 specification \n" +
                "\t\tfor details and explanations.\n" +
                "\t\tthis tool support [ GET, POST, PUT, PATCH, DELETE ] request \n" +
                "\t\tmethod. Also this command is not case sensitive. You can\n" +
                "\t\tenter your expected method in lowercase form. Example of \n" +
                "\t\tvalid method command : \n\n" +
                "\t\t    jurl http://example.com/ -M POST\n" +
                "\t\t    jurl http://example.com/ -method post\n\n" +
                "\t-H, --headers < headers >\n" +
                "\t\t(HTTP) Extra header to include in the request when sending \n" +
                "\t\tHTTP to a server. You may specify any number of extra headers.\n" +
                "\t\tYour entered headers should be in a double quote and be in\n" +
                "\t\tkey-value form. in this form, each key-value separate with\n" +
                "\t\tsemicolon(;). Also key and value should be colon separated.\n" +
                "\t\tExample of a valid headers tag : \n\n" +
                "\t\t    jurl http://example.com/ -H \" key1 : value1 ; key2 : value2 ; key3 : value3\"\n" +
                "\t\t    jurl http://example.com/ --headers \" key1 : value1 ; key2 : value2 ; key3 : value3\"\n\n" +
                "\t-d, --data < form data >\n" +
                "\t\t(HTTP) This option is for sending form data to expected server.\n" +
                "\t\tYour entered data should be separated with ampersand(&). This\n" +
                "\t\tpart should be as key-value form too, but in this tag, your \n" +
                "\t\tkey-value should be separated with '='. Also your entered \n" +
                "\t\tform data must be in double quotation marks. Example of valid\n" +
                "\t\tdata tag :\n\n" +
                "\t\t    jurl http://example.com/ -d \" key1 = value1 & key2 = value2 & key3 = value3\"\n" +
                "\t\t    jurl http://example.com/ --data \" key1 = value1 & key2 = value2 & key3 = value3 \"\n\n" +
                "\t-j, --json < json text >\n" +
                "\t\t(HTTP) This option allow you to pass a json string to http reqeust\n" +
                "\t\tinstead of form data. In this program you can pass at most one of \n" +
                "\t\tthe data format. Your entered json string most be in double quotation.\n" +
                "\t\tExample of valid json tag : \n\n" +
                "\t\t    jurl http://example.com/ -j \" { \"key1\" : \"value1\", \"key2\" : \"value2\", \"key3\" : \"value3\" } \"\n" +
                "\t\t    jurl http://example.com/ --json \" { \"key1\" : \"value1\", \"key2\" : \"value2\", \"key3\" : \"value3\" } \" \n\n" +
                "\t-O, --output < file name >\n" +
                "\t\t(HTTP)This option, can save response body of a http request in \n" +
                "\t\ta file in response directory. Argument of this tag is optional.\n" +
                "\t\tIf you add a name for your expected response, file name will be\n" +
                "\t\tit, else jurl automatically generate new name and save it in response\n" +
                "\t\tdirectory. If you want to add a name for this response body file,\n" +
                "\t\tNote that you should choose expected extension for your file else\n" +
                "\t\tjurl save this request without any extesion. also generally commands \n" +
                "\t\twith no arguments, saved in '.txt' file format. So if type of file is \n" +
                "\t\timportant for you, it is better to explicitly choose your expected \n" +
                "\t\tfile format in file name. Example of valid output tag :\n\n" +
                "\t\t    jurl http://example.com/ -O \n" +
                "\t\t    jurl http://example.com/ --output example.html\n\n" +
                "\t--upload < file path >\n" +
                "\t\t(HTTP) This option, can load a binary file from your system and\n" +
                "\t\tsend it through with http request. Argument of this option is \n" +
                "\t\tabsolute file path. this tag automatically add new header to \n" +
                "\t\tidentify content-type and set it as 'application/octet-stream'.\n" +
                "\t\tExample of valid upload tag : \n\n" +
                "\t\t    jurl http://example.com/ --upload /home/myfiles/text.txt\n\n" +
                "\t-f\n" +
                "\t\t(HTTP) This option, enable follow redirect in this http request.\n" +
                "\t\tThis option don't accept any argument and its location not matter\n" +
                "\t\tAlthough it must be after url! \n\n" +
                "\t-i\n" +
                "\t\tThis option determine response header shown or not. If you use \n" +
                "\t\tthis tag, response header will be shown else jurl will discard \n" +
                "\t\tthem.\n\n" +
                "\t-S, --save\n" +
                "\t\t(HTTP) This option, save expected http request with all properties\n" +
                "\t\tin request directory. These http request saved in binary form and \n" +
                "\t\tdon't change it. extension of these file is '.bin'.\n" +
                "\t\tYou can access to your saved http request, with 'jurl list' command.\n" +
                "\t-h, --help\n" +
                "\t\tThis command, show this manual page and you can use it when you\n" +
                "\t\twere confused about jurl. Keep in you mind best tutorial for jurl\n" +
                "\t\tis jurl man page. :)\n\n" +
                "OTHER COMMANDS\n" +
                "\tlist\n" +
                "\t\tYou can use this command to access to your saved http reqeust in\n" +
                "\t\trequest directory. Note that this command don't need any arguments or\n" +
                "\t\tadding url for jurl. this command is exactly without url. so don't\n" +
                "\t\tuse this command with entering any url.\n" +
                "\t\tAlso this command list all of the saved http request with row number\n" +
                "\t\tand you can select them with 'jurl fire' command. Example of valid \n" +
                "\t\tlist command : \n\n" +
                "\t\t    jurl list\n\n" +
                "\tfire < row number of listed http requests >\n" +
                "\t\tYou can use 'jurl list' command, at first. then, use this command\n" +
                "\t\tto select your expected saved http request. In this command you can \n" +
                "\t\tenter more than one row to be fired. in this state, these http request\n" +
                "\t\twill establish in entered order of row number.\n" +
                "\t\tIt's good practice to use this command with 'jurl list'.\n" +
                "\t\tExample of valid fire command : \n\n" +
                "\t\t    jurl fire 2 4 5\n";
        System.out.println(jurl);
        System.exit(0);

    }

    /**
     * this method set a file in current request
     *
     * @param filePath absolute path of this file
     */
    public void uploadOperation(String filePath) {

        File uploadFile = new File(filePath);

        if(uploadFile.isFile()) {

            httpRequest.setUploadedFile(uploadFile);

        }

    }

    /**
     * this method enable follow redirect in current request
     */
    public void followRedirectOperation() {

        httpRequest.setFollowRedirect(true);

    }

    /**
     * this method enable showing response header
     */
    public void showResponseHeaderOperation() {

        httpRequest.setShowResponseHeader(true);

    }

    /**
     * this method establish connection of current request
     */
    public void startConnection() {

        if(httpRequest.isRequestEnable()) {

            if(saveCalled) {

                saveRequest();

            }
            httpRequest.setResponseSetter(responseSetter);
            httpRequest.establishConnection();
            if(outputCalled) {

                outputWriterOperation();
                outputCalled = false;

            }
        }

    }

    /**
     * this method convert header string to map
     *
     * @param h headers string
     * @return map of these headers
     */
    private Map<String, String> splitHeaders(String h) {

        Map<String, String> headers = new HashMap<>();

        h = h.replaceAll("\"", "").trim();

        String[] header = h.split(";");
        for (String head : header) {

            String[] nodes = head.split(":");

            headers.put(nodes[0].trim(), nodes[1].trim());

        }

        return headers;
    }


    /**
     * this method convert data string to map
     *
     * @param d data string
     * @return map of data
     */
    private Map<String, String> splitDatas(String d) {

        Map<String, String> datas = new HashMap<>();

        d = d.replaceAll("\"", "").trim();

        String[] header = d.split("&");
        for (String data : header) {

            String[] nodes = data.split("=");

            datas.put(nodes[0].trim(), nodes[1].trim());

        }

        return datas;

    }


    /**
     * this method complete operation of outputOperation to
     * write response body to file
     */
    private void outputWriterOperation() {

        try(DataOutputStream out = new DataOutputStream(new FileOutputStream(RESPONSE_DIR + fileName))) {

            if(httpRequest.getImageBytes() == null) {

                out.writeBytes(httpRequest.getResponseBody());
                System.out.println("Successfully saved in file \"" + fileName + "\"");

            } else {

                out.write(httpRequest.getImageBytes());
                out.flush();

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            httpRequest.setRequestEnable(false);
        } catch (IOException e) {
            e.printStackTrace();
            httpRequest.setRequestEnable(false);
        }

    }


    /**
     * this method complete saveOperation
     */
    private void saveRequest() {

        String fileName = "request_[" + System.currentTimeMillis() + "].bin";

        try(ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(REQUEST_DIR + fileName))) {

            out.writeObject(httpRequest);
            System.out.println("Successfully saved in file \"" + fileName + "\"");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            httpRequest.setRequestEnable(false);
        } catch (IOException e) {
            e.printStackTrace();
            httpRequest.setRequestEnable(false);
        }

    }

    /**
     * this method complete list and fire operation
     *
     * @return true if be ok else false
     */
    private boolean showSavedRequest() {

        List<HttpRequest> requestList = new ArrayList<>();

        File[] requestFile = new File(REQUEST_DIR).listFiles();

        assert requestFile != null;
        for (File file : requestFile) {

            if(file.isFile()) {

                try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {

                    requestList.add((HttpRequest)in.readObject());

                } catch (FileNotFoundException | ClassNotFoundException e) {
                    e.printStackTrace();
                    return false;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }

            }

        }

        this.savedRequestList = requestList;
        return true;

    }


    /**
     * this method convert string indexes to list of integers
     *
     * @param indexesStr string of indexes
     * @return Integer list of these indexes
     */
    private List<Integer> identifyRequestIndex(String indexesStr) {

        String[] str = indexesStr.split(" ");
        List<Integer> index = new ArrayList<>();

        try{

            for (String s : str) {

                int temp = Integer.parseInt(s) - 1;
                if(temp >= 0 && temp < savedRequestList.size()) {

                    index.add(temp);

                } else {
                    throw new NumberFormatException("This number is not valid index!!!");
                }

            }

        } catch (NumberFormatException e) {
            System.err.println("There is no item with this index!!!");
        }

        return index;

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
}
