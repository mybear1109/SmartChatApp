package com.mybea1109.SmartChatApp.chat;

public class ChatMessage {
    private String senderId;
    private String message;
    private long timestamp;

    public ChatMessage() {}  // Firestore 직렬화를 위해 필요

    public ChatMessage(String senderId, String message, long timestamp) {
        this.senderId = senderId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getSenderId() { return senderId; }
    public String getMessage() { return message; }
    public long getTimestamp() { return timestamp; }
}
