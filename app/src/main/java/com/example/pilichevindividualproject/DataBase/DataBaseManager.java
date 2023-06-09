package com.example.pilichevindividualproject.DataBase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.pilichevindividualproject.Data.Group;
import com.example.pilichevindividualproject.Data.Student;
import com.example.pilichevindividualproject.R;

import java.util.ArrayList;
import java.util.List;

public class DataBaseManager {
    private final Context context;
    private final DataBaseHelper dbHelper;
    private SQLiteDatabase db;

    public DataBaseManager(Context context) {
        this.context = context;
        dbHelper = new DataBaseHelper(this.context);
    }

    @SuppressLint("Range")
    public List<Group> getAllGroupsFromDb() {
        List<Group> groupList = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("Select * From " + DataBaseConstant.GROUP_TABLE_NAME, null);
        while (cursor.moveToNext()) {
            Group group = new Group();
            group.setName(cursor.getString(cursor.getColumnIndex(DataBaseConstant.GROUP_NAME)));
            group.setNumber(cursor.getInt(cursor.getColumnIndex(DataBaseConstant.GROUP_NUMBER)));
            group.setId(cursor.getInt(cursor.getColumnIndex(DataBaseConstant.GROUP_ID)));
            groupList.add(group);
        }
        cursor.close();
        return groupList;
    }

    @SuppressLint("Range")
    public List<Student> getAllStudentsFromDbIntoEditText(String word) {
        List<Student> studentList = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("Select * From " + DataBaseConstant.STUDENTS_TABLE_NAME + " Where " + DataBaseConstant.STUDENT_SECONDNAME + " =?" + " or " + DataBaseConstant.STUDENT_FIRSTNAME + " =?", new String[]{word});
        while (cursor.moveToNext()) {
            Student student = new Student();
            student.setFirstName(cursor.getString(cursor.getColumnIndex(DataBaseConstant.STUDENT_FIRSTNAME)));
            student.setBirthday(cursor.getString(cursor.getColumnIndex(DataBaseConstant.STUDENT_BIRTHDAY)));
            student.setGroupId(cursor.getColumnIndex(DataBaseConstant.STUDENT_GROUP_ID));
            student.setSecondName(cursor.getString(cursor.getColumnIndex(DataBaseConstant.STUDENT_SECONDNAME)));
            student.setMiddleName(cursor.getString(cursor.getColumnIndex(DataBaseConstant.STUDENT_MIDDLENAME)));
            student.setId(cursor.getColumnIndex(DataBaseConstant.STUDENT_ID));
            studentList.add(student);
        }
        return studentList;
    }

