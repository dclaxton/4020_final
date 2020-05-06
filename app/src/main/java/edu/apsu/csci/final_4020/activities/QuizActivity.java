/*
    Authors: Daniel Davis, Dalton Claxton, Peyton White
    Date: 7 May 2020
    Description: A simple implementation of an 'impossible' quiz
 */

package edu.apsu.csci.final_4020.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import edu.apsu.csci.final_4020.R;
import edu.apsu.csci.final_4020.classes.Alert;
import edu.apsu.csci.final_4020.classes.QueryJSON;
import edu.apsu.csci.final_4020.classes.Question;
import edu.apsu.csci.final_4020.classes.Sound;
import edu.apsu.csci.final_4020.listeners.GoToActivityClosingPrevious;

public class QuizActivity extends AppCompatActivity {
    private Question question;
    private ArrayList<Question> questions;
    private QueryJSON query;
    private Alert alert;

    private Sound sound;
    private String difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questions = new ArrayList<>();
        sound = new Sound(this);

        setDifficulty();
        startQuiz();

        findViewById(R.id.menu_button).setOnClickListener(new GoToActivityClosingPrevious(this, MenuActivity.class));
        findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((EditText) findViewById(R.id.answer_edit_text)).getText().toString()
                        .toUpperCase().equals(question.getAnswer().replaceAll("/[^-\\sa-zA-Z0-9 ]", "").toUpperCase())) {
                    sound.playSound(R.raw.correct_answer);

                    questions.add(question);
                    startQuiz();
                } else {
                    endQuiz(questions.size());
                }
            }
        });
    }

    private void setDifficulty() {
        int mode = getIntent().getIntExtra("Difficulty", 0);

        switch (mode) {
            case R.id.easy_rb:
                difficulty = "EASY";
                break;
            case R.id.normal_rb:
                difficulty = "NORMAL";
                break;
            case R.id.hard_rb:
                difficulty = "HARD";
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sound.stopMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sound.playMusic(R.raw.taking_quiz);
    }

    @Override
    protected void onStart() {
        super.onStart();
        sound.loadSoundPool();
    }

    @Override
    protected void onStop() {
        super.onStop();
        sound.releaseSound();
    }

    public void startQuiz() {
        query = new QueryJSON(getApplicationContext(), difficulty);
        query.execute();

        query.setCallback(new QueryJSON.JSONCallBack() {
            @Override
            public void onResponse(Question q) {
                if (q != null) {
                    switch (difficulty) {
                        case "EASY":
                            if (q.getDifficulty() > 300) {
                                startQuiz();
                            }
                            break;
                        case "NORMAL":
                            if (q.getDifficulty() < 400 || q.getDifficulty() > 600) {
                                startQuiz();
                            }
                            break;
                        case "HARD":
                            if (q.getDifficulty() < 700) {
                                startQuiz();
                            }
                            break;
                    }

                    question = q;

                    // Wipe the Edit Text, increment Question counter, display new Question, display new Category
                    ((EditText) findViewById(R.id.answer_edit_text)).getText().clear();
                    ((EditText) findViewById(R.id.answer_edit_text)).setHint(q.getAnswer().replaceAll("[^-\\s]", "_ "));
                    ((TextView) findViewById(R.id.question_header_tv)).setText(getString(R.string.question, questions.size() + 1));
                    ((TextView) findViewById(R.id.question_tv)).setText(q.getQuestion());
                    ((TextView) findViewById(R.id.category_tv)).setText(getString(R.string.category, q.getCategory()));
                }
            }
        });
    }

    public void endQuiz(int score) {
        sound.stopMusic();
        sound.playSound(R.raw.game_over);

        alert = new Alert(this);
        alert.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == DialogInterface.BUTTON_POSITIVE) {
                    questions = new ArrayList<>();
                    sound.playMusic(R.raw.taking_quiz);
                    startQuiz();
                }
            }
        });

        alert.backToMenu(this);
        // High score comes from DB
        alert.showScores(question.getAnswer().replaceAll("/[^-\\sa-zA-Z0-9 ]", ""), score, "0");
    }
}
