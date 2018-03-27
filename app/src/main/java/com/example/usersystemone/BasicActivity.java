package com.example.usersystemone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.usersystemone.common.util.ActivityController;
import com.example.usersystemone.dbutil.UserDatabaseHelper;

/**
 * Created by Administrator on 2018/3/26 0026.
 */

public class BasicActivity extends AppCompatActivity {

    protected static String Tag = null;
    private UserDatabaseHelper dbHelper;
    private ForceOfflineReceiver receiver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
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
    protected void onStop() {
        super.onStop();
        Log.i(Tag, getClass().getSimpleName() + "onStop");
    }


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.usersystemone.FORCE_OFFLINE");
        receiver = new ForceOfflineReceiver();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(receiver!=null){
            unregisterReceiver(receiver);
            receiver=null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }

    class ForceOfflineReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, Intent intent) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Waring");
            builder.setMessage("You will be offline and will go back to initial screen");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityController.finishAll();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            });
            builder.show();
        }
    }
}

