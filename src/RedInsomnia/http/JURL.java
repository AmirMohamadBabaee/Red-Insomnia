package RedInsomnia.http;

/**
 * JURL
 *
 * This class is main class of this cli program
 *
 * @author Amir01
 * @version
 */
public class JURL {

    private CommandParser commandParser;

    /**
     * Constructor of JURL class
     *
     * @param args array of program arguments
     */
    public JURL(String[] args) {

        String commandArgs = "";

        for (String arg : args) {
            commandArgs += arg + " ";
        }

        commandParser = new CommandParser(commandArgs);

    }
}
