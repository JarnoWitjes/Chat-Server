package nl.saxion.internettech.server.main;

import nl.saxion.internettech.server.main.client.User;
import nl.saxion.internettech.server.main.logger.Logger;
import nl.saxion.internettech.server.main.runnables.SignInThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private ServerSocket serverSocket;
    private Logger logger;

    public static List<User> clients;

    public static void main(String[] args) {
        new Main().startServer();
    }

    private void startServer() {
        logger = Logger.getInstance();
        clients = new ArrayList<>();

        try {
            logger.setLogLevel(Logger.LOG_LEVEL_DEBUG);

            serverSocket = new ServerSocket(1337);

            logger.logInfo("Server has started!");
            printServerSocketDetails();

            acceptClients();

        } catch (IOException e) {
            logger.logError("Could not setup server socket!");
        }
    }

    private void printServerSocketDetails() {
        System.out.println("Server socket details");
        System.out.println("IP-address:\t" + serverSocket.getInetAddress());
        System.out.println("Port      :\t" + serverSocket.getLocalPort());
    }

    private void acceptClients() {
        //TODO Make a boolean to keep accepting clients. So the server can be quit gracefully
        while(true) {
            try {
                Socket clientSocket = serverSocket.accept();
                logger.logInfo("Accepted new client");

                SignInThread signInThread = new SignInThread(clientSocket);
                Thread thread = new Thread(signInThread);
                thread.start();

            } catch (IOException e) {
                logger.logError("Could not accept new client.");
            }
        }
    }
}