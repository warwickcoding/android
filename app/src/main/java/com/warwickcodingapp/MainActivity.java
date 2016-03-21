package com.warwickcodingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.warwickcodingapp.DatabaseClasses.DatabaseHelper;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private EditText _name;
    private Button _startButton;
    public static final String PASSWORD = "Password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getXMLControls();
        setUpFolders();
        animateButton();
    }

    private void getXMLControls() {
        _startButton = (Button) findViewById(R.id.startButton);
        _name = (EditText) findViewById(R.id.name);
    }

    private void setUpFolders(){
        File dir = new File(Environment.getExternalStorageDirectory().toString() + "/WarwickCoding/");
        File dir2 = new File(Environment.getExternalStorageDirectory().toString() + "/WarwickCoding/.ProfilePictures/");
        try{
            if(dir.mkdir()) {
                Log.e("directory 1", "Directory created");
            } else {
                Log.e("directory 1", "Directory is not created");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            if(dir2.mkdir()) {
                Log.e("directory 2", "Directory created");
            } else {
                Log.e("directory 2", "Directory is not created");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void animateButton() {
        final Animation scale1 = new ScaleAnimation(1.0f, 1.07f, 1.0f, 0.93f,
                                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.3f);
        scale1.setDuration(1000);
        final Animation scale2 = new ScaleAnimation(1.07f, 1.0f, 0.93f, 1.0f,
                                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.3f);
        scale2.setDuration(1000);

        scale1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                _startButton.startAnimation(scale2);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        scale2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                _startButton.startAnimation(scale1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        _startButton.startAnimation(scale1);
    }

    public void start(View view) {
        String inputtedText = _name.getText().toString();
        if (!inputtedText.equals("")) {
            Intent intent = new Intent(this, UsersActivity.class);
            intent.putExtra(PASSWORD, inputtedText);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Please Enter a Name", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        _startButton.setOnClickListener(null);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
