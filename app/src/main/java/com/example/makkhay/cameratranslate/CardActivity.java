package com.example.makkhay.cameratranslate;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

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
    ImageView leftButton, rightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);


        swipeCardsView = findViewById(R.id.swipCardsView);
        swipeCardsView.retainLastCard(true);
        swipeCardsView.enableSwipe(true);

        swipeCardsView.setCardsSlideListener(new SwipeCardsView.CardsSlideListener() {
            @Override
            public void onShow(int index) {
            }

            @Override
            public void onCardVanish(int index, SwipeCardsView.SlideType type) {
                switch (type) {
                    case LEFT:
                        animateLeft();
                        break;
                    case RIGHT:
                        animateRight();
                        break;
                }
            }

            @Override
            public void onItemClick(View cardImageView, int index) {
            }
        });



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

    private void animateLeft(){
        final Animation leftAnim = AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);
        leftButton.startAnimation(leftAnim);

        leftAnim.setAnimationListener(new Animation.AnimationListener() {


            @Override
            public void onAnimationStart(Animation animation) {

              leftButton.setImageResource(R.drawable.right_wink);
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                leftButton.setImageResource(R.drawable.baseline_arrow_back_ios_black_48dp);

            }
            @Override
            public void onAnimationRepeat(Animation animation) { }
        });


    }


    private void animateRight(){
        final Animation rightAnim = AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate_right);
        rightButton.startAnimation(rightAnim);

        rightAnim.setAnimationListener(new Animation.AnimationListener() {


            @Override
            public void onAnimationStart(Animation animation) {

                rightButton.setImageResource(R.drawable.left_48dp);
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                rightButton.setImageResource(R.drawable.baseline_arrow_forward_ios_black_48dp);

            }
            @Override
            public void onAnimationRepeat(Animation animation) { }
        });


    }



}
