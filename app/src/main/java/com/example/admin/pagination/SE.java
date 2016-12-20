package com.example.admin.pagination;

import java.io.Serializable;

public class SE implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String emailId;

    public SE() {

    }
    public SE(String name, String emailId) {
        this.name = name;
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }


}