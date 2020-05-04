/*
    Authors: Daniel Davis, Dalton Claxton, Peyton White
    Date: 7 May 2020
    Description: A simple implementation of an 'impossible' quiz
 */

package edu.apsu.csci.final_4020.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import edu.apsu.csci.final_4020.R;
import edu.apsu.csci.final_4020.listeners.GoToActivity;

public class MenuActivity extends AppCompatActivity {
    public static boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MenuActivity.isOpen = true;
        setContentView(R.layout.activity_main_menu);

        // Menu buttons
        findViewById(R.id.play_button).setOnClickListener(new GoToActivity(this, QuizActivity.class));
        findViewById(R.id.highscore_button).setOnClickListener(new GoToActivity(this, HighScoreActivity.class));
        findViewById(R.id.credits_button).setOnClickListener(new GoToActivity(this, CreditsActivity.class));
    }

    // Exit button
    public void onExitButton(final View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isOpen = false;
    }
}
