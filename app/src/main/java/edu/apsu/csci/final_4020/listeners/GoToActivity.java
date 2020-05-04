/*
    Authors: Daniel Davis, Dalton Claxton, Peyton White
    Date: 7 May 2020
    Description: A simple implementation of an 'impossible' quiz
 */

package edu.apsu.csci.final_4020.listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class GoToActivity implements View.OnClickListener {
    Activity fromActivity;
    private Class toActivityClass;
    private int intExtra;

    public GoToActivity(Activity fromActivity, Class<? extends Activity> toActivityClass) {
        this.fromActivity = fromActivity;
        this.toActivityClass = toActivityClass;
    }

    public GoToActivity(Activity fromActivity, Class<? extends Activity> toActivityClass, int intExtra) {
        this.fromActivity = fromActivity;
        this.toActivityClass = toActivityClass;
        this.intExtra = intExtra;
    }

    // Passes an intent from one activity to another
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(fromActivity, toActivityClass);
        intent.putExtra("Difficulty", intExtra);
        fromActivity.startActivity(intent);
    }
}
