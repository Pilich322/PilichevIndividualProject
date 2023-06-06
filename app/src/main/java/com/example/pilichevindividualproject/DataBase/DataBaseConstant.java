package com.example.pilichevindividualproject.DataBase;

public class DataBaseConstant {

    public static final String DATABASE_NAME = "college.db";
    public static final int DATABASE_VERSION = 1;
    public static final String STUDENTS_TABLE_NAME = "students";
    public static final String STUDENT_ID = "student_id";
    public static final String STUDENT_FIRSTNAME = "firstName";
    public static final String STUDENT_SECONDNAME = "lastName";
    public static final String STUDENT_MIDDLENAME = "middleName";
    public static final String STUDENT_BIRTHDAY = "birthday";
    public static final String STUDENT_GROUP_ID = "group_id";

    public static final String GROUP_TABLE_NAME = "groups";
    public static final String GROUP_ID = "group_id";
    public static final String GROUP_NAME = "name";
    public static final String GROUP_NUMBER = "number";

    public static final String CREATE_TABLE_STUDENTS = "create table if not exists " +
            STUDENTS_TABLE_NAME + " ( " + STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            STUDENT_FIRSTNAME + " text not null, " + STUDENT_SECONDNAME + " text not null, " +
            STUDENT_MIDDLENAME + " text not null, " + STUDENT_BIRTHDAY + " text not null, " +
            STUDENT_GROUP_ID + " integer, FOREIGN KEY ("+STUDENT_GROUP_ID+") REFERENCES " + GROUP_TABLE_NAME + " ("+GROUP_ID+") )";

    public static final String DELETE_TABLE_USER = "drop table if exists " + STUDENTS_TABLE_NAME;

    public static final String CREATE_TABLE_GROUPS = "create table if not exists " +
            GROUP_TABLE_NAME + " ( " + GROUP_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            GROUP_NAME + " text not null, " + GROUP_NUMBER + " integer not null )";
    public static final String DELETE_TABLE_GROUPS = "drop table if exists " + GROUP_TABLE_NAME;
}
