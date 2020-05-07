/*
    Authors: Daniel Davis, Dalton Claxton, Peyton White
    Date: 7 May 2020
    Description: A simple implementation of an 'impossible' quiz
 */

package edu.apsu.csci.final_4020.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import edu.apsu.csci.final_4020.R;
import edu.apsu.csci.final_4020.db.DbDataSource;
import edu.apsu.csci.final_4020.listeners.GoToActivityClosingPrevious;

public class HighScoreActivity extends AppCompatActivity {

    private DbDataSource dataSource;
    int whichDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_highscores);

        whichDifficulty = 1;
        dataSource = new DbDataSource(this);
        findViewById(R.id.menu_button).setOnClickListener(new GoToActivityClosingPrevious(this, MenuActivity.class));

        findViewById(R.id.show_highscore_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDifficulty();
                List<Integer> highscores = dataSource.getTopHighscores(whichDifficulty);

                TextView tv = findViewById(R.id.highscore_tv);
                tv.setText("");
                for(int i : highscores)
                {
                    tv.append(i + "\n");
                }
            }
        });





    }


    private void setDifficulty() {
        RadioGroup rg = findViewById(R.id.difficulty_rg);
        int mode = rg.getCheckedRadioButtonId();

        switch (mode) {
            case R.id.easy_highscore_rb:
                whichDifficulty = 1;
                break;
            case R.id.normal_highscore_rb:
                whichDifficulty = 2;
                break;
            case R.id.hard__highscore_rb:
                whichDifficulty = 3;
                break;
            default:
                break;
        }
    }
}
