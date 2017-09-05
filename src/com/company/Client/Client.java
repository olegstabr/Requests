package com.company.Client;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Client {
    private final int PORT = 7777;
    private final String IP = "127.0.0.1";
    private static Client instance;

    private Socket clientSocket = null;

    public Client() {
        try {
            clientSocket = new Socket(IP, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(byte[] data) {
        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(clientSocket.getOutputStream());
            outputStream.write(data);
            outputStream.flush();

            ByteBuffer byteBuffer = ByteBuffer.wrap(data);
            int value = byteBuffer.getInt();

            System.out.println("send " + value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateQueue() {

    }


    //
    // Singleton
    //
    public static Client getInstance() {
        Client localInstance = instance;
        if (localInstance == null) {
            synchronized (Client.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Client();
                }
            }
        }
        return localInstance;
    }
}
