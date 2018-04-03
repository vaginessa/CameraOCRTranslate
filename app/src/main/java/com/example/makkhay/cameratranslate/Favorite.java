package com.example.makkhay.cameratranslate;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Favorite extends Fragment {


    public Favorite(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
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
}
