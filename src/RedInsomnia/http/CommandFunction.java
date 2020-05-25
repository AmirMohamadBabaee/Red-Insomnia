package RedInsomnia.http;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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


    public void jurlOperation(String url) {

        httpRequest = new HttpRequest(url, "GET");

    }

    public void methodOperation(String method) {

        httpRequest.setMethod(method);

    }

    public void headersOperation(String head) {

        httpRequest.setHttpHeader(splitHeaders(head));

    }

    public void outputOperation(String outputName) {

        boolean isSuccessful = new File(RESPONSE_DIR).mkdirs();
        System.out.println("Created response Directory : " + isSuccessful);

        if(outputName.equals("")) {

            fileName = "output_[" + System.currentTimeMillis() + "].txt";

        } else {

            if(!new File(RESPONSE_DIR + outputName).exists()) {

                fileName = outputName;

            } else {

                // todo : this log must be changed for connection of this to GUI
                System.out.println("There is file with \"" + outputName + "\" name!");
                return;

            }

        }

        outputCalled = true;

    }

    public void dataOperation(String data) {

        httpRequest.setHttpData(splitDatas(data));

    }

    public void jsonOperation(String jsonStr) {

        boolean isJsonArray = true;
        boolean isJsonObject = true;

        jsonStr = jsonStr.substring(1, jsonStr.length()-1).trim();

        try {

            JSONArray jsonArray = new JSONArray(jsonStr);
            httpRequest.setJsonStr(jsonStr);

        } catch(JSONException err) {
            isJsonArray = false;
        }

        try {

            JSONObject jsonObject = new JSONObject(jsonStr);
            httpRequest.setJsonStr(jsonStr);

        } catch(JSONException err) {
            isJsonObject = false;
        }

        if(!(isJsonArray || isJsonObject)) {

            System.out.println("jurl : your entered JSON string is not valid!!!");
            httpRequest.setRequestEnable(false);

        }

    }

    public void listOperation() {

        if(showSavedRequest()) {

            for(int i = 0 ; i < savedRequestList.size() ; i++) {

                System.out.println("[ " + (i+1) + " ] " + savedRequestList.get(i).toString());

            }

        }

        System.exit(0);

    }

    public void fireOperation(String args) {

        if(showSavedRequest()) {

            List<Integer> index = identifyRequestIndex(args);

            for (Integer i : index) {

                httpRequest = savedRequestList.get(i);
                if(httpRequest.isRequestEnable()) {
                    httpRequest.establishConnection();
                }

            }

        }

        System.exit(0);

    }

    public void saveOperation() {

        boolean isSuccessful = new File(REQUEST_DIR).mkdirs();
        System.out.println("Created request Directory : " + isSuccessful);

        saveCalled = true;

    }

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
                "\tor disorder in tool. example of valid URL :\n" +
                "\n\t    http://example.com\n" +
                "\t    https://example.com\n\n" +
                "OPTIONS\n" +
                "\tOptions start with one or two dashes. Many of the options require\n" +
                "\tadditional value next to them.\n" +
                "\tThe short \"single-dash\" form of the options, -d for example, \n" +
                "\tmay be used with or without a space between it and its value, \n" +
                "\ta space is a recommended separator. The long \"double-dash\" \n" +
                "\tform, -d, --data for example, requires a space between it and its \n" +
                "\tvalue. \n\n" +
                "\t-M, --method < method name>\n\n" +
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
                "\t-H, -headers < headers >\n\n" +
                "\t\t(HTTP) Extra header to include in the request when sending \n" +
                "\t\tHTTP to a server. You may specify any number of extra headers.\n" +
                "\t\tYour entered headers should be in a double quote and be in\n" +
                "\t\tkey-value form. in this form, each key-value separate with\n" +
                "\t\tsemicolon(;). Also key and value should be colon separated.\n" +
                "\t\tExample of a valid headers tag : \n\n" +
                "\t\t    jurl http://example.com/ -H \" key1 : value1 ; key2 : value2 ; key3 : value3\"\n" +
                "\t\t    jurl http://example.com/ --headers \" key1 : value1 ; key2 : value2 ; key3 : value3\"\n\n";
        System.out.println(jurl);
        System.exit(0);

    }

    public void uploadOperation(String filePath) {

        File uploadFile = new File(filePath);

        if(uploadFile.isFile()) {

            httpRequest.setUploadedFile(uploadFile);

        }

    }

    public void followRedirectOperation() {

        httpRequest.setFollowRedirect(true);

    }

    public void showResponseHeaderOperation() {

        httpRequest.setShowResponseHeader(true);

    }

    public void startConnection() {

        if(httpRequest.isRequestEnable()) {

            if(saveCalled) {

                saveRequest();

            }
            httpRequest.establishConnection();
            if(outputCalled) {

                outputWriterOperation();
                outputCalled = false;

            }
        }

    }



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


    private void outputWriterOperation() {

        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(RESPONSE_DIR + fileName))) {

            out.writeBytes(httpRequest.getResponseBody());
            System.out.println("Successfully saved in file \"" + fileName + "\"");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            httpRequest.setRequestEnable(false);
        } catch (IOException e) {
            e.printStackTrace();
            httpRequest.setRequestEnable(false);
        }

    }


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
            e.printStackTrace();
        }

        return index;

    }

}
