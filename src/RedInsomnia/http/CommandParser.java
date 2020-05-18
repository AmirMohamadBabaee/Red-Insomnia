package RedInsomnia.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CommandParser
 *
 * This class parse command of CLI
 *
 * @author Amir01
 * @version
 */
public class CommandParser {

    private CommandFunction commandFunction;
    private String[] validCommand;
    private String[] commands;


    public CommandParser(String enteredCommand) {

        commandFunction = new CommandFunction();

        validCommand = new String[]{
                "M ", "method ", "headers ", "H ",
                "i", "h ", "help ", "f ", "output",
                "O", "data ", "d ", "json ", "j " ,
                "S", "save", "upload ", "jurl list",
                "jurl "
        };

        if(isValidCommand(enteredCommand)) {

            splitCommand(enteredCommand);

        }

    }


    private void splitCommand(String enteredCommand) {

        Pattern pattern = Pattern.compile("-{1,2}");
        commands = pattern.split(enteredCommand);

    }


    private void commandCaller(String[] commands) {

        for (String command : commands) {

            System.out.println(isValidArgument(command));

        }

    }


    private boolean isValidArgument(String args) {

        for (String command : validCommand) {

            if(args.startsWith(command)) {
                return true;
            }

        }

        return false;

    }


    private boolean isValidCommand(String command) {

        if(!command.startsWith("jurl ")) {

            return false;

        }

        String test = command.replaceAll("--", "%%%%");
        test = test.replaceAll("-", "@@@@");

        Pattern p = Pattern.compile("%%%%");
        Matcher m = p.matcher(test);

        while(m.find()) {

            if((m.end() + 1 < test.length()) && (test.charAt(m.end() + 1) == ' ')) {


                return false;

            }

        }

        p = Pattern.compile("@@@@");
        m = p.matcher(test);

        while(m.find()) {

            if((m.end() + 1 < test.length()) && (test.charAt(m.end() + 1) != ' ')) {

                return false;

            }

        }

        return true;

    }

}
