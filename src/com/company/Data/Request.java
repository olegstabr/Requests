package com.company.Data;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class Request {
    private int id;
    private String message;
    private int bufferSize = 1024 * 8;

    public Request() { }

    public Request(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public byte[] getBytes() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
        try {
            int messageLength = message.getBytes("UTF-8").length;
            byteBuffer.putInt(id);
            byteBuffer.putInt(messageLength);
            byteBuffer.put(message.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return byteBuffer.array();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ID: " + id + " ");
        builder.append("Message: " + message);
        return builder.toString();
    }
}
