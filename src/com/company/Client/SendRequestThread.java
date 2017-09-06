package com.company.Client;

import com.company.Data.Request;

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
        while (isWork) {
            client.updateQueue(new Request());
        }
    }
}
