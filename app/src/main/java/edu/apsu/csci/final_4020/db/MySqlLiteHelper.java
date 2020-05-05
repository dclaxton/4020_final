/*
    Authors: Daniel Davis, Dalton Claxton, Peyton White
    Date: 7 May 2020
    Description: A simple implementation of an 'impossible' quiz
 */

package edu.apsu.csci.final_4020.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqlLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "highscores.sqlite";
    private static final int DB_VERSION = 1;
    static final String DATA_TABLE = "Data";

    // DB columns
    public enum HighscoreColumns {
        primary_key,
        easy,
        normal,
        hard;

        public static String[] names() {
            HighscoreColumns[] v = values();
            String[] names = new String[v.length];
            for (int i = 0; i < v.length; i++) {
                names[i] = v[i].toString();
            }
            return names;
        }
    }

    MySqlLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create table with created columns
        String sql = "CREATE TABLE " + DATA_TABLE + " (" +
                HighscoreColumns.primary_key + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HighscoreColumns.easy + " INTEGER , " +
                HighscoreColumns.normal + " INTEGER ," +
                HighscoreColumns.hard + " INTEGER  " +
                ");";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
