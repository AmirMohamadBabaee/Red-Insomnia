package RedInsomnia.http;

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

        String fileName = "";

        if(outputName == null) {

            fileName = "output_[" + System.currentTimeMillis() + "]";

        } else {

            if(!new File(RESPONSE_DIR + outputName).exists()) {

                fileName = outputName;

            } else {

                // todo : this log must be changed for connection of this to GUI
                System.out.println("There is file with \"" + outputName + "\" name!");
                return;

            }

        }

        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(RESPONSE_DIR + fileName))) {

            out.writeObject(httpRequest.getResponseBody());
            System.out.println("Successfully saved in file " + fileName);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void dataOperation(String data) {

        httpRequest.setHttpData(splitDatas(data));

    }

    public void jsonOperation(String jsonStr) {

        jsonStr = jsonStr.substring(1, jsonStr.length()-1).trim();

        try {

            JSONObject jsonObject = new JSONObject(jsonStr);
            httpRequest.setJsonStr(jsonStr);

        } catch(JSONException err) {
            err.printStackTrace();
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
        }

    }

}
