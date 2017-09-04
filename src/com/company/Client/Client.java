package com.company.Client;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private final int PORT = 7777;
    private final String IP = "127.0.0.1";

    private Socket clientSocket = null;

    public Client() {
        try {
            clientSocket = new Socket(IP, PORT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
