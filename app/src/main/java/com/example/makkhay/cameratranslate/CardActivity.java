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


/**
 * This class is used to display the favorite words in a cardview. Users can swipe left or right to view the new word
 * <p>
 * This class uses SwipCardsView library to display cards
 */
public class CardActivity extends AppCompatActivity {
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


        swipeCardsView = findViewById(R.id.swipCardsView);
        swipeCardsView.retainLastCard(true);
        swipeCardsView.enableSwipe(true);
        loadsPosition = getIntent().getStringExtra("loadsPosition");

        pref = getSharedPreferences("lado", MODE_PRIVATE);

        Gson gson = new Gson();
        Dictionary dictionary = gson.fromJson(pref.getString("word", ""), Dictionary.class);
        if (dictionary != null)
            for (CardModel words : dictionary.getWordsList()) {
                modelList.add(new CardModel(words.getMeaning(), words.getTitle(), R.drawable.bg));
                CardAdapter cardAdapter = new CardAdapter(modelList, this);
                swipeCardsView.setAdapter(cardAdapter);
            }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
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
