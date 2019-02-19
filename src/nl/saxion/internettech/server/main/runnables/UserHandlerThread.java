package nl.saxion.internettech.server.main.runnables;

import nl.saxion.internettech.server.main.client.User;
import nl.saxion.internettech.server.main.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author jarnowitjes on 2019-02-18
 */
public class UserHandlerThread implements Runnable {

    private User client;

    private Logger logger;

    private BufferedReader reader;

    public UserHandlerThread(User client, BufferedReader reader) {
        logger = Logger.getInstance();

        this.client = client;
        this.reader = reader;
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
            } catch (IOException e) {
                logger.logWarning("Handler " + client.getUserName() + " could not read line!");
            }


        }
    }
}
