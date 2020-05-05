/*
    Authors: Daniel Davis, Dalton Claxton, Peyton White
    Date: 7 May 2020
    Description: A simple implementation of an 'impossible' quiz
 */

package edu.apsu.csci.final_4020.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DbDataSource {
    private SQLiteDatabase database;
    private MySqlLiteHelper databaseHelper;

    public DbDataSource(Context context) {
        databaseHelper = new MySqlLiteHelper(context);
    }

    private void open() {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    // Gets all Highscores
    public List<Integer> getAllHighscores(int whichGame) {
        open();

        List<Integer> highScores = new ArrayList<>();
        String[] columns = MySqlLiteHelper.HighscoreColumns.names(); //getWhichColumns(whichGame); //MySqlLiteHelper.HighscoreColumns.names();
        Cursor cursor = database.query(MySqlLiteHelper.DATA_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Integer highscore = cursorToHighscore(cursor, whichGame);
            highScores.add(highscore);
            cursor.moveToNext();

        }
        cursor.close();
        return highScores;
    }

    private String[] getWhichColumns(int whichGame) {
        String[] c;
        if (whichGame == 1) {
            c = new String[]{"easy"};
        } else if (whichGame == 2) {
            c = new String[]{"normal"};
        } else {
            c = new String[]{"hard"};
        }
        return c;
    }

    // Inserts highscore into DB
    public void insertHighscore(int whichGame, int scoreRecorded) {
        ContentValues contentValues = new ContentValues();
        if (whichGame == 1) {
            contentValues.put(MySqlLiteHelper.HighscoreColumns.easy.toString(), scoreRecorded);
        } else if (whichGame == 2) {
            contentValues.put(MySqlLiteHelper.HighscoreColumns.normal.toString(), scoreRecorded);

        } else {
            contentValues.put(MySqlLiteHelper.HighscoreColumns.hard.toString(), scoreRecorded);
        }

        open();
        database.insert(MySqlLiteHelper.DATA_TABLE, null, contentValues);
    }


    //going to need updating
    private Integer cursorToHighscore(Cursor cursor, int whichGame) {

        int highscore;

        //int scoreId = cursor.getInt(MySqlLiteHelper.HighscoreColumns.primary_key.ordinal());
        if (whichGame == 1) {
            highscore = cursor.getInt(MySqlLiteHelper.HighscoreColumns.easy.ordinal());
        } else if (whichGame == 2) {
            highscore = cursor.getInt(MySqlLiteHelper.HighscoreColumns.normal.ordinal());
        } else {
            highscore = cursor.getInt(MySqlLiteHelper.HighscoreColumns.hard.ordinal());
        }

        Integer highScore = new Integer(highscore);

        //String dateStr = cursor.getString(MySqlLiteHelper.HighscoreColumns.date_created.ordinal());

        return highScore;
    }

    //this is for the endgame alert dialog
    public String getHighscore(int whichGame) {
        List<Integer> highScores = getAllHighscores(whichGame);
        int highScore = highScores.get(0);

        for (int i : highScores) {
            if (i > highScore) {
                highScore = i;
            }

        }

        return String.valueOf(highScore);
    }


}








