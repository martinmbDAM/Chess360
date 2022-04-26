package com.example.chess360.vo;

import com.example.chess360.dao.Dao;

public class Achievement {

    private int id;
    private String name;
    private String description;

    public Achievement(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = Dao.generateID_Achievement();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
