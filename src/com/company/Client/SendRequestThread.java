package com.company.Client;

import com.company.Data.Request;

public class SendRequestThread extends Thread {
    private boolean isWork = true;

    public SendRequestThread() { }

    @Override
    public void run() {
        Client client = Client.getInstance();
        while (isWork) {
            client.send();
        }
    }
}
