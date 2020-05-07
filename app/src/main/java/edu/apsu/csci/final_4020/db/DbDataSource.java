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
    public List<Integer> getAllHighscores(int whichDifficulty) {
        open();

        String columnName;
        if(whichDifficulty == 1)
        {
            columnName = "easy";
        }
        else if (whichDifficulty == 2)
        {
            columnName = "normal";
        }
        else
        {
            columnName = "hard";
        }
        List<Integer> highScores = new ArrayList<>();
        String[] columns = MySqlLiteHelper.HighscoreColumns.names();
        Cursor cursor = database.query(MySqlLiteHelper.DATA_TABLE, columns, null, null, null, null, columnName + " DESC");
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Integer highscore = cursorToHighscore(cursor, whichDifficulty);
            highScores.add(highscore);
            cursor.moveToNext();

        }
        cursor.close();
        return highScores;
    }

    private String[] getWhichColumns(int whichDifficulty) {
        String[] c;
        if (whichDifficulty== 1) {
            c = new String[]{"easy"};
        } else if (whichDifficulty == 2) {
            c = new String[]{"normal"};
        } else {
            c = new String[]{"hard"};
        }
        return c;
    }

    // Inserts highscore into DB
    public void insertHighscore(int whichDifficulty, int scoreRecorded) {
        ContentValues contentValues = new ContentValues();
        if (whichDifficulty == 1) {
            contentValues.put(MySqlLiteHelper.HighscoreColumns.easy.toString(), scoreRecorded);
        } else if (whichDifficulty == 2) {
            contentValues.put(MySqlLiteHelper.HighscoreColumns.normal.toString(), scoreRecorded);

        } else {
            contentValues.put(MySqlLiteHelper.HighscoreColumns.hard.toString(), scoreRecorded);
        }

        open();
        database.insert(MySqlLiteHelper.DATA_TABLE, null, contentValues);
    }


    //going to need updating
    private Integer cursorToHighscore(Cursor cursor, int whichDifficulty) {

        int highscore;

        //int scoreId = cursor.getInt(MySqlLiteHelper.HighscoreColumns.primary_key.ordinal());
        if (whichDifficulty == 1) {
            highscore = cursor.getInt(MySqlLiteHelper.HighscoreColumns.easy.ordinal());
        } else if (whichDifficulty == 2) {
            highscore = cursor.getInt(MySqlLiteHelper.HighscoreColumns.normal.ordinal());
        } else {
            highscore = cursor.getInt(MySqlLiteHelper.HighscoreColumns.hard.ordinal());
        }

        Integer highScore = new Integer(highscore);


        return highScore;
    }

    //this is for the endgame alert dialog
    public String getHighscore(int whichDifficulty) {
        List<Integer> highScores = getAllHighscores(whichDifficulty);
        int highScore = highScores.get(0);

        for (int i : highScores) {
            if (i > highScore) {
                highScore = i;
            }

        }

        return String.valueOf(highScore);
    }

    public List<Integer> getTopHighscores(int whichDifficulty)
    {
        List<Integer> highScores = getAllHighscores(whichDifficulty);
        List<Integer> highScoresTop10 = new ArrayList<>();


        int max = 10;
        int count = 0;
        for(int high : highScores)
        {
            if(count == max)
            {
                break;
            }

            //if highscore is not already in top 10 it add it to top 10 (rid of duplicates)
            if(!(highScoresTop10.contains(high)) && high != 0) {
                highScoresTop10.add(high);
            }
            count++;

        }



        return highScoresTop10;
    }


}








