package com.example.chess360.vo;

public class CompleteAchievement {

    Achievement achievement;
    User user;

    public CompleteAchievement(Achievement achievement, User user) {
        this.achievement = achievement;
        this.user = user;
    }

    public Achievement getAchievement() {
        return achievement;
    }

    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
