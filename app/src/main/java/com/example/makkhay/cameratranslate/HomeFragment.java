package com.example.makkhay.cameratranslate;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makkhay.cameratranslate.RetroFit.api.model.WordHelper;
import com.example.makkhay.cameratranslate.RetroFit.api.service.RetroFitHelper;
import com.example.makkhay.cameratranslate.Util.Application;
import com.example.makkhay.cameratranslate.Util.ButtonAnimateUtil;
import com.example.makkhay.cameratranslate.Util.CardModel;
import com.example.makkhay.cameratranslate.Util.Dictionary;

import com.example.makkhay.cameratranslate.Util.Prefs;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.gson.Gson;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.functions.Action1;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment implements View.OnClickListener {


    private static final String TAG = "Camera";
    private static final int RC_OCR_CAPTURE = 9003;
    private final int REQ_CODE = 100;
    private ImageButton cameraButton, locationButton, micButton;
    private Button clearButton, favButton, shareButton, translate;
    private EditText cameraText;
    private TextView outputTV;
    private final static String language1 = "en-es";
    private final static String language2 = "en-fr";
    private  String languageSelector;
    private ShowcaseView showcaseView;
    private int counter = 0;
    private Prefs prefs;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        cameraButton = v.findViewById(R.id.cameraButton);
        cameraText = v.findViewById(R.id.cameraEditText);
        clearButton = v.findViewById(R.id.clearButton);
        shareButton = v.findViewById(R.id.shareButton);
        outputTV = v.findViewById(R.id.outputTV);
        favButton = v.findViewById(R.id.favButton);
        micButton = v.findViewById(R.id.micButton);
        locationButton = v.findViewById(R.id.locationButton);
        translate = v.findViewById(R.id.translate);



        cameraButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        shareButton.setOnClickListener(this);
        favButton.setOnClickListener(this);
        micButton.setOnClickListener(this);
        locationButton.setOnClickListener(this);
        translate.setOnClickListener(this);




        outputTV.setText("This is just a test");

        cameraText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacks(input_finish_checker);

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    last_text_edit = System.currentTimeMillis();
                    handler.postDelayed(input_finish_checker, delay);
                } else {

                }
            }
        });


        SharedPreferences settings= getContext().getSharedPreferences("prefs",0);
        boolean firstRun=settings.getBoolean("firstRun",false);
        if(!firstRun)//if running for first time
        //Splash will load for first time
        {
            showcaseView = new ShowcaseView.Builder(getActivity())
                    .setTarget(new ViewTarget(v.findViewById(R.id.cameraButton)))
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            animateShowCase();
                        }
                    })
                    .setStyle(R.style.CustomShowcaseTheme3)
                    .build();
            showcaseView.setButtonText(getString(R.string.next));
            showcaseView.setContentTitle("Camera");
            showcaseView.setContentText("Click this button to open your camera and scan");
            SharedPreferences.Editor editor=settings.edit();
            editor.putBoolean("firstRun",true);
            editor.apply();

        }





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
                 if(dropdown.getSelectedItemPosition() == 0){
                     languageSelector = language1;
                 } else {
                     languageSelector =language2;
                 }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    String text = data.getStringExtra(OcrCaptureActivity.TextBlockObject);
