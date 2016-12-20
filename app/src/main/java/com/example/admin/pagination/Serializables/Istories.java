package com.example.admin.pagination.Serializables;

import java.io.Serializable;

/**
 * Created by Admin on 11.07.2016.
 */
public class Istories implements Serializable {
    public Istories(){

    }
    String text,nickName,image;
    int id_json;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNickName() {
        return nickName;
    }

    public String getText() {
        return text;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId_json() {
        return id_json;
    }

    public void setId_json(int id_json) {
        this.id_json = id_json;
    }
}
