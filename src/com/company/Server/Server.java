package com.company.Server;

import com.company.Data.Request;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Server {
    private final int PORT = 7777;
    private static volatile Server instance;

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
                break;
            }
        }
    }

    public void send() {
        ReentrantLock locker = new ReentrantLock();
        try {
            boolean lockFlag = locker.tryLock(100, TimeUnit.MILLISECONDS);
            if (lockFlag) {
                try {

                } finally {
                    locker.unlock();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateQueue(Request request) {
        ReentrantLock locker = new ReentrantLock();
        locker.lock();
        try {

        } finally {
            locker.unlock();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
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
}
