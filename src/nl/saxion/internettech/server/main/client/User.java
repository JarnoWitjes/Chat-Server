package nl.saxion.internettech.server.main.client;

import nl.saxion.internettech.server.main.logger.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class User {

    private String userName;
    private Logger logger;

    private PrintWriter writer;

    private boolean loggedIn = false;

    private User(Socket clientSocket) {
        logger = Logger.getInstance();

        try {
            writer = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            logger.logError("Could not setup new user.");
        }
    }

    public static User setupUser(Socket clientSocket) {
        return new User(clientSocket);
    }

    //Getters
    public String getUserName() {
        return userName;
    }
}
