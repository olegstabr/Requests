package com.company.Client;

import java.nio.ByteBuffer;

public class SendRequestThread extends Thread {
    private boolean isWork = true;
    private static int id = 0;

    public SendRequestThread() {
        id++;
    }

    public int getThreadId() {
        return id;
    }

    @Override
    public void run() {
        Client client = Client.getInstance();
        byte[] data;
        while (isWork) {
            data = ByteBuffer.allocate(4).putInt(id).array();
            client.send(data);
        }
    }
}
