package com.example.pilichevindividualproject.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(@Nullable Context context) {
        super(context, DataBaseConstant.DATABASE_NAME, null, DataBaseConstant.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseConstant.CREATE_TABLE_GROUPS);
        db.execSQL(DataBaseConstant.CREATE_TABLE_STUDENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DataBaseConstant.DELETE_TABLE_USER);
        db.execSQL(DataBaseConstant.DELETE_TABLE_GROUPS);
        onCreate(db);
    }
}
