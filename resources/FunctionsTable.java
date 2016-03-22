package com.digitalsnack.errorpropagator.DatabaseClasses;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.digitalsnack.errorpropagator.ModelClasses.Function;

public class FunctionsTable {
	public static String ROW_ID = "ID";
	public static String DATEUSED = "DATEUSED";
    public static String FUNCTION = "FUNCTION";
    public static String FUNCTIONTEXT = "FUNCTIONTEXT";
    public static String CURSORPOS = "CURSORPOS";
    public static SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
    
    private static final String DATABASE_TABLE = "FUNCTIONS";

    private DBAdapter mDbHelper;
    private SQLiteDatabase mDb;
    
    public FunctionsTable(Context context) {
        mDbHelper = new DBAdapter(context);
    }
    
    public FunctionsTable(DBAdapter database) {
        mDbHelper = database;
    }
    
    public void open() throws SQLException {
    	mDb = mDbHelper.getWritableDatabase();
    }
    
    public void close() {
        mDbHelper.close();
    }
    
    public int createRow(Function function){
        ContentValues initialValues = new ContentValues();
        initialValues.put(FUNCTION, function.getFunction());
        initialValues.put(FUNCTIONTEXT, function.getFunctionText());
        initialValues.put(CURSORPOS, function.getCursorPos());
        try {
        	initialValues.put(DATEUSED, df.format(function.getDateUsed()));
        }
        catch (Exception e) {
        	//Code should not go into here
        }
        open();
        int id = (int) this.mDb.insert(DATABASE_TABLE, null, initialValues);
        close();
        return id;
    }
    
    public boolean deleteAllRows() {
    	open();
    	boolean deleted = this.mDb.delete(DATABASE_TABLE, null, null) > 0;
    	close();
        return deleted;
    }
    
    public boolean DeleteRow(int RowID) {
    	open();
    	boolean deleted = this.mDb.delete(DATABASE_TABLE, ROW_ID + "= " + RowID, null) > 0;
    	close();
    	return deleted;
    }
    
    public Cursor getAllRows() {

        return this.mDb.query(DATABASE_TABLE, new String[] { ROW_ID, DATEUSED,
        		FUNCTION, FUNCTIONTEXT, CURSORPOS}, null, null, null, null, DATEUSED + " DESC");
    }
    
    public Cursor getRow(int rowID) {

        return this.mDb.query(true, DATABASE_TABLE, new String[] { ROW_ID, DATEUSED,
        		FUNCTION, FUNCTIONTEXT, CURSORPOS}, ROW_ID + "='" + rowID + "'", null, null, null, null, null);
    }
    
    public Cursor getLastRow() {

        return this.mDb.query(true, DATABASE_TABLE, new String[] { ROW_ID, DATEUSED,
        		FUNCTION, FUNCTIONTEXT, CURSORPOS}, null, null, null, null, ROW_ID +" DESC", "1");
    }
    
    public ArrayList<Function> convertCursor(Cursor cursor) {
    	ArrayList<Function> functions = new ArrayList<Function>();
    	
    	if (cursor.moveToFirst()) {
            do {
            	Function function = new Function();
            	function.setID(Integer.parseInt(cursor.getString(0)));
            	Date time; 
        		try {
        			time = df.parse(cursor.getString(1));
        			function.setDateUsed(time);
        			} 
        		catch (Exception e) {
        			//Code should never come into here.
        			}
            	function.setFunction(cursor.getString(2));
                function.setFunctionText(cursor.getString(3));
                function.setCursorPos(Integer.parseInt(cursor.getString(4)));
        		functions.add(function);
            } while (cursor.moveToNext());
        }
        return functions;
    }
    
    public Function convertSingleCursor(Cursor cursor) {
    	Function function = new Function();
    	
    	if (cursor.moveToFirst()) {
            function.setID(Integer.parseInt(cursor.getString(0)));
            Date time; 
        	try {
        		time = df.parse(cursor.getString(1));
        		function.setDateUsed(time);
        		} 
        	catch (Exception e) {
        		//Code should never come into here.
        		}
            function.setFunction(cursor.getString(2));
            function.setFunctionText(cursor.getString(3));
            function.setCursorPos(Integer.parseInt(cursor.getString(4)));
        }
        return function;
    }
    
    public ArrayList<Function> getAllFunctions() {
    	open();
    	Cursor cursor = getAllRows();
    	ArrayList<Function> functions = convertCursor(cursor);
    	close();
        return functions;
    }
    
    public Function getFunctionFromID(int id) {
    	open();
    	Cursor cursor = getRow(id);
    	Function function = convertSingleCursor(cursor);
    	close();
        return function;
    }

    public void createMultipleFunctions(ArrayList<Function> functions) {
        for (Function f: functions) {
            createRow(f);
        }
    }
    
    public int rowCount() {
    	int count;
    	open();
    	count = getAllRows().getCount();
    	close();
    	return count;
    }
}
