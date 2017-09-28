package com.company.Server;

public class SendResponseThread extends Thread {
    private boolean isWork = true;

    public SendResponseThread() { }

    public boolean isWork() {
        return isWork;
    }

    public void setWork(boolean isWork) {
        this.isWork = isWork;
    }

    @Override
    public void run() {
        Server server = Server.getInstance();
        while (isWork) {
            server.send();
        }
    }
}
