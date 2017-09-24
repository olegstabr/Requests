package com.company.Server;

import com.company.Data.Request;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {
    private final int PORT = 7777;

    private ServerSocket serverSocket = null;
    private List<Request> queue = Collections.synchronizedList(new ArrayList<>());

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

    public void send() {

    }

    public void updateQueue(Request request) {

    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
