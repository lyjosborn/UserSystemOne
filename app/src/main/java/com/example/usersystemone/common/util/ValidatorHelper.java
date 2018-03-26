package com.example.usersystemone.common.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Administrator on 2018/3/26 0026.
 */

public class ValidatorHelper {

    private static final String Tag = "ValidatorHelper";
    public static boolean validateIncoming(String incoming){
        if(incoming == null || "".equals(incoming)) {
            return false;
        }
        return true;

    }

    public static boolean isExisting(SQLiteDatabase sqLiteDatabase, String incoming){
        Cursor cursor = sqLiteDatabase.query("User", null, "userId = ?", new String[]{incoming}, null, null, null);
        if(cursor.moveToFirst()) {
            Log.d(Tag, "cursor.moveToFirst() is existing, return false");
            return true;
        }
        return  false;
    }
}
