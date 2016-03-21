package com.warwickcodingapp.ModelClasses;

public class Comment {
    private int _id;
    private int _userId;
    private String _comment;

    public Comment() {

    }

    //getters and setters etc.
    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public int getUserId() {
        return _userId;
    }

    public void setUserId(int _userId) {
        this._userId = _userId;
    }

    public String getComment() {
        return _comment;
    }

    public void setComment(String _comment) {
        this._comment = _comment;
    }
}
