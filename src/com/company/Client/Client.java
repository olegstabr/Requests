package com.company.Client;

import com.company.Data.Request;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private final int PORT = 7777;
    private final String IP = "127.0.0.1";
    private static volatile Client instance;

    private Socket clientSocket = null;
    private List<Request> queue = new ArrayList<>();

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

    public void updateQueue(Request request) {
        int queueSize = queue.size();
        request.setId(queueSize == 0 ? 0 : queueSize - 1);
        queue.add(request);
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
