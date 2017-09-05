package com.company;

import com.company.Client.Client;
import com.company.Client.SendRequestThread;

public class Main {

    public static void main(String[] args) {
        SendRequestThread sendRequestThread = new SendRequestThread();
        sendRequestThread.start();
    }
}
