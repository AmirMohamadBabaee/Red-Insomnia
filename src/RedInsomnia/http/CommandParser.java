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
                "i", "h", "help", "f", "output",
                "O", "data ", "d ", "json ", "j " ,
                "S", "save", "upload ", "jurl list",
                "jurl "
        };

        if(isValidCommand(enteredCommand)) {

            splitCommand(enteredCommand);
            if(commandCaller()) {

                commandFunction.startConnection();

            }

        }

    }


    private void splitCommand(String enteredCommand) {

        Pattern pattern = Pattern.compile(" -{1,2}");
        commands = pattern.split(enteredCommand);

    }


    private boolean commandCaller() {

        for (String command : commands) {

            if(!isValidArgument(command)) {

                System.out.println("jurl : This args is not valid!!! \nProblem < " + command + " >");
                return false;

            }

        }

        for (String command : commands) {

            callOperation(command);

        }

        return true;

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

            System.out.println("jurl : Your entered Command was INCORRECT! try \"jurl -h\" or \"jurl --help\" for more information");
            return false;

        }

        String test = command.replaceAll("--", "%%%%");
        test = test.replaceAll(" -", "@@@@");

        Pattern p = Pattern.compile("%%%%");
        Matcher m = p.matcher(test);

        while(m.find()) {

            if((m.end() + 1 < test.length()) && (test.charAt(m.end() + 1) == ' ')) {

                if(test.charAt(m.end() + 1) == '@' || test.charAt(m.end() + 1) == '%') {
                    continue;
                }
                System.out.println("jurl : Your entered Command was INCORRECT! try \"jurl -h\" or \"jurl --help\" for more information");
                return false;

            }

        }

        p = Pattern.compile("@@@@");
        m = p.matcher(test);

        while(m.find()) {

            if((m.end() + 1 < test.length()) && (test.charAt(m.end() + 1) != ' ')) {

                if(test.charAt(m.end() + 1) == '@' || test.charAt(m.end() + 1) == '%') {
                    continue;
                }
                System.out.println("jurl : Your entered Command was INCORRECT! try \"jurl -h\" or \"jurl --help\" for more information");
                return false;

            }

        }

        if((test.contains("%%%%data") || test.contains("@@@@d")) &&
                (test.toLowerCase().contains("%%%%method get") || test.toLowerCase().contains("@@@@m get")
                || (!test.contains("%%%%method") && !test.contains("@@@@M")))) {

            System.out.println("HTTP GET method doesn't have any data(Request Body)! \nPlease try again with POST method");
            return false;

        }

        return true;

    }


    private void callOperation(String args) {

        if(args.startsWith("method ")) {

            args = args.replace("method", "").trim();
            commandFunction.methodOperation(args);

        } else if(args.startsWith("M ")) {

            args = args.replace("M", "").trim();
            commandFunction.methodOperation(args);

        } else if(args.startsWith("headers ")) {

            args = args.replace("headers", "").trim();
            commandFunction.headersOperation(args);

        } else if(args.startsWith("H ")) {

            args = args.replace("H", "").trim();
            commandFunction.headersOperation(args);

        } else if(args.startsWith("data ")) {

            args = args.replace("data", "").trim();
            commandFunction.dataOperation(args);

        } else if(args.startsWith("d ")) {

            args = args.replace("d", "").trim();
            commandFunction.dataOperation(args);

        } else if(args.startsWith("jurl list")) {

            args = args.replace("jurl list", "").trim();
            commandFunction.listOperation();

        } else if(args.startsWith("jurl fire")) {

            args = args.replace("jurl fire", "").trim();
            commandFunction.fireOperation(args);

        } else if(args.startsWith("output")) {

            args = args.replace("output", "").trim();
            commandFunction.outputOperation(args);

        } else if(args.startsWith("O")) {

            args = args.replace("O", "").trim();
            commandFunction.outputOperation(args);

        } else if(args.startsWith("json ")) {

            args = args.replace("json", "").trim();
            commandFunction.jsonOperation(args);

        } else if(args.startsWith("j ")) {

            args = args.replace("j", "").trim();
            commandFunction.jsonOperation(args);

        } else if(args.startsWith("f")) {

            args = args.replace("f", "").trim();
            commandFunction.followRedirectOperation();

        } else if(args.startsWith("i")) {

            args = args.replace("i", "").trim();
            commandFunction.showResponseHeaderOperation();

        } else if(args.startsWith("S")) {

            args = args.replace("S", "").trim();
            commandFunction.saveOperation();

        } else if(args.startsWith("save")) {

            args = args.replace("save", "").trim();
            commandFunction.saveOperation();

        } else if(args.startsWith("jurl ")) {

            args = args.replace("jurl ", "").trim();
            commandFunction.jurlOperation(args);

        } else if(args.startsWith("upload")) {

            args = args.replace("upload", "").trim();
            commandFunction.uploadOperation(args);

        }

    }

}
