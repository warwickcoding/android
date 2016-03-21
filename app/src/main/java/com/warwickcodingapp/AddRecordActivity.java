package com.warwickcodingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.warwickcodingapp.DatabaseClasses.UserTable;
import com.warwickcodingapp.ModelClasses.User;
import com.warwickcodingapp.ServiceClasses.PictureServices;

public class AddRecordActivity extends AppCompatActivity {
    private EditText _name, _email, _age, _location;
    private ImageView _profilePicture;
    private Spinner _gender;
    private Button _createRecord;
    private UserTable _userTable;
    private Bitmap _profileBitmap = null;
    private static final int SELECT_PHOTO = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getXMLControls();
        setOnClickListeners();
        createTables();
    }

    private void getXMLControls() {
        _name = (EditText) findViewById(R.id.name);
        _email = (EditText) findViewById(R.id.email);
        _age = (EditText) findViewById(R.id.age);
        _location = (EditText) findViewById(R.id.location);
        _profilePicture = (ImageView) findViewById(R.id.profilePicture);
        _gender = (Spinner) findViewById(R.id.gender);
        _createRecord = (Button) findViewById(R.id.createRecord);
    }

    private void setOnClickListeners() {
        _profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });
        _createRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRecord();
            }
        });
    }

    private void createTables(){
        _userTable = new UserTable(this);
    }

    private void createRecord() {
        User newUser = new User();

        //add the users name after checking it is not empty
        String name = _name.getEditableText().toString();
        if(!name.equals("")) {
            newUser.setName(name);
        }
        else {
            showErrorDialog("Error Creating Record", "Name must not be nothing");
        }
        newUser.setEmail(_email.getEditableText().toString());
        if (!_age.getEditableText().toString().equals(""))
            newUser.setAge(Integer.parseInt(_age.getEditableText().toString()));
        newUser.setLocation(_location.getEditableText().toString());
        newUser.setGender(_gender.getSelectedItemPosition() == 1);
        newUser.setHasPicture(_profileBitmap != null);
        newUser.setId(_userTable.createRow(newUser));
        if (_profileBitmap != null)
            PictureServices.saveProfilePicFromBitmap(_profileBitmap, newUser.getId());
        Toast.makeText(this, "Created User", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void showErrorDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setNeutralButton(R.string.addUser_dialogButton_ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK) {
                    //Code that runs when the user returns from picking a picture
                    Uri selectedImage = data.getData();
                    Bitmap newPic = PictureServices.decodeSampledBitmapFromStream(selectedImage, 90, 90, this);
                    newPic = PictureServices.createSquareBitmap(newPic);
                    _profilePicture.setImageBitmap(newPic);
                    _profileBitmap = newPic;
                }
                break;
        }
    }

}
