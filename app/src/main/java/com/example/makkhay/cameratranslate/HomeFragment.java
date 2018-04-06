package com.example.makkhay.cameratranslate;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makkhay.cameratranslate.Util.ButtonAnimateUtil;
import com.google.android.gms.common.api.CommonStatusCodes;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment implements View.OnClickListener{


    private static final String TAG = "Camera";
    private static final int RC_OCR_CAPTURE = 9003;
    private ImageButton cameraButton, locationButton, micButton;
    private Button clearButton, favButton, shareButton;
    private EditText cameraText;
    private TextView outputTV;
    String data = "lado kha mug randiko ban";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        cameraButton =  v.findViewById(R.id.cameraButton);
        cameraText = v.findViewById(R.id.cameraEditText);
        clearButton = v.findViewById(R.id.clearButton);
        favButton = v.findViewById(R.id.favButton);
        shareButton=v.findViewById(R.id.shareButton);
        outputTV = v.findViewById(R.id.outputTV);


        cameraButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        favButton.setOnClickListener(this);
        shareButton.setOnClickListener(this);





        //get the spinner from the xml.
        final Spinner dropdown = v.findViewById(R.id.spinner);
        //create a list of items for the spinner.
        final String[] items = new String[]{"Spanish", "French"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = dropdown.getSelectedItem().toString();

                Toast.makeText(getContext(),"You have selected :  " + selected ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    String text = data.getStringExtra(OcrCaptureActivity.TextBlockObject);
//                    statusMessage.setText(R.string.ocr_success);
                    cameraText.setText(text);
                    Log.d(TAG, "Text read: " + text);
                } else {
                    Log.d(TAG, "No Text captured, intent data is null");
                    Toast.makeText(getContext(),"No text captured, intent data is null",Toast.LENGTH_LONG).show();
                }
            } else {
//                statusMessage.setText(String.format(getString(R.string.ocr_error),
//                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.cameraButton:
                // launch Ocr capture activity.
                Intent intent = new Intent(getActivity().getApplicationContext(), OcrCaptureActivity.class);
                intent.putExtra(OcrCaptureActivity.AutoFocus, true);
//                intent.putExtra(OcrCaptureActivity.UseFlash);

                startActivityForResult(intent, RC_OCR_CAPTURE);
                break;

            case R.id.clearButton:
                //clear text
                cameraText.setText("");
                    ButtonAnimateUtil.animateButton(v);
                break;


            case R.id.shareButton:
                ButtonAnimateUtil.animateButton(v);

                break;

            case R.id.favButton:
                favButton.setBackgroundResource(R.drawable.ic_favorite_red_12dp);
                ButtonAnimateUtil.animateButton(v);
                //Put the value
                HomeFragment ldf = new HomeFragment ();
                Bundle args = new Bundle();
                args.putString("YourKey", "randi ko ban mug bhalu");
                ldf.setArguments(args);

                //Inflate the fragment
                getFragmentManager().beginTransaction().add(R.id.container, ldf).commit();
                break;
        }

    }




}
