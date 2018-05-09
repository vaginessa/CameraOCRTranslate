package com.example.makkhay.cameratranslate.Util;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.makkhay.cameratranslate.R;
import com.huxq17.swipecardsview.BaseCardAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CardAdapter extends BaseCardAdapter {

    private List<CardModel> modelList;
    private Context context;

    public CardAdapter(List<CardModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public int getCardLayoutId() {
        return R.layout.card_item;
    }

    @Override
    public void onBindData(int position, View cardview) {

        if (modelList == null || modelList.size() == 0) {
            return;
        }
        ImageView imageView = cardview.findViewById(R.id.cardImage);
        TextView textView = cardview.findViewById(R.id.cardWord);
        TextView textViewMeaning = cardview.findViewById(R.id.cardMeaning);

        CardModel model = modelList.get(position);
        textView.setText(model.getTitle());
        textViewMeaning.setText(model.getMeaning());
        Picasso.with(context).load(model.getImage()).into(imageView);


    }
}
