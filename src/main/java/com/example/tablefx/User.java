package com.example.tablefx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//класс для формирования данных, с помощью которых будет заполнена таблица(в данном случае два атрибута:id и text)
public class User {
    private int id;
    private String text;
    private ImageView picture;

    public User(int id, String text, ImageView picture) {
        this.id = id;
        this.text = text;
        this.picture=picture;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ImageView getPicture() {
        return picture;
    }

    public void setPicture(ImageView picture) {
        this.picture = picture;
    }
}

