package com.warwickcodingapp.DatabaseClasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{

    public static final String DATABASE_NAME = "WarwickCodingApp.db";
    public static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_USER =
            "CREATE TABLE IF NOT EXISTS USER (ID integer primary key autoincrement, " +
                    "NAME TEXT, " +
                    "EMAIL TEXT, " +
                    "AGE INT, " +
                    "LOCATION TEXT, " +
                    "GENDER SMALLINT, " +
                    "HASPICTURE SMALLINT)";
    private static final String CREATE_TABLE_COMMENT =
            "CREATE TABLE IF NOT EXISTS COMMENT (ID integer primary key autoincrement, " +
                    "USERID INT, " +
                    "COMMENT TEXT)";
    //Add table strings here

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_USER);
        database.execSQL(CREATE_TABLE_COMMENT);
        //add tables here
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //upgrade here
    }

}