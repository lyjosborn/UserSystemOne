package com.example.usersystemone;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usersystemone.common.util.ValidatorHelper;
import com.example.usersystemone.dbutil.UserDatabaseHelper;

public class RegActivity extends BasicActivity {

    private UserDatabaseHelper dbHelper;
    private Button doRegButton;
    private EditText userIdText;
    private EditText passwdText;
    private EditText companyNameText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        dbHelper = getDbHelper();
        doRegButton = (Button) findViewById(R.id.doRegButton);

        doRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.doRegButton:
                        userIdText = (EditText) findViewById(R.id.userIdEditText);
                        passwdText = (EditText) findViewById(R.id.passwdEditText);
                        companyNameText = (EditText) findViewById(R.id.companyEditText);

                        String userId = userIdText.getText().toString();
                        String passwd = passwdText.getText().toString();
                        String companyName = companyNameText.getText().toString();

                        SQLiteDatabase sqLiteDatabase = null;
                        ContentValues values = null;
                        if(!ValidatorHelper.validateIncoming(userId) ) {
                            Toast.makeText(RegActivity.this, "User Id is invalid!", Toast.LENGTH_SHORT).show();
                            break;
                        } else if (!ValidatorHelper.validateIncoming(passwd)) {

                            Toast.makeText(RegActivity.this, "Password is invalid!", Toast.LENGTH_SHORT).show();;
                            break;
                        }else if (!ValidatorHelper.validateIncoming(companyName) ){

                            Toast.makeText(RegActivity.this, "Company Name is invalid!", Toast.LENGTH_SHORT).show();;
                            break;
                        } else {
                            sqLiteDatabase = dbHelper.getWritableDatabase();
                            if(ValidatorHelper.isExisting(sqLiteDatabase, userId)){
                                Toast.makeText(RegActivity.this, "User Id is existing!", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            values = new ContentValues();
                            values.put("userId", userId);
                            values.put("password", passwd);
                            values.put("company", companyName);
                            sqLiteDatabase.insert("User", null, values);

                            Log.d(RegActivity.this.Tag, "insert "+userId + " into User successfully. Start Intent...");
                            Intent intent = new Intent(RegActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        break;

                    default:
                        break;
                }

            }
        });


    }


}
