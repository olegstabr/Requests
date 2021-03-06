package com.company.Server;

import com.company.Data.Request;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ServerWorkThread extends Thread {
    private Socket clientSocket;
    private boolean connected = true;
    private int bufferSize = 1024 * 8;

    ServerWorkThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            Server server = Server.getInstance();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(clientSocket.getInputStream());
            System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " connected...");

            while (connected) {
                Request request;
                byte[] data = new byte[bufferSize];
                int numBytes = bufferedInputStream.read(data);

                if (numBytes == -1) {
                    System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " disconnected...");
                    connected = false;
                    break;
                }

                request = decodeMessage(data);
                server.updateQueue(clientSocket, request);

                System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " | request = " + request.toString());
            }
            bufferedInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Request decodeMessage(byte[] data) {
        Request request = null;
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(data);
            int requestId = byteBuffer.getInt();
            int messageLength = byteBuffer.getInt();
            byte[] messageBytes = new byte[messageLength];
            byteBuffer.get(messageBytes, 0, messageLength);
            String message = new String(messageBytes, "UTF-8");

            request = new Request(requestId, message);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return request;
    }
}
