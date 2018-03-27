package com.example.usersystemone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usersystemone.common.util.ValidatorHelper;
import com.example.usersystemone.dbutil.UserDatabaseHelper;

public class LoginActivity extends BasicActivity {
    private UserDatabaseHelper dbHelper;
    private Button doLoginButton;
    private EditText userIdText;
    private EditText passwdText;
    private CheckBox rememberPasswd;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = getDbHelper();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        userIdText = (EditText) findViewById(R.id.userIdEditText);
        passwdText = (EditText) findViewById(R.id.passwdEditText);
        rememberPasswd = (CheckBox) findViewById(R.id.rember_passwd);
        boolean isRemembered = sharedPreferences.getBoolean("remember_password",false);
        Log.d(Tag, "isRemembered " +isRemembered);
        if(isRemembered) {
            String userId = sharedPreferences.getString("userId","");
            String password = sharedPreferences.getString("password", "");
            userIdText.setText(userId);
            passwdText.setText(password);
            rememberPasswd.setChecked(true);
        }

        doLoginButton = (Button) findViewById(R.id.doLoginButton);
        doLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(Tag, "onClick start, button id : " + view.getId());
                SQLiteDatabase sqLiteDatabase = null;
                Cursor cursor = null;
                switch (view.getId()) {
                    case R.id.doLoginButton :
                        Log.d(Tag, "doLoginButton");
                        String userId = userIdText.getText().toString();
                        String password = passwdText.getText().toString();

                        Log.d(Tag, "userId = " + userId);
                        Log.d(Tag, "password = " + password);
                        if (!ValidatorHelper.validateIncoming(userId)){
                            Toast.makeText(LoginActivity.this, "User Id is invalid!", Toast.LENGTH_SHORT).show();
                            break;
                        } else if (!ValidatorHelper.validateIncoming(password)){
                            Toast.makeText(LoginActivity.this, "Password is invalid!", Toast.LENGTH_SHORT).show();
                            break;
                        } else {
                            sqLiteDatabase = dbHelper.getWritableDatabase();
                            cursor = sqLiteDatabase.query("User", null, null, null, null, null, null);
                            int validUser = 0;
                            if(cursor.moveToFirst()){
                                do{
                                    String tempUserId = cursor.getString(cursor.getColumnIndex("userId"));
                                    String tempPasswd = cursor.getString(cursor.getColumnIndex("password"));
                                    Log.d(Tag, " tempUserId " + tempUserId);
                                    Log.d(Tag, " tempPasswd " + tempPasswd);
                                    if(userId.equals(tempUserId) && password.equals(tempPasswd)){
                                        ++validUser;
                                    }
                                }while (cursor.moveToNext());
                            }
                            cursor.close();
                            if(validUser>0){
                                sharedPreferencesEditor = sharedPreferences.edit();
                                if(rememberPasswd.isChecked()) {
                                    sharedPreferencesEditor.putString("userId", userId);
                                    sharedPreferencesEditor.putString("password", password);
                                    sharedPreferencesEditor.putBoolean("remember_password", true);
                                } else {
                                    sharedPreferencesEditor.clear();
                                }
                                sharedPreferencesEditor.apply();
                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                intent.putExtra("userId", userId);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "account or password is invalid", Toast.LENGTH_SHORT).show();
                            }

                        }
                        break;
                    default:
                        break;
                }

            }
        });

    }
}
