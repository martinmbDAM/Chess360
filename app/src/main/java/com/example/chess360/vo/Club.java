package com.example.chess360.vo;

public class Club extends User {

    private String name;
    private String location;
    private String phone;

    public Club (String name, String username, String location, String phone, String email, String password){
        super(username, email,password);
        this.name = name;
        this.location = location;
        this.phone = phone;
    }

    public Club(String name){
        super(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
