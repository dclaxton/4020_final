/*
    Authors: Daniel Davis, Dalton Claxton, Peyton White
    Date: 7 May 2020
    Description: A simple implementation of an 'impossible' quiz
 */

package edu.apsu.csci.final_4020.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import edu.apsu.csci.final_4020.R;
import edu.apsu.csci.final_4020.classes.Alert;
import edu.apsu.csci.final_4020.classes.Question;
import edu.apsu.csci.final_4020.listeners.GoToActivityClosingPrevious;

public class QuizActivity extends AppCompatActivity {
    private Question question;
    private ArrayList<Question> questions;

    private Alert alert;
    private int difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Add background music
        difficulty = getIntent().getIntExtra("Difficulty", 0);
        questions = new ArrayList<>();
        startQuiz();

        findViewById(R.id.menu_button).setOnClickListener(new GoToActivityClosingPrevious(this, MenuActivity.class));
        findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((EditText) findViewById(R.id.answer_edit_text)).getText().toString().equals(question.getAnswer())) {
                    // Add notification / sound for correct answer

                    questions.add(question);
                    startQuiz();
                } else {
                    endQuiz(questions.size());
                }
            }
        });
    }

    public void startQuiz() {
        question = new Question(difficulty);

        // Results in infinite loop unless a new question is generated every time
        for (Question oldQuestion : questions) {
            if (oldQuestion.getID() == question.getID()) {
                //startQuiz();
            }
        }

        ((EditText) findViewById(R.id.answer_edit_text)).getText().clear();
        ((TextView) findViewById(R.id.question_header_tv)).setText(
                getString(R.string.question, questions.size() + 1));
        ((TextView) findViewById(R.id.question_tv)).setText(question.getQuestion());
    }

    public void endQuiz(int score) {
        // Add notification / sound for wrong answer

        alert = new Alert(this);
        alert.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == DialogInterface.BUTTON_POSITIVE) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            questions = new ArrayList<>();
                            startQuiz();
                        }
                    }, 500);
                }
            }
        });

        alert.backToMenu(this);
        // High score comes from DB
        alert.showScores(score, "From DB" );
    }

}
