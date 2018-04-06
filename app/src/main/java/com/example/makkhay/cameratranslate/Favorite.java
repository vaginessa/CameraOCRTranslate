package com.example.makkhay.cameratranslate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
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

import com.example.makkhay.cameratranslate.Util.ItemtouchHelperCallback;
import com.example.makkhay.cameratranslate.Util.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class Favorite extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter adapter;
    private int color = 0;
    private List<String> data;
    private String insertData;
    private boolean loading;
    private TextView favText;

    public Favorite(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_favorite, container, false);
        favText = v.findViewById(R.id.tv_recycler_item_1);

        initData();
        initView(v);
//        String value = getArguments().getString("YourKey");
//        if (value != null){
//            //Retrieve the value
//
//        favText.setText(value);
//    }





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


    private void initData() {
        data = new ArrayList<>();
//        for (int i = 1; i <= 6; i++) {
//            data.add(i + "");
//        }


        insertData = "0";
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

        adapter = new RecyclerViewAdapter(getContext());
        mRecyclerView.setAdapter(adapter);
        adapter.setItems(data);
        adapter.addFooter();



        ItemTouchHelper.Callback callback = new ItemtouchHelperCallback(adapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);


    }




    private int getScreenWidthDp() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return (int) (displayMetrics.widthPixels / displayMetrics.density);
    }

}
