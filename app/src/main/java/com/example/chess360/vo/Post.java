package com.example.chess360.vo;

import java.time.LocalDateTime;

public class Post {

    public static final int MAX_CHARACTERS = 150;

    private String text;
    private LocalDateTime date;
    private User user;

    public Post(String text, User user) {
        this.text = text;
        this.date = LocalDateTime.now();
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
