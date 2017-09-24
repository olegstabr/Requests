package com.company.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int PORT = 7777;

    private ServerSocket serverSocket = null;

    private Server() {
        try {
            System.out.println("Server is starting...");
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void start() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                ServerWorkThread serverWorkThread = new ServerWorkThread(clientSocket);
                serverWorkThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
