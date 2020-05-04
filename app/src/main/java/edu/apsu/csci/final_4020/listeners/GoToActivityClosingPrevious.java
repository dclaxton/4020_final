package edu.apsu.csci.final_4020.listeners;

import android.app.Activity;
import android.view.View;

import edu.apsu.csci.final_4020.activities.MenuActivity;

public class GoToActivityClosingPrevious extends GoToActivity {
    public GoToActivityClosingPrevious(Activity fromActivity, Class<? extends Activity> toActivityClass) {
        super(fromActivity, toActivityClass);
    }

    // Prevents a menu from going back to the previous activity when 'Exit' is pressed
    @Override
    public void onClick(View v) {
        if (!MenuActivity.isOpen) {
            super.onClick(v);
        }
        fromActivity.finish();
    }
}
