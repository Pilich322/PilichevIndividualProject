package com.example.pilichevindividualproject.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseManager{
    Context context;
    DataBaseHelper dbHelper;
    SQLiteDatabase db;

    public DataBaseManager (Context context){
        this.context = context;
        dbHelper = new DataBaseHelper(this.context);
    }

    public void openDbToWrite(){
        db = dbHelper.getWritableDatabase();
    }

    public void openDbToRead(){
        db = dbHelper.getReadableDatabase();
    }
    public void closeDb(){
        db.close();
    }
}
