package com.example.admin.pagination.Serializables;

import java.io.Serializable;

/**
 * Created by Admin on 20.07.2016.
 */
public class Hotline  implements Serializable{
    String phoneNumber,description,title;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
