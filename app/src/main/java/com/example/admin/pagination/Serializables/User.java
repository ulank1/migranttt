package com.example.admin.pagination.Serializables;

import java.io.Serializable;

/**
 * Created by Admin on 09.08.2016.
 */
public class User implements Serializable {
    String email,username,password;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
