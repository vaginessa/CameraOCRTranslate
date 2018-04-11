package com.example.makkhay.cameratranslate.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Dictionary implements Serializable{
    public List<CardModel> wordsList = new ArrayList<>();


    public List<CardModel> getWordsList() {
        return wordsList;
    }

    public void setWordsList(List<CardModel> wordsList) {
        this.wordsList = wordsList;
    }
}
