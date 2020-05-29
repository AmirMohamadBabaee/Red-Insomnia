package RedInsomnia.http;

import com.google.gson.*;

/**
 * JsonUtility
 *
 * this class defined to do operation related to json files
 *
 * @author Amir01
 * @version
 */
public final class JsonUtility {

    private static final Gson gson = new Gson();


    /**
     * this method check param String, is a valid json or not.
     *
     * @param jsonInString param string want to be checked
     * @return true, if this string be valid else false
     */
    public static boolean isJSONValid(String jsonInString) {
        try {
            gson.fromJson(jsonInString, Object.class);
            return true;
        } catch(com.google.gson.JsonSyntaxException ex) {
            return false;
        }
    }

    /**
     * this method beautify a json String.
     * it's important to call this method after calling isJsonValid
     * method to avoid mistakes.
     *
     * @param jsonString param json string
     * @return beautified json string
     */
    public static String beautifyJson(String jsonString) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(jsonString);
        String prettyJsonString = gson.toJson(je);

        return prettyJsonString;
    }

}
