package com.warwickcodingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.warwickcodingapp.AdapterClasses.UserListAdapter;
import com.warwickcodingapp.DatabaseClasses.UserTable;
import com.warwickcodingapp.ModelClasses.User;

import java.util.ArrayList;
import java.util.Random;

public class UsersActivity extends AppCompatActivity {
    private ListView _userList;
    private UserListAdapter _userListAdapter;
    private UserTable _userTable;
    private final static int ADDED_RECORD = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsersActivity.this, AddRecordActivity.class);
                startActivityForResult(intent, ADDED_RECORD);
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (getIntent().getExtras() != null) {
                getSupportActionBar().setTitle(getIntent().getExtras().getString(MainActivity.PASSWORD));
            }
        }
        getXMLControls();
        createTables();
        initialiseList();
    }

    public void getXMLControls() {
        _userList = (ListView) findViewById(R.id.userList);
    }

    private void createTables() {
        _userTable = new UserTable(this);
    }

    private void initialiseList() {
        final ArrayList<User> users = _userTable.getAllUsers();
        _userListAdapter = new UserListAdapter(this, users);
        _userList.setAdapter(_userListAdapter);
        _userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User u = users.get(position);
                if (u != null) {
                    Intent intent = new Intent(UsersActivity.this, ViewUserActivity.class);
                    intent.putExtra(ViewUserActivity.idKey, u.getId());
                    startActivityForResult(intent, ADDED_RECORD);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case ADDED_RECORD:
                initialiseList();
                break;
        }
    }
}
