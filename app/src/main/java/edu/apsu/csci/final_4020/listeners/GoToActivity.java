/*
    Authors: Daniel Davis, Dalton Claxton, Peyton White
    Date: 7 May 2020
    Description: A simple implementation of an 'impossible' quiz
 */

package edu.apsu.csci.final_4020.listeners;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import edu.apsu.csci.final_4020.R;

public class GoToActivity implements View.OnClickListener {
    Activity fromActivity;
    private Class toActivityClass;
    private RadioGroup rg;

    public GoToActivity(Activity fromActivity, Class<? extends Activity> toActivityClass) {
        this.fromActivity = fromActivity;
        this.toActivityClass = toActivityClass;
        this.rg = null;
    }

    public GoToActivity(Activity fromActivity, Class<? extends Activity> toActivityClass, RadioGroup rg) {
        this.fromActivity = fromActivity;
        this.toActivityClass = toActivityClass;
        this.rg = rg;
    }

    // Passes an intent from one activity to another
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(fromActivity, toActivityClass);
        if (rg != null) {
            intent.putExtra("Difficulty", rg.getCheckedRadioButtonId());
        }
        fromActivity.startActivity(intent);
    }
}
