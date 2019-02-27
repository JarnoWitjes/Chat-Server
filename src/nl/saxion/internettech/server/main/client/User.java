package nl.saxion.internettech.server.main.client;

import nl.saxion.internettech.server.main.Main;
import nl.saxion.internettech.server.main.logger.Logger;
import nl.saxion.internettech.server.main.runnables.UserHandlerThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class User {

    private String userName;

    private List<User> clients;
    private Logger logger;

    private PrintWriter writer;
    private BufferedReader reader;
    private Socket clientSocket;

    private UserHandlerThread handler;

    public User(String userName, PrintWriter writer, BufferedReader reader, Socket clientSocket) {
        this.userName = userName;
        this.writer = writer;
        this.reader = reader;
        this.clientSocket = clientSocket;

        logger = Logger.getInstance();
        clients = Main.clients;

        handler = new UserHandlerThread(this, reader, clientSocket);
        Thread thread = new Thread(handler);
        thread.start();
    }

    //Methods

    public void sendBroadcast(String userName, String message) {
        writer.println("<" + userName + "> " + message);
        writer.flush();
    }

    public void sendUsers(String users) {
        writer.println("<server> " + users);
        writer.flush();
    }

    //Getters
    public String getUserName() {
        return userName;
    }
}