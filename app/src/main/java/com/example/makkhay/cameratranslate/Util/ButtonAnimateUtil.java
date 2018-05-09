package com.example.makkhay.cameratranslate.Util;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.widget.ImageView;

/**
 * Helper class to animate button onClick
 */



public final class ButtonAnimateUtil {


    public static void animateButton(View v) {
        Animator scale = ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 1, 1.3f, 1),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1, 1.3f, 1)
        );
        scale.setDuration(1000);
        scale.start();
    }





}
