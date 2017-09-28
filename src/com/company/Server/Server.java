package com.company.Server;

import com.company.Data.Request;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Server {
    private final int PORT = 7777;
    private static volatile Server instance;

    private ServerSocket serverSocket = null;
    private Map<Socket, Request> queue = Collections.synchronizedMap(new LinkedHashMap<>());

    private Server() {
        try {
            System.out.println("Server is starting...");
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send() {
        ReentrantLock locker = new ReentrantLock();
        try {
            boolean lockFlag = locker.tryLock(100, TimeUnit.MILLISECONDS);
            if (lockFlag) {
                try {
                    while (!queue.isEmpty()) {
                        Map.Entry<Socket, Request> entry = queue.entrySet().iterator().next();

                        Socket client = entry.getKey();
                        Request request = entry.getValue();

                        BufferedOutputStream outputStream = new BufferedOutputStream(client.getOutputStream());
                        outputStream.write(request.getBytes());
//                        outputStream.flush();
//                        outputStream.close();

                        queue.remove(entry);

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

    public void updateQueue(Socket client, Request request) {
        ReentrantLock locker = new ReentrantLock();
        locker.lock();
        try {
            queue.put(client, request);
        } finally {
            locker.unlock();
        }
    }

    private void start() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                ServerWorkThread serverWorkThread = new ServerWorkThread(clientSocket);
                serverWorkThread.start();
                SendResponseThread sendResponseThread = new SendResponseThread();
                sendResponseThread.start();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    //
    // Singleton
    //

    static Server getInstance() {
        Server localInstance = instance;
        if (localInstance == null) {
            synchronized (Server.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Server();
                }
            }
        }
        return localInstance;
    }

    public static void main(String[] args) {
        Server server = Server.getInstance();
        server.start();
    }
}
