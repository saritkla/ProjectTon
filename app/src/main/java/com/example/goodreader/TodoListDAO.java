package com.example.goodreader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static com.example.goodreader.DbHelper.COLUMN_AGE;
import static com.example.goodreader.DbHelper.COLUMN_BIRTHDAY;
import static com.example.goodreader.DbHelper.COLUMN_NAMESUR;
import static com.example.goodreader.DbHelper.COLUMN_SCHOOL;
import static com.example.goodreader.DbHelper.COLUMN_SCORE1;
import static com.example.goodreader.DbHelper.COLUMN_SCORE2;
import static com.example.goodreader.DbHelper.COLUMN_USERNAME;
import static com.example.goodreader.DbHelper.TABLE_NAME;

public class TodoListDAO {
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public TodoListDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open(){
        database = dbHelper.getWritableDatabase();
    }

    public  void close(){
        dbHelper.close();
    }

    public ArrayList<String> getAllTodoList(){
        ArrayList<String> todoList = new ArrayList<String>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+ TABLE_NAME + ";",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            todoList.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return todoList;
    }
    public boolean insertDataUser(String username ,String name ,String school ,String age ,String birthday,int score1,int score2){
        ContentValues contentValues= new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_NAMESUR, name);
        contentValues.put(COLUMN_SCHOOL, school);
        contentValues.put(COLUMN_AGE, age);
        contentValues.put(COLUMN_BIRTHDAY,birthday);
        contentValues.put(COLUMN_SCORE1,score1);
        contentValues.put(COLUMN_SCORE2,score2);
        long result = database.insert(TABLE_NAME, null,contentValues);


        return  result != -1;
    }
}
