package nl.saxion.internettech.server.main.client;

import nl.saxion.internettech.server.main.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class User {

    private String userName;
    private Logger logger;

    private PrintWriter writer;
    private BufferedReader reader;


    public User(String userName, PrintWriter writer, BufferedReader reader) {
        this.userName = userName;
        this.writer = writer;
        this.reader = reader;

        logger = Logger.getInstance();
    }

    //Getters
    public String getUserName() {
        return userName;
    }
}
