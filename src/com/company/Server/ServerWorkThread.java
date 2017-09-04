package com.company.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerWorkThread extends Thread {
    private boolean isWork = true;
    private ServerSocket serverSocket;

    public ServerWorkThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            while (isWork) {
                Socket clientSocket = serverSocket.accept();
                System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " connected...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
