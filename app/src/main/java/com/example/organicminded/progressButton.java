package com.example.organicminded;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

public class progressButton {

    private CardView cardView;
    private ProgressBar progressBar;
    private TextView textView;
    private ConstraintLayout layout;
    private String activeName;
    private String finishName;

    Animation fade_in;

    progressButton(Context context, View view, int defName,int color) {
        fade_in = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        cardView = view.findViewById(R.id.cardView);
        layout = view.findViewById(R.id.constrainLayout);
        progressBar = view.findViewById(R.id.progressBar);
        textView = view.findViewById(R.id.buttonTextView);
        textView.setText(defName);
        layout.setBackgroundColor(cardView.getResources().getColor(color));

    }


    public void buttonRestart(int defName) {

        progressBar.setAnimation(fade_in);
        progressBar.setVisibility(View.GONE);
        textView.setAnimation(fade_in);
        textView.setText(defName);
        layout.setBackgroundColor(cardView.getResources().getColor(R.color.Button));
    }

    public void buttonActivated(int actName) {

        progressBar.setAnimation(fade_in);
        progressBar.setVisibility(View.VISIBLE);
        textView.setAnimation(fade_in);
        textView.setText(actName);

    }

    public void buttonError(int ErrorName) {

        progressBar.setAnimation(fade_in);
        layout.setBackgroundColor(cardView.getResources().getColor(R.color.RED));
        progressBar.setVisibility(View.GONE);
        textView.setAnimation(fade_in);
        textView.setText(ErrorName);
    }

    public void buttonFinished(int finName) {

        progressBar.setAnimation(fade_in);
        layout.setBackgroundColor(cardView.getResources().getColor(R.color.Green));
        progressBar.setVisibility(View.GONE);
        textView.setAnimation(fade_in);
        textView.setText(finName);
    }

//    public void clickOn(boolean activeMode,
//                        final boolean finishMode,
//                        String activeName,
//                        int activDelay,
//                        final String finishName,
//                        final int finishDelay) {
//        if (activeMode) {
//            buttonActivated(activeName);
//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    if (finishMode) {
//                        buttonFinished(finishName);
//                        Handler handler1 = new Handler();
//                        handler1.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//
//                            }
//                        }, finishDelay);
//                    }
//
//                }
//            }, activDelay);
//        }
//
//
//}

}
