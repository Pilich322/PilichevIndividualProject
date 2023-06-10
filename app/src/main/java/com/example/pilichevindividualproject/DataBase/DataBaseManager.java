package com.example.pilichevindividualproject.DataBase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pilichevindividualproject.Data.Group;
import com.example.pilichevindividualproject.Data.Student;

import java.util.ArrayList;
import java.util.List;

public class DataBaseManager{
    private final Context context;
    private final DataBaseHelper dbHelper;
    private SQLiteDatabase db;

    public DataBaseManager (Context context){
        this.context = context;
        dbHelper = new DataBaseHelper(this.context);
    }

    @SuppressLint("Range")
    public List<Group> getAllGroupsFromDb(){
        List<Group> groupList = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("Select * From "+ DataBaseConstant.GROUP_TABLE_NAME,null);
        while (cursor.moveToNext()){
            Group group = new Group();
            group.setName(cursor.getString(cursor.getColumnIndex(DataBaseConstant.GROUP_NAME)));
            group.setNumber(cursor.getInt(cursor.getColumnIndex(DataBaseConstant.GROUP_NUMBER)));
            group.setId(cursor.getInt(cursor.getColumnIndex(DataBaseConstant.GROUP_ID)));
            groupList.add(group);
        }
        return groupList;
    }

    public void insertStudent(Student student){
        ContentValues cv = new ContentValues();
        cv.put(DataBaseConstant.STUDENT_FIRSTNAME,student.getFirstName());
        cv.put(DataBaseConstant.STUDENT_SECONDNAME,student.getSecondName());
        cv.put(DataBaseConstant.STUDENT_MIDDLENAME,student.getMiddleName());
        cv.put(DataBaseConstant.STUDENT_BIRTHDAY,student.getBirthday());
        cv.put(DataBaseConstant.STUDENT_GROUP_ID,student.getGroupId());
        db.insert(DataBaseConstant.STUDENTS_TABLE_NAME,null,cv);
    }

    public void updateStudent(Student student){
        ContentValues cv = new ContentValues();
        cv.put(DataBaseConstant.STUDENT_FIRSTNAME,student.getFirstName());
        cv.put(DataBaseConstant.STUDENT_SECONDNAME,student.getSecondName());
        cv.put(DataBaseConstant.STUDENT_MIDDLENAME,student.getMiddleName());
        cv.put(DataBaseConstant.STUDENT_BIRTHDAY,student.getBirthday());
        cv.put(DataBaseConstant.STUDENT_GROUP_ID,student.getGroupId());
        db.update(DataBaseConstant.STUDENTS_TABLE_NAME,cv,DataBaseConstant.STUDENT_ID + " = " +student.getId(),null);
    }
    public void updateGroup(Group group){
        ContentValues cv = new ContentValues();
        cv.put(DataBaseConstant.GROUP_NAME,group.getName());
        cv.put(DataBaseConstant.GROUP_NUMBER,group.getNumber());
        db.update(DataBaseConstant.GROUP_TABLE_NAME,cv,DataBaseConstant.GROUP_ID + " = " +group.getId(),null);
    }
    public void insertGroup(Group group){
        ContentValues cv = new ContentValues();
        cv.put(DataBaseConstant.GROUP_NAME,group.getName());
        cv.put(DataBaseConstant.GROUP_NUMBER,group.getNumber());
        db.insert(DataBaseConstant.GROUP_TABLE_NAME,null,cv);
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
