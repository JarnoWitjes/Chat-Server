package nl.saxion.internettech.server.main.command;

import java.util.Arrays;

/**
 * @author jarnowitjes on 2019-02-26
 */
public class ChatCommand {

    public String command;
    public String[] parameters;

    public ChatCommand(String line) {
        constructCommand(line);
    }

    private void constructCommand(String input) {
        String[] splitResult = input.split(" ");

        command = splitResult[0];

        if (splitResult.length > 1) {
            parameters = new String[splitResult.length-1];
            System.arraycopy(splitResult, 1, parameters, 0, splitResult.length-1);
        }
    }

    @Override
    public String toString() {
        return "Command " + command + "\nParameters " + Arrays.toString(parameters);
    }
}