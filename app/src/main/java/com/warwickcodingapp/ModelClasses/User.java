package com.warwickcodingapp.ModelClasses;

import android.graphics.Bitmap;
import android.os.Environment;

import com.warwickcodingapp.ServiceClasses.PictureServices;

public class User {
    private int _id;
    private String _name;
    private String _email;
    private Bitmap _profilePicture;
    private int _age;
    private String _location;
    private boolean _gender;
    private float _rating;
    private boolean _hasPicture;

    public User() {
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getEmail() {
        return _email;
    }

    public void setEmail(String email) {
        _email = email;
    }

    public Bitmap getProfilePicture() {
        return _profilePicture;
    }

    public void setProfilePicture(Bitmap profilePicture) {
        _profilePicture = profilePicture;
    }

    public int getAge() {
        return _age;
    }

    public void setAge(int _age) {
        this._age = _age;
    }

    public float getRating() {
        return _rating;
    }

    public void setRating(float _rating) {
        this._rating = _rating;
    }


    public boolean getHasPicture() {
        return _hasPicture;
    }

    public void setHasPicture(boolean hasPicture) {
        this._hasPicture = hasPicture;
        if (hasPicture) {
            String mPath = Environment.getExternalStorageDirectory().toString() +
                                "/WarwickCoding/.ProfilePictures/" + "profile_"+this._id +".jpg";
            Bitmap profilePic = PictureServices.decodeSampledBitmapFromFile(mPath, 90,90);
            this.setProfilePicture(profilePic);
        }
    }

    public String getLocation() {
        return _location;
    }

    public void setLocation(String _location) {
        this._location = _location;
    }

    public boolean getGender() {
        return _gender;
    }

    public void setGender(boolean _gender) {
        this._gender = _gender;
    }
}



