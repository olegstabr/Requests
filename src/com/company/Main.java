package com.company;

import com.company.Client.CustomThread;
import com.company.Client.SendRequestThread;

public class Main {
    private static volatile boolean keepRunning = true;

    public static void main(String[] args) {
        final SendRequestThread sendRequestThread = new SendRequestThread();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                keepRunning = false;
                sendRequestThread.sleep();
                sendRequestThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        for (int i = 0; i < 10; ++i) {
            CustomThread customThread = new CustomThread();
            customThread.start();
        }

        sendRequestThread.start();
    }

}
