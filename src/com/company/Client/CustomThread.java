package com.company.Client;

import com.company.Data.Request;

public class CustomThread extends Thread {
    private boolean isWork = true;
    private static int id = 0;

    public CustomThread() {
        id++;
    }

    public int getThreadId() {
        return id;
    }

    public void sleep() {
        isWork = false;
    }

    @Override
    public void run() {
        Client client = Client.getInstance();
        while (isWork) {
            client.updateQueue(new Request());
            sleep();
        }
    }
}
