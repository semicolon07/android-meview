package com.unigainfo.android.meview.sample.model;

/**
 * Created by Semicolon07 on 10/8/2016 AD.
 */

public class Student {
    private String name;
    private String email;

    public Student(){

    }

    public Student(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