//                    statusMessage.setText(R.string.ocr_success);
                    cameraText.setText(text);
                    Log.d(TAG, "Text read: " + text);
                } else {
                    Log.d(TAG, "No Text captured, intent data is null");
                    Toast.makeText(getContext(), "No text captured, intent data is null", Toast.LENGTH_LONG).show();
                }
            } else {
//                statusMessage.setText(String.format(getString(R.string.ocr_error),
//                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        switch (requestCode) {
            case REQ_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    cameraText.setText(result.get(0));

                }
                break;

        }

    }


    @Override
    public void onClick(View v) {
//        animateShowCase();


        switch (v.getId()) {
            case R.id.cameraButton:
                // launch Ocr capture activity.
                Intent intent = new Intent(getActivity().getApplicationContext(), OcrCaptureActivity.class);
                intent.putExtra(OcrCaptureActivity.AutoFocus, true);
                startActivityForResult(intent, RC_OCR_CAPTURE);
                break;

            case R.id.clearButton:
                //clear text
                cameraText.setText("");
                ButtonAnimateUtil.animateButton(v);
                break;

            case R.id.translate:

                ButtonAnimateUtil.animateButton(v);
                translateLanguage(languageSelector);


                break;

            case R.id.shareButton:
                ButtonAnimateUtil.animateButton(v);
                String shareBody = outputTV.getText().toString();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Camera Translate"));

                break;

            case R.id.favButton:
                favButton.setBackgroundResource(R.drawable.ic_favorite_red_12dp);
                ButtonAnimateUtil.animateButton(v);
                saveToFavorite();
                break;


            case R.id.micButton:
                ButtonAnimateUtil.animateButton(v);
                speechToText();
                break;
            case R.id.locationButton:
                Toast.makeText(getContext(), "You are in: " +
                        getCountryBasedOnSimCardOrNetwork(getContext()), Toast.LENGTH_SHORT).show();

                break;
        }




    }

    private void saveToFavorite() {
        String cardTitle = cameraText.getText().toString();
        String cardMeaning = outputTV.getText().toString();

        SharedPreferences pref = getContext().getSharedPreferences("lado", Context.MODE_PRIVATE);
        CardModel words = new CardModel();
        words.setTitle(cardTitle);
        words.setMeaning(cardMeaning);

        List<CardModel> wordsList = new ArrayList<>();

        Gson gson = new Gson();
        Dictionary dictionary = gson.fromJson(pref.getString("word", ""), Dictionary.class);
        if (dictionary != null && dictionary.getWordsList() != null) {
            wordsList.addAll(dictionary.getWordsList());
            dictionary.setWordsList(wordsList);
            wordsList.add(words);
        } else {
            dictionary = new Dictionary();
            wordsList.add(words);
            dictionary.setWordsList(wordsList);
        }
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("word", new Gson().toJson(dictionary));
        editor.apply();


    }

    private void speechToText() {
        // passing an intent of recognize speech
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // Gets the default language. In our case, english is selected.
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Up");

        try {
            startActivityForResult(i, REQ_CODE);
        } catch (ActivityNotFoundException e) {
            Log.d(TAG, "speechToText: speech not supported");
        }
    }

    private static String getCountryBasedOnSimCardOrNetwork(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
                return simCountry.toLowerCase(Locale.US);
            } else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
                    return networkCountry.toLowerCase(Locale.US);
                }
            }
        } catch (Exception e) {
        }
        return null;
    }


    private void translateLanguage( String language) {
        String url1 = "https://translate.yandex.net/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(url1)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        RetroFitHelper client = retrofit.create(RetroFitHelper.class);
        String englishToNepali = cameraText.getText().toString();

        Call<WordHelper> call = client.findMeaning(language, englishToNepali);
        call.enqueue(new Callback<WordHelper>() {


            @Override
            public void onResponse(Call<WordHelper> call, Response<WordHelper> response) {
                List<String> res = response.body().getText();
                String output = res.toString();
                output = output.replace("[", "");
                String finalText = output;
                finalText = finalText.replace("]","");
                outputTV.setText(finalText);
            }

            @Override
            public void onFailure(Call<WordHelper> call, Throwable t) {

                Toast.makeText(getContext(),"Failed, something happened!!",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setAlpha(float alpha, View... views) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            for (View view : views) {
                view.setAlpha(alpha);
            }
        }





    }


//    private void launchShowCaseView(View v){
//        showcaseView = new ShowcaseView.Builder(getActivity())
//                .setTarget(new ViewTarget(v.findViewById(R.id.cameraButton)))
//                .setOnClickListener(this)
//                .setStyle(R.style.CustomShowcaseTheme3)
//                .build();
//        showcaseView.setButtonText(getString(R.string.next));
//        showcaseView.setContentTitle("Camera");
//        showcaseView.setContentText("Click this button to open your camera and scan");
//
//    }


    private void animateShowCase(){
        switch (counter) {
            case 0:
                showcaseView.setShowcase(new ViewTarget(micButton), true);
                showcaseView.setContentTitle("Mic use");
                showcaseView.setContentText("Use Your Mic to input the text that you want to convert");
                break;

            case 1:
                showcaseView.setTarget(Target.NONE);
                showcaseView.setContentTitle("All Set");
                showcaseView.setContentText("Enjoy the App ");
                showcaseView.setButtonText("close");
                setAlpha(0.4f, cameraButton, micButton, translate);
                break;

            case 2:
                showcaseView.hide();
                setAlpha(1.0f, cameraButton, micButton, translate);
                break;
        }
        counter++;


    }

    long delay = 1000; // 1 seconds after user stops typing
    long last_text_edit = 0;

    Handler handler = new Handler();

    private Runnable input_finish_checker = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                translateLanguage(languageSelector);
            }
        }
    };

}