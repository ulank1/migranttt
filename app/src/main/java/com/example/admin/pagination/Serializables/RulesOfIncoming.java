package com.example.admin.pagination.Serializables;

import java.io.Serializable;

/**
 * Created by Admin on 25.07.2016.
 */
public class RulesOfIncoming implements Serializable {
    String image,title,text;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }
}
