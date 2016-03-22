package com.warwickcodingapp.DatabaseClasses;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.warwickcodingapp.ModelClasses.User;

import java.util.ArrayList;


public class UserTable {
    public static String ROW_ID = "ID";
    public static String NAME = "NAME";
    public static String EMAIL = "EMAIL";
    public static String AGE = "AGE";
    public static String LOCATION = "LOCATION";
    public static String GENDER = "GENDER";
    public static String HASPICTURE = "HASPICTURE";
    public static String[] COLUMNS = {ROW_ID, NAME, EMAIL, AGE, LOCATION, GENDER, HASPICTURE};

    private static final String DATABASE_TABLE = "USER";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    public UserTable(Context context) {
        mDbHelper = new DatabaseHelper(context);
    }

    public UserTable(DatabaseHelper database) {
        mDbHelper = database;
    }

    public void open() throws SQLException {
        mDb = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public int createRow(User user){
        ContentValues initialValues = new ContentValues();
        initialValues.put(NAME, user.getName());
        initialValues.put(EMAIL, user.getEmail());
        initialValues.put(AGE, user.getAge());
        initialValues.put(LOCATION, user.getLocation());
        initialValues.put(GENDER, boolToInt(user.getGender()));
        initialValues.put(HASPICTURE, boolToInt(user.getHasPicture()));

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

        return this.mDb.query(DATABASE_TABLE, COLUMNS, null, null, null, null, null);
    }

    public Cursor getRow(int rowID) {

        return this.mDb.query(true, DATABASE_TABLE, COLUMNS, ROW_ID + "='" + rowID + "'", null, null, null, null, null);
    }

    private ArrayList<User> convertCursor(Cursor cursor) {
        ArrayList<User> users = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                User user = convertSingleCursor(cursor);
                users.add(user);
            } while (cursor.moveToNext());
        }
        return users;
    }

    private User convertSingleCursor(Cursor cursor) {
        User user = new User();
        user.setId(Integer.parseInt(cursor.getString(0)));
        user.setName(cursor.getString(1));
        user.setEmail(cursor.getString(2));
        user.setAge(Integer.parseInt(cursor.getString(3)));
        user.setLocation(cursor.getString(4));
        user.setGender(stringToBool(cursor.getString(5)));
        user.setHasPicture(stringToBool(cursor.getString(6)));
        return user;
    }

    public ArrayList<User> getAllUsers() {
        open();
        Cursor cursor = getAllRows();
        ArrayList<User> users = convertCursor(cursor);
        close();
        return users;
    }

    public User getUserFromID(int id) {
        open();
        Cursor cursor = getRow(id);
        User user = new User();
        if (cursor.moveToFirst()) {
            user = convertSingleCursor(cursor);
        }
        close();
        return user;
    }

    public void createMultipleFunctions(ArrayList<User> users) {
        for (User u: users) {
            createRow(u);
        }
    }

    public int rowCount() {
        int count;
        open();
        count = getAllRows().getCount();
        close();
        return count;
    }

    private static int boolToInt(boolean bool) {
        if (bool) return 1;
        else return 0;
    }

    private static boolean stringToBool(String s) {
        if (s.equals("1")) return true;
        else if (s.equals("0")) return false;
        throw new IllegalArgumentException(s+" is not a bool. Only 1 and 0 are.");
    }
}
