package nl.saxion.internettech.server.main.runnables;

import nl.saxion.internettech.server.main.Main;
import nl.saxion.internettech.server.main.client.User;
import nl.saxion.internettech.server.main.encoding.Encoder;
import nl.saxion.internettech.server.main.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Pattern;

public class SignInThread implements Runnable {

    private Logger logger;

    private Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader reader;

    private final Pattern userNameRegex = Pattern.compile("[A-Za-z0-9_]+");

    public SignInThread(Socket clientSocket) throws IOException  {
        logger = Logger.getInstance();
        this.clientSocket = clientSocket;
        writer = new PrintWriter(clientSocket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    @Override
    public void run() {
        signInUser();
    }

    private void signInUser() {
        sendWelcomeMessage();
        String userName = readUserName();
        sendEncodedUserName(userName);

        User client = new User(userName, writer, reader, clientSocket);
        Main.clients.add(client);
        logger.logInfo("New user signed in: " + userName);
    }

    private String readUserName() {
        String username = "";

        boolean validUserName = validateUserName(username);

        while (!validUserName) {
            try {
                String line = reader.readLine();

                username = extractUserName(line);

                validUserName = validateUserName(username);

                if (validUserName) {
                    return username;
                } else {
                    writer.println("+ERR Invalid username");
                    writer.flush();
                }

            } catch (IOException e) {
                logger.logError("Could not read username!");
            }
        }

        return username;
    }

    private void sendWelcomeMessage() {
        writer.println("HELO ");
        writer.flush();
    }

    private void sendEncodedUserName(String username) {
        String encoded = Encoder.encode64MD5(username);
        writer.println("+OK " + encoded);
        writer.flush();
    }

    private boolean validateUserName(String toTest) {
        return userNameRegex.matcher(toTest).matches();
    }

    private String extractUserName(String line) {
        String[] splitResult = line.split(" ");

        if (splitResult.length == 2) {
            return splitResult[1];
        } else {
            return "";
        }
    }
}