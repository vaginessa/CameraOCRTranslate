package com.example.makkhay.cameratranslate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makkhay.cameratranslate.Util.CardModel;
import com.example.makkhay.cameratranslate.Util.Dictionary;
import com.example.makkhay.cameratranslate.Util.ItemtouchHelperCallback;
import com.example.makkhay.cameratranslate.Util.RecyclerViewAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class Favorite extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter adapter;
    private int color = 0;
    private List<String> data;
    private String insertData;
    private boolean loading;
    private TextView favText;

    TextView txtData;

    List<String> list;



    public Favorite(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_favorite, container, false);
        favText = v.findViewById(R.id.tv_recycler_item_1);



        SharedPreferences pref = getContext().getSharedPreferences("lado", MODE_PRIVATE);

        list = new ArrayList<>();
        Gson gson = new Gson();
        Dictionary dictionary = gson.fromJson(pref.getString("word", ""), Dictionary.class);
        if (dictionary != null)
            for (CardModel words : dictionary.getWordsList()) {
                list.add(words.getTitle() + "\n \n\n" + words.getMeaning());
            }



        System.out.println(list);

        initView(v);




        // Inflate the layout for this fragment
        return  v;
    }




    @Override
    public void onAttach(Context context) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        super.onDetach();

    }




    private void initView(View v) {
        mRecyclerView = v.findViewById(R.id.recycler_view_recycler_view);

        if (getScreenWidthDp() >= 1200) {
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        } else if (getScreenWidthDp() >= 800) {
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        } else {
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(linearLayoutManager);
        }

        adapter = new RecyclerViewAdapter(getContext(),list);
        mRecyclerView.setAdapter(adapter);
//        adapter.setItems(list);
//        adapter.addFooter();




        ItemTouchHelper.Callback callback = new ItemtouchHelperCallback(adapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "card is clicked",Toast.LENGTH_SHORT).show();
            }
        });


    }




    private int getScreenWidthDp() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return (int) (displayMetrics.widthPixels / displayMetrics.density);
    }

}
