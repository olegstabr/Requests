package com.company.Data;

import java.nio.ByteBuffer;

public class Request {
    private int id;

    public Request() { }

    public Request(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public byte[] getBytes() {
        return ByteBuffer.allocate(4).putInt(id).array();
    }

    public void setId(int id) {
        this.id = id;
    }
}
