package com.company.Client;

public class SendRequestThread extends Thread {
    private boolean isWork = true;

    public SendRequestThread() { }

    public void sleep() {
        isWork = false;
    }

    @Override
    public void run() {
        Client client = Client.getInstance();
        while (isWork) {
            client.send();
        }
    }
}
