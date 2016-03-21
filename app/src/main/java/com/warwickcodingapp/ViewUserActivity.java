package com.warwickcodingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.warwickcodingapp.AdapterClasses.CommentListAdapter;
import com.warwickcodingapp.DatabaseClasses.CommentTable;
import com.warwickcodingapp.DatabaseClasses.UserTable;
import com.warwickcodingapp.ModelClasses.Comment;
import com.warwickcodingapp.ModelClasses.User;
import com.warwickcodingapp.ServiceClasses.PictureServices;

import java.util.ArrayList;

public class ViewUserActivity extends AppCompatActivity {
    public final static String idKey = "UserId";
    private TextView _name, _email, _age, _location, _gender;
    private ImageView _profilePicture;
    private ImageButton _addComment;
    private ListView _commentSection;
    private RelativeLayout _geometricBackground;
    private int _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getExtras() != null) {
            _id = getIntent().getExtras().getInt(idKey);
            UserTable userTable = new UserTable(this);
            User user = userTable.getUserFromID(_id);
            getXMLControls();
            setUserDetails(user);
        }
        else {
            finish();
        }
    }

    private void getXMLControls() {
        _name = (TextView) findViewById(R.id.name);
        _email = (TextView) findViewById(R.id.email);
        _age = (TextView) findViewById(R.id.age);
        _location = (TextView) findViewById(R.id.location);
        _gender = (TextView) findViewById(R.id.gender);
        _profilePicture = (ImageView) findViewById(R.id.profilePicture);
        _addComment = (ImageButton) findViewById(R.id.addComment);
        _commentSection = (ListView) findViewById(R.id.commentSection);
        _geometricBackground = (RelativeLayout) findViewById(R.id.geometricBackground);
    }

    private void setUserDetails(User user) {
        _addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentDialog();
            }
        });
        _name.setText(user.getName());
        _email.setText(user.getEmail());
        _age.setText(user.getAge()+"");
        _location.setText(user.getLocation());
        String gender;
        if (user.getGender()){
            gender = "Male";
        }
        else {
            gender = "Female";
        }
        _gender.setText(gender);
        _profilePicture.setImageBitmap(PictureServices.getCircleProfilePicture(user, this));
        //Set random geometric background
        int rand = (int) Math.floor(Math.random() * 21);
        if (rand >21)
            rand = 21;
        if (rand<1)
            rand = 1;
        int resId = getResources().getIdentifier("geometric_" + rand, "drawable", getPackageName());
        _geometricBackground.setBackgroundResource(resId);

        showComments(user.getId());
        animateIn();
    }

    private void animateIn() {
        Animation fadeIn = new AlphaAnimation(0,1);
        fadeIn.setDuration(1500);
        _name.startAnimation(fadeIn);
        _email.startAnimation(fadeIn);
        _location.startAnimation(fadeIn);
        _age.startAnimation(fadeIn);
        _gender.startAnimation(fadeIn);

        Animation dropDown = new TranslateAnimation(0,0,-90,0);
        dropDown.setDuration(1500);
        Animation fadeIn2 = new AlphaAnimation(0,1);
        fadeIn2.setDuration(1500);
        AnimationSet both = new AnimationSet(true);
        both.addAnimation(dropDown);
        both.addAnimation(fadeIn2);
        both.setInterpolator(new DecelerateInterpolator());
        _profilePicture.startAnimation(both);
    }

    private void showComments(int id) {
        CommentTable commentTable = new CommentTable(this);
        ArrayList<Comment> comments = commentTable.getCommentFromUserID(id);
        CommentListAdapter commentAdapter = new CommentListAdapter(this, comments);
        _commentSection.setAdapter(commentAdapter);
    }

    private void showCommentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.viewUser_dialogTitle_addComment);
        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setCancelable(true);
        builder.setPositiveButton("ADD",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Comment comment = new Comment();
                        comment.setUserId(_id);
                        comment.setComment(input.getEditableText().toString());
                        CommentTable commentTable = new CommentTable(ViewUserActivity.this);
                        commentTable.createRow(comment);
                        dialog.dismiss();
                        showComments(_id);
                    }
                });
        builder.setNegativeButton(R.string.viewUser_dialogButton_cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_deleteUser:
                UserTable userTable = new UserTable(this);
                userTable.DeleteRow(_id);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
