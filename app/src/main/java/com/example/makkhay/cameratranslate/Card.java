package com.example.makkhay.cameratranslate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.makkhay.cameratranslate.Util.CardAdapter;
import com.example.makkhay.cameratranslate.Util.CardModel;
import com.example.makkhay.cameratranslate.Util.Dictionary;
import com.google.gson.Gson;
import com.huxq17.swipecardsview.SwipeCardsView;

import java.util.ArrayList;
import java.util.List;

public class Card extends AppCompatActivity {
    private SwipeCardsView swipeCardsView;
    private List<CardModel> modelList = new ArrayList<>();

    private SharedPreferences pref;

    String loadsPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        swipeCardsView =  findViewById(R.id.swipCardsView);
        swipeCardsView.retainLastCard(true);
        swipeCardsView.enableSwipe(true);
        loadsPosition = getIntent().getStringExtra("loadsPosition");

         pref = getSharedPreferences("lado", MODE_PRIVATE);

//        List<String> list = new ArrayList<>();
        Gson gson = new Gson();
        Dictionary dictionary = gson.fromJson(pref.getString("word", ""), Dictionary.class);
        if (dictionary != null)
            for (CardModel words : dictionary.getWordsList()) {
                modelList.add( new CardModel(words.getMeaning(),words.getTitle(), "https://i.imgur.com/1Ls9n51.png"));
                CardAdapter cardAdapter = new CardAdapter(modelList,this);
                swipeCardsView.setAdapter(cardAdapter);
            }


//        getData();



    }

//    private void getData() {
//        modelList.add(new CardModel(loadsPosition, "https://avatars.githubusercontent.com/makkhay"));
////        modelList.add(new CardModel("hello", "https://avatars3.githubusercontent.com/u/36139326?s=460&v=4 "));
////        modelList.add(new CardModel("makk", "https://scontent-lax3-1.xx.fbcdn.net/v/t1.0-1/c44.0.320.320/p320x320/20914645_1579156895438534_1027285013181250707_n.jpg?oh=0ff1b3c4c8bc8e00edb84cecd660d0fe&oe=5B1C897A "));
////        modelList.add(new CardModel("makk", "http://thomaszhu.com/resources/thomas_avatar.jpeg"));
////        modelList.add(new CardModel("makk", "http://thomaszhu.com/resources/thomas_avatar.jpeg"));
////        modelList.add(new CardModel("makk", "https://avatars2.githubusercontent.com/u/7840686?s=400&v=4"));
////        modelList.add(new CardModel("makk", "https://avatars1.githubusercontent.com/u/19352928?s=460&v=4"));
//        CardAdapter cardAdapter = new CardAdapter(modelList,this);
//        swipeCardsView.setAdapter(cardAdapter);
//
//    }


//    private void deleteCard(){
//        Gson gson = new Gson();
//        Dictionary dictionary = gson.fromJson(pref.getString("word", ""), Dictionary.class);
//        for (CardModel words : dictionary.getWordsList()) {
//
//                dictionary.getWordsList().remove(words);
//                break;
//            }
//        }
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString("word", new Gson().toJson(dictionary));
//        editor.commit();
//
//        adapter.notifyDataSetChanged();
//    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // for back button
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
