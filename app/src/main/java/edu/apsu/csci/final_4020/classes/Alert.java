/*
    Authors: Daniel Davis, Dalton Claxton, Peyton White
    Date: 7 May 2020
    Description: A simple implementation of an 'impossible' quiz
 */

package edu.apsu.csci.final_4020.classes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Alert {
    private AlertDialog.Builder aBuilder;
    private AlertDialog ad;

    public Alert(Context c) {
        aBuilder = new AlertDialog.Builder(c);
        ad = null;
    }

    public void showScores(String answer, int score, String highScore) {
        aBuilder.setTitle("Game Over");
        aBuilder.setMessage("Answer: " + answer + "\nScore: " + score + "\nHigh Score: " + highScore);

        buildDialog();
    }

    private void buildDialog() {
        ad = aBuilder.create();
        ad.setCancelable(false);
        ad.setCanceledOnTouchOutside(false);

        ad.show();
    }

    public void setPositiveButton(String s, DialogInterface.OnClickListener dcl) {
        aBuilder.setPositiveButton(s, dcl);
    }

    private void setNegativeButton(String s, DialogInterface.OnClickListener dcl) {
        aBuilder.setNegativeButton(s, dcl);
    }

    public void backToMenu(final Activity a) {
        setNegativeButton("Menu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == DialogInterface.BUTTON_NEGATIVE) {
                    a.finish();
                }
            }
        });
    }
}
