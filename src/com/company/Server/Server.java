package com.company.Server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private final int PORT = 7777;

    private ServerSocket serverSocket = null;
    private ServerWorkThread serverWorkThread = null;

    public Server() {
        try {
            serverSocket = new ServerSocket(PORT);
            serverWorkThread = new ServerWorkThread(serverSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        serverWorkThread.start();
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