    @SuppressLint("Range")
    public List<Student> getAllStudentsFromDb() {
        List<Student> studentList = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("Select * From " + DataBaseConstant.STUDENTS_TABLE_NAME, null);
        while (cursor.moveToNext()) {
            Student student = new Student();
            student.setFirstName(cursor.getString(cursor.getColumnIndex(DataBaseConstant.STUDENT_FIRSTNAME)));
            student.setMiddleName(cursor.getString(cursor.getColumnIndex(DataBaseConstant.STUDENT_MIDDLENAME)));
            student.setSecondName(cursor.getString(cursor.getColumnIndex(DataBaseConstant.STUDENT_SECONDNAME)));
            student.setBirthday(cursor.getString(cursor.getColumnIndex(DataBaseConstant.STUDENT_BIRTHDAY)));
            student.setGroupId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DataBaseConstant.STUDENT_GROUP_ID))));
            student.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DataBaseConstant.STUDENT_ID))));
            studentList.add(student);
        }
        cursor.close();
        return studentList;
    }

    public void deleteStudent(Student student) {
        Toast.makeText(context.getApplicationContext(), R.string.delete, Toast.LENGTH_SHORT).show();
        db.delete(DataBaseConstant.STUDENTS_TABLE_NAME, DataBaseConstant.STUDENT_ID + " = " + student.getId(), null);
    }

    private boolean checkStudent(Group group) {
        for (Student s : getAllStudentsFromDb()) {
            if (s.getGroupId() == group.getId()) {
                Toast.makeText(context.getApplicationContext(), "В группе есть студенты", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    public void deleteGroup(Group group) {
        if (checkStudent(group)) {
            Toast.makeText(context.getApplicationContext(), R.string.delete, Toast.LENGTH_SHORT).show();
            db.delete(DataBaseConstant.GROUP_TABLE_NAME, DataBaseConstant.GROUP_ID + " = " + group.getId(), null);
        }
    }

    public void insertStudent(Student student) {
        ContentValues cv = new ContentValues();
        cv.put(DataBaseConstant.STUDENT_FIRSTNAME, student.getFirstName());
        cv.put(DataBaseConstant.STUDENT_SECONDNAME, student.getSecondName());
        cv.put(DataBaseConstant.STUDENT_MIDDLENAME, student.getMiddleName());
        cv.put(DataBaseConstant.STUDENT_BIRTHDAY, student.getBirthday());
        cv.put(DataBaseConstant.STUDENT_GROUP_ID, student.getGroupId());
        db.insert(DataBaseConstant.STUDENTS_TABLE_NAME, null, cv);
    }

    public void updateStudent(Student student) {
        ContentValues cv = new ContentValues();
        cv.put(DataBaseConstant.STUDENT_FIRSTNAME, student.getFirstName());
        cv.put(DataBaseConstant.STUDENT_SECONDNAME, student.getSecondName());
        cv.put(DataBaseConstant.STUDENT_MIDDLENAME, student.getMiddleName());
        cv.put(DataBaseConstant.STUDENT_BIRTHDAY, student.getBirthday());
        cv.put(DataBaseConstant.STUDENT_GROUP_ID, student.getGroupId());
        db.update(DataBaseConstant.STUDENTS_TABLE_NAME, cv, DataBaseConstant.STUDENT_ID + " = " + student.getId(), null);
    }

    public void updateGroup(Group group) {
        ContentValues cv = new ContentValues();
        cv.put(DataBaseConstant.GROUP_NAME, group.getName());
        cv.put(DataBaseConstant.GROUP_NUMBER, group.getNumber());
        db.update(DataBaseConstant.GROUP_TABLE_NAME, cv, DataBaseConstant.GROUP_ID + " = " + group.getId(), null);
    }

    @SuppressLint("Range")
    public List<Student> updateStudentAdapter(Group group) {
        List<Student> studentList = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("Select * From " + DataBaseConstant.STUDENTS_TABLE_NAME + " Where " + DataBaseConstant.STUDENT_GROUP_ID + " = " + group.getId(), null);
        while (cursor.moveToNext()) {
            Student student = new Student();
            student.setFirstName(cursor.getString(cursor.getColumnIndex(DataBaseConstant.STUDENT_FIRSTNAME)));
            student.setMiddleName(cursor.getString(cursor.getColumnIndex(DataBaseConstant.STUDENT_MIDDLENAME)));
            student.setSecondName(cursor.getString(cursor.getColumnIndex(DataBaseConstant.STUDENT_SECONDNAME)));
            student.setBirthday(cursor.getString(cursor.getColumnIndex(DataBaseConstant.STUDENT_BIRTHDAY)));
            student.setGroupId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DataBaseConstant.STUDENT_GROUP_ID))));
            student.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DataBaseConstant.STUDENT_ID))));
            studentList.add(student);
        }
        cursor.close();
        return studentList;
    }

    public void insertGroup(Group group) {
        ContentValues cv = new ContentValues();
        cv.put(DataBaseConstant.GROUP_NAME, group.getName());
        cv.put(DataBaseConstant.GROUP_NUMBER, group.getNumber());
        db.insert(DataBaseConstant.GROUP_TABLE_NAME, null, cv);
    }

    public void openDbToWrite() {
        db = dbHelper.getWritableDatabase();
    }

    public void closeDb() {
        db.close();
    }
}
