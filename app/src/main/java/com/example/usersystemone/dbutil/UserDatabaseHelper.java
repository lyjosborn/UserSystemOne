package com.example.usersystemone.dbutil;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Created by Administrator on 2018/3/26 0026.
 */
    public class UserDatabaseHelper extends SQLiteOpenHelper {

        public static final String Tag = "MyDatabaseHelper";

        public static final String CREATE_BOOK = "create table User ("
                + "id integer primary key autoincrement,"
                + "userId text,"
                + "password text,"
                + "company text)";

        private Context mContext;

        public UserDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            mContext = context;
        }


        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            Log.d(Tag, "onCreate() create database start");
            sqLiteDatabase.execSQL(CREATE_BOOK);
            Log.d(Tag, "onCreate() create database end ");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            Log.d(Tag, "onUpgrade() start");
            sqLiteDatabase.execSQL("drop table if exists User");
            Log.d(Tag, "drop table if exists done");
            onCreate(sqLiteDatabase);
        }
    }
