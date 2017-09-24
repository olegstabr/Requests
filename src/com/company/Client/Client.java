package com.company.Client;

import com.company.Data.Request;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

class Client {
    private final int PORT = 7777;
    private final String IP = "127.0.0.1";
    private static volatile Client instance;

    private Socket clientSocket = null;
    private List<Request> queue = Collections.synchronizedList(new ArrayList<>());

    private Client() {
        try {
            clientSocket = new Socket(IP, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void send() {
        ReentrantLock locker = new ReentrantLock();
        try {
            boolean lockFlag = locker.tryLock(100, TimeUnit.MILLISECONDS);
            if (lockFlag) {
                try {
                    BufferedOutputStream outputStream = new BufferedOutputStream(clientSocket.getOutputStream());

                    while (!queue.isEmpty()) {
                        Request request = queue.get(0);
                        byte[] data = request.getBytes();
                        int value = request.getId();
                        outputStream.write(data);
                        outputStream.flush();

                        queue.remove(request);
                        System.out.println("send " + request.toString());
                    }
                } finally {
                    locker.unlock();
                }
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    void updateQueue(Request request) {
        ReentrantLock locker = new ReentrantLock();
        locker.lock();
        try {
            int queueSize = queue.size();
            request.setId(queueSize == 0 ? 0 : queueSize - 1);
            queue.add(request);
        } finally {
            locker.unlock();
        }

    }

    //
    // Singleton
    //

    static Client getInstance() {
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
