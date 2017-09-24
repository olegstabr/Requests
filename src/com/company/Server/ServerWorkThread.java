package com.company.Server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ServerWorkThread extends Thread {
    private Socket clientSocket;
    private boolean connected = true;

    ServerWorkThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(clientSocket.getInputStream());
            System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " connected...");

            while (connected) {
                byte[] data = new byte[4];
                int numBytes = bufferedInputStream.read(data, 0, 4);

                if (numBytes == -1) {
                    System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " disconnected...");
                    connected = false;
                    break;
                }

                ByteBuffer byteBuffer = ByteBuffer.wrap(data);
                int value = byteBuffer.getInt();
                System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " | data = " + value);
            }
            bufferedInputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
