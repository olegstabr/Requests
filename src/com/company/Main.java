package com.company;

import com.company.Client.CustomThread;
import com.company.Client.SendRequestThread;

public class Main {

    public static void main(String[] args) {
        SendRequestThread sendRequestThread = new SendRequestThread();
        sendRequestThread.start();

        for (int i = 0; i < 10; ++i) {
            CustomThread customThread = new CustomThread();
            customThread.start();
        }
    }
}
