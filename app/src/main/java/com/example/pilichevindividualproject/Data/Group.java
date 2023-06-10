package com.example.pilichevindividualproject.Data;

import java.io.Serializable;

public class Group implements Serializable {

    private int id;
    private String name;
    private int number;

    public Group(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public Group(){}

    @Override
    public String toString() {
        return name +" "+ number;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
