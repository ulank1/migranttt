package com.example.admin.pagination.Serializables;

/**
 * Created by Admin on 20.07.2016.
 */
public class VseAndUzery {
    public VseAndUzery(){

    }
    String username,title,text,id,category;

    public String getId() {
        return id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
