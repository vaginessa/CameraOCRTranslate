package com.example.makkhay.cameratranslate.Util;

import java.io.Serializable;

public class CardModel implements Serializable {
    private String title;
    private String image;
    private String meaning;


    public CardModel(){

    }

    public CardModel(String title, String meaning,String image) {
        this.title = title;
        this.image = image;
        this.meaning = meaning;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
