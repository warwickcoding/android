package com.digitalsnack.errorpropagator.DatabaseClasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter extends SQLiteOpenHelper
{

    public static final String DATABASE_NAME = "PhysicsErrorPropagatorDB.db";
    public static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_FUNCTIONS =
    	       "CREATE TABLE IF NOT EXISTS FUNCTIONS (ID integer primary key autoincrement, " +
    	       "DATEUSED DATE, " +
    	       "FUNCTION TEXT, " +
               "FUNCTIONTEXT TEXT, CURSORPOS INT)";
    private static final String CREATE_TABLE_RESULTS =
  	       "CREATE TABLE IF NOT EXISTS RESULTS (ID integer primary key autoincrement, " +
  	       "DATEUSED DATE, FUNCTIONID INT, VALUE DOUBLE, ERROR DOUBLE)";
    private static final String CREATE_TABLE_VARIABLES =
   	       "CREATE TABLE IF NOT EXISTS VARIABLES (ID integer primary key autoincrement, " +
   	       "VARIABLE TEXT, VALUE DOUBLE, ERROR DOUBLE, RESULTID INT)";
    //Add table strings here 

    public DBAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

      @Override
      public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_FUNCTIONS);
        database.execSQL(CREATE_TABLE_RESULTS);
        database.execSQL(CREATE_TABLE_VARIABLES);
        //add tables here
      }

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //upgrade here
      }

} 