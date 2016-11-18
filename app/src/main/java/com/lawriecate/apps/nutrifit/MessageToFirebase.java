package com.lawriecate.apps.nutrifit;

/**
 * Created by touss_000 on 16/11/2016.
 */
public class MessageToFirebase {
    String message;
    String author;

    public MessageToFirebase() {
    }

    public MessageToFirebase(String message, String author) {
        this.message = message;
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}