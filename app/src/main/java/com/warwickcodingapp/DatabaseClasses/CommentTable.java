package com.warwickcodingapp.DatabaseClasses;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.warwickcodingapp.ModelClasses.Comment;
import com.warwickcodingapp.ModelClasses.User;

import java.util.ArrayList;


public class CommentTable {
    public static String ROW_ID = "ID";
    public static String USERID = "USERID";
    public static String COMMENT = "COMMENT";
    public static String[] COLUMNS = {ROW_ID, USERID, COMMENT};

    private static final String DATABASE_TABLE = "COMMENT";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    public CommentTable(Context context) {
        mDbHelper = new DatabaseHelper(context);
    }

    public CommentTable(DatabaseHelper database) {
        mDbHelper = database;
    }

    public void open() throws SQLException {
        mDb = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public int createRow(Comment comment){
        ContentValues initialValues = new ContentValues();
        initialValues.put(USERID, comment.getUserId());
        initialValues.put(COMMENT, comment.getComment());

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

    public Cursor getRowByUser(int userID) {
        return this.mDb.query(true, DATABASE_TABLE, COLUMNS, USERID + "='" + userID + "'", null, null, null, null, null);
    }

    private ArrayList<Comment> convertCursor(Cursor cursor) {
        ArrayList<Comment> comments = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Comment comment = convertSingleCursor(cursor);
                comments.add(comment);
            } while (cursor.moveToNext());
        }
        return comments;
    }

    private Comment convertSingleCursor(Cursor cursor) {
        Comment comment = new Comment();
        comment.setId(Integer.parseInt(cursor.getString(0)));
        comment.setUserId(Integer.parseInt(cursor.getString(1)));
        comment.setComment(cursor.getString(2));
        return comment;
    }

    public ArrayList<Comment> getAllComments() {
        open();
        Cursor cursor = getAllRows();
        ArrayList<Comment> comments = convertCursor(cursor);
        close();
        return comments;
    }

    public ArrayList<Comment> getCommentFromUserID(int userId) {
        open();
        Cursor cursor = getRowByUser(userId);
        ArrayList<Comment> comments = convertCursor(cursor);
        close();
        return comments;
    }

    public Comment getCommentFromID(int id) {
        open();
        Cursor cursor = getRow(id);
        Comment comment = new Comment();
        if (cursor.moveToFirst()) {
            comment = convertSingleCursor(cursor);
        }
        close();
        return comment;
    }

    public void createMultipleComments(ArrayList<Comment> comments) {
        for (Comment c: comments) {
            createRow(c);
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
