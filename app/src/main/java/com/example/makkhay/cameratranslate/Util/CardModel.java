package com.example.makkhay.cameratranslate.Util;


import java.io.Serializable;

/**
 * model/helper class for CardActivity
 */

public class CardModel implements Serializable {
    private String title;
    private int image;
    private String meaning;


    public CardModel() {

    }

    public CardModel(String meaning, String title, int image) {
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


    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
