package nl.saxion.internettech.server.main.runnables;

import nl.saxion.internettech.server.main.client.User;

import java.net.Socket;

public class SignInThread implements Runnable {
    private Socket clientSocket;

    private User client;

    public SignInThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        signInUser();
    }

    public void signInUser() {
        client = User.setupUser(clientSocket);
    }
}