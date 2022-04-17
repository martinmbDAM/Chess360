package com.example.chess360.vo;

import com.example.chess360.dao.Dao;

public class User {

    private int id;
    private String username;
    private String email;
    private String password;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.id = Dao.generateID_User();
    }

    public User(String user){
        this.username = user;
        this.email = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o){

        boolean output = false;

        if (o instanceof User){
            User myUser = (User) o;
            output = this.username.equals(myUser.getUsername());
        }

        return output;
    }
}
