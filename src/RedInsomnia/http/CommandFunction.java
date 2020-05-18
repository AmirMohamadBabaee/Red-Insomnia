package RedInsomnia.http;

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

    public void outputOperation() {



    }

    public void dataOperation(String data) {

        httpRequest.setHttpData(splitDatas(data));

    }

    public void jsonOperation() {



    }

    public void listOperation() {



    }

    public void saveOperation() {



    }

    public void helpOperation() {



    }

    public void uploadOperation() {



    }



    public Map<String, String> splitHeaders(String h) {

        Map<String, String> headers = new HashMap<>();

        String[] header = h.split(";");
        for (String head : header) {

            String[] nodes = head.split(":");

            headers.put(nodes[0], nodes[1]);

        }

        return headers;
    }


    public Map<String, String> splitDatas(String d) {

        Map<String, String> datas = new HashMap<>();

        String[] header = d.split("&");
        for (String data : header) {

            String[] nodes = data.split("=");

            datas.put(nodes[0], nodes[1]);

        }

        return datas;

    }

}
