package nl.saxion.internettech.server.main.runnables;

import nl.saxion.internettech.server.main.Main;
import nl.saxion.internettech.server.main.client.User;
import nl.saxion.internettech.server.main.command.ChatCommand;
import nl.saxion.internettech.server.main.command.data.Commands;
import nl.saxion.internettech.server.main.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * @author jarnowitjes on 2019-02-18
 */
public class UserHandlerThread implements Runnable {

    private User client;
    private String userName;
    private Socket clientSocket;

    private List<User> clients;

    private Logger logger;

    private BufferedReader reader;

    public UserHandlerThread(User client, BufferedReader reader, Socket clientSocket) {
        logger = Logger.getInstance();
        clients = Main.clients;
        userName = client.getUserName();

        this.client = client;
        this.reader = reader;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        logger.logDebug("User handler started for user: " + client.getUserName());
        listen();
    }

    private void listen() {
        String line = "";

        //TODO Make a boolean to stop listening and quit the handler gracefully
        while (true) {
            try {
                line = reader.readLine();
                logger.logDebug("Read line from user " + client.getUserName() + " " + line);
            } catch (IOException e) {
                logger.logWarning("Handler " + client.getUserName() + " could not read line!");
            }

            if (!line.isEmpty()) {
                ChatCommand chatCommand = new ChatCommand(line);
                logger.logDebug(chatCommand.toString());

                switch (chatCommand.command) {
                    case Commands.BROADCAST:
                        broadcastMessage(chatCommand);
                        break;
                    case Commands.GET_USERS:
                        getUsers();
                        break;
                    case Commands.QUIT:
                        quit();
                        break;
                }
            }
        }
    }

    private void broadcastMessage(ChatCommand chatCommand) {
        //Construct the message from the parameters from the ChatCommand
        StringBuilder builder = new StringBuilder();
        String message;

        for(String part : chatCommand.parameters) {
            builder.append(part);
        }

        message = builder.toString();

        //Send the built message to every user except the user that send the broadcast
        for (User client : clients) {
            if (!client.getUserName().equals(userName))
                client.sendBroadcast(userName, message);
        }
    }

    private void getUsers() {
        StringBuilder builder = new StringBuilder();
        builder.append("[ ");
        for (User client : clients) {
            builder.append(client.getUserName());
            builder.append(", ");
        }
        builder.append("]");

        String result = builder.toString();
        client.sendUsers(result);
    }

    private void quit() {
        try {
            clientSocket.getInputStream().close(); //Close the input stream
            clientSocket.getOutputStream().close(); //Close the output stream
            clientSocket.close(); //Close the socket connection
        } catch (IOException e) {
            logger.logWarning("Could not gracefully close socket of user: " + userName);
        }

        clients.remove(client); //Remove this instance from the connected user list
    }
}