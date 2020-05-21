package RedInsomnia.http;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;
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
    private HttpRequest httpRequest;
    private String fileName = "";
    private boolean outputCalled;


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



    }

    public void saveOperation() {



    }

    public void helpOperation() {



    }

    public void uploadOperation() {



    }

    public void followRedirectOperation() {

        httpRequest.setFollowRedirect(true);

    }

    public void showResponseHeaderOperation() {

        httpRequest.setShowResponseHeader(true);

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


    public Map<String, String> splitDatas(String d) {

        Map<String, String> datas = new HashMap<>();

        d = d.replaceAll("\"", "").trim();

        String[] header = d.split("&");
        for (String data : header) {

            String[] nodes = data.split("=");

            datas.put(nodes[0].trim(), nodes[1].trim());

        }

        return datas;

    }


    public void startConnection() {

        if(httpRequest.isRequestEnable()) {
            httpRequest.establishConnection();
            outputWriterOperation();
            outputCalled = false;
        }

    }

}
