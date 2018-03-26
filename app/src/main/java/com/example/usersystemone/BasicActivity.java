package com.example.usersystemone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.usersystemone.dbutil.UserDatabaseHelper;

/**
 * Created by Administrator on 2018/3/26 0026.
 */

public class BasicActivity extends AppCompatActivity {

    protected static String Tag = null;
    private UserDatabaseHelper dbHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tag = getClass().getSimpleName();
        Log.i(Tag, "onCreate");
        if(dbHelper == null) {

            setDbHelper(new UserDatabaseHelper(this, "User", null, 1));
        }
        Log.i(Tag, "dbHelper = " + dbHelper);
    }

    public UserDatabaseHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(UserDatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i(Tag, getClass().getSimpleName() + "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(Tag, getClass().getSimpleName() + "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(Tag, getClass().getSimpleName() + "onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(Tag, getClass().getSimpleName() + "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(Tag, getClass().getSimpleName() + "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(Tag, getClass().getSimpleName() + "onDestroy");
    }
}
