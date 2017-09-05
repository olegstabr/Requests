package com.company.Server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

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

                while (true){
                    byte[] data = new byte[4];
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(clientSocket.getInputStream());
                    bufferedInputStream.read(data, 0, 4);

                    ByteBuffer byteBuffer = ByteBuffer.wrap(data);
                    int value = byteBuffer.getInt();
                    System.out.println(value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
