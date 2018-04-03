package com.example.makkhay.cameratranslate;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.gms.common.api.CommonStatusCodes;

public class HomeFragment extends Fragment implements View.OnClickListener{


    private static final String TAG = "Camera";
    private static final int RC_OCR_CAPTURE = 9003;
    ImageButton cameraButton, locationButton, micButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        cameraButton = (ImageButton) v.findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(this);
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    String text = data.getStringExtra(OcrCaptureActivity.TextBlockObject);
//                    statusMessage.setText(R.string.ocr_success);
//                    textValue.setText(text);
                    Log.d(TAG, "Text read: " + text);
                } else {
//                    statusMessage.setText(R.string.ocr_failure);
                    Log.d(TAG, "No Text captured, intent data is null");
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
        }

    }
}
