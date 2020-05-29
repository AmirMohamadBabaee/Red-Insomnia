package RedInsomnia.http;

import java.util.Scanner;

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
    private Scanner scan;

    public JURL(String[] args) {

        String commandArgs = "";

        for (String arg : args) {
            commandArgs += arg + " ";
        }

        commandParser = new CommandParser(commandArgs);

    }
}
