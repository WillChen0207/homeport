package com.homeport.app.Entity;

import jakarta.persistence.*;

@Entity(name = "Message")
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", nullable = false)
    private Integer message_id;

    @Column(name = "message_content")
    private String messageContent;

    @Column(name = "message_type")
    private Integer messageType;

    @Column(name = "receiver_id")
    private String receiverId;

    @Column(name = "sender_id")
    private String senderId;

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Integer getMessageId() {
        return message_id;
    }

    public void setMessageId(Integer message_id) {
        this.message_id = message_id;
    }
}